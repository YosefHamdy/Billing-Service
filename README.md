# MOP Billing Service — Spring Boot

Rebuilt from `mop-portal` (JSF/WAR) into a stateless Spring Boot REST service.

---

## Architecture

```
com.mop.billing/
│
├── controller/                  ← REST endpoints (JWT secured)
│   └── BillController           POST /api/bills, DELETE cancel, GET status
│
├── service/                     ← Interfaces (contracts)
│   ├── IBillService
│   ├── IPaymentNotificationService
│   └── ITahseelGatewayService
│
├── service/impl/                ← Business Layer (implementations)
│   ├── BillService              Create / Cancel / Query / Retry
│   ├── PaymentNotificationService  PmtNotification + Reconciliation
│   └── TahseelGatewayService    Outbound SOAP to MOF gateway
│
├── data/
│   ├── entity/                  ← JPA entities (exact table mapping)
│   │   ├── BillMaster           SADAD_BILLS_MASTER
│   │   ├── SadadWsBills         SADAD_WS_BILLS
│   │   ├── RevenueEntry         SADAD_WS_BILLS_DET  (GFS codes)
│   │   ├── PmtInfo              SADAD_WS_PMT_NOTIFICATION
│   │   ├── SadadWsAuditRequest  SADAD_WS_AUDIT_REQUEST
│   │   ├── User                 USER_DATA
│   │   └── Role                 ROLE_DESC
│   └── repository/              ← Spring Data JPA repos (7 total)
│
├── producer/                    ← Kafka Producer Layer
│   └── BillingEventProducer     7 event types on 7 topics
│
├── integration/
│   ├── tahseel/                 ← Outbound Tahseel/SOAP
│   │   ├── TahseelSslConfig     Keystore + SSL init (mirrors SslUtils)
│   │   └── TahseelXmlSigner     WS-Security sign/verify (mirrors HeaderHandler)
│   └── sadad/                   ← Inbound Sadad/SOAP callbacks
│       ├── SadadPaymentNotificationController   POST /sadad/payment-notify
│       └── SadadReconciliationController        POST /sadad/reconciliation
│
├── security/                    ← JWT Security Layer
│   ├── JwtUtil                  Token generation / validation
│   ├── JwtAuthenticationFilter  Per-request Bearer token check
│   └── UserDetailsServiceImpl   Loads USER_DATA + ROLE_DESC
│
├── config/                      ← Spring configuration beans
│   ├── SecurityConfig           HttpSecurity rules + filter chain
│   ├── KafkaConfig              Producer factory
│   ├── TahseelProperties        tahseel.* config binding
│   ├── BillProperties           bill.* config binding
│   ├── KafkaTopicProperties     kafka.topics.* binding
│   └── JwtProperties            jwt.* config binding
│
├── dto/
│   ├── request/                 BillCreateRequest, BillCancelRequest,
│   │                            RevenueEntryRequest, PaymentNotificationRequest,
│   │                            ReconciliationRequest
│   └── response/                BillResponse, BillStatusResponse, ApiResponse<T>
│
└── exception/                   Custom exceptions + GlobalExceptionHandler
```

---

## Full Integration Flow

### 1 — Create Bill (Outbound to Tahseel)

```
Client                   BillController         BillService           TahseelGatewayService
  │                           │                     │                        │
  │ POST /api/bills            │                     │                        │
  │ Authorization: Bearer JWT  │                     │                        │
  ├──────────────────────────► │                     │                        │
  │                           │  createBill(req)     │                        │
  │                           ├────────────────────► │                        │
  │                           │                     │ persist BillMaster      │
  │                           │                     │ persist RevenueEntry[]  │
  │                           │                     │ persist SadadWsBills    │
  │                           │                     │                        │
  │                           │                     │ addBill(sadadBill, revs)│
  │                           │                     ├───────────────────────► │
  │                           │                     │                        │ SOAP: BillsMngAGWRq
  │                           │                     │                        ├──────────────────► Tahseel
  │                           │                     │                        │ ◄── I000000 (OK)
  │                           │                     │                        │ update REQID in DB
  │                           │                     │ ◄── statusCode         │
  │                           │                     │                        │
  │                           │                     │ Kafka: bill.created    │
  │                           │                     ├──────────────────── Kafka
  │                           │ BillResponse         │
  │ ◄─────────────────────── │ ◄──────────────────  │
```

### 2 — Payment Notification (Inbound from Tahseel)

```
Tahseel                  SadadPaymentNotificationController    PaymentNotificationService
  │                                    │                                │
  │ POST /sadad/payment-notify         │                                │
  │ (signed SOAP XML)                  │                                │
  ├───────────────────────────────────► │                                │
  │                                    │ Parse SOAP XML                 │
  │                                    │ Validate XML signature         │
  │                                    │ Validate header (partnerId)    │
  │                                    │                                │
  │                                    │ processPaymentNotification()   │
  │                                    ├──────────────────────────────► │
  │                                    │                                │ persist PmtInfo
  │                                    │                                │ BillMaster.status = PAID
  │                                    │                                │ Kafka: payment.received
  │                                    │                                ├───────────────── Kafka
  │                                    │ ◄─ responseCode (I000000)      │
  │                                    │                                │
  │ ◄── SOAP response XML              │
```

### 3 — Reconciliation (Inbound from Tahseel)

Same pattern as payment notification but on `POST /sadad/reconciliation`.
Fires `mop.reconciliation.received` Kafka event for downstream finance systems.

---

## Kafka Topics

| Topic                      | Trigger                           | Consumers (expected)          |
|----------------------------|-----------------------------------|-------------------------------|
| `mop.bill.created`         | New bill created                  | Notification, reporting       |
| `mop.bill.cancelled`       | Bill cancelled                    | Reporting                     |
| `mop.bill.paid`            | Bill marked paid                  | SAP sync, SMS, reporting      |
| `mop.tahseel.sent`         | Tahseel retry succeeded           | Alerting                      |
| `mop.tahseel.failed`       | Tahseel dispatch failed           | Alerting, dead-letter handler |
| `mop.payment.received`     | PmtNotificationAGW processed      | SAP sync, SMS, reporting      |
| `mop.reconciliation.received` | AgencyReconRptAGW processed    | Finance reconciliation        |

---

## API Reference

### Headers required on all `/api/**` endpoints
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

### POST /api/bills — Create a bill
```json
{
  "licNumber": 12345,
  "licType": "M",
  "invId": 67890,
  "billOwnerName": "شركة التعدين",
  "nationalId": "1234567890",
  "poiType": "NI",
  "billCategory": "LICENSE_FEE",
  "displayLabelAr": "رسوم الترخيص",
  "totalAmount": 5000.00,
  "billType": 1,
  "referenceType": 3,
  "dueDate": "2026-06-01T00:00:00",
  "revenueEntries": [
    { "accountNo": "1422101", "amount": 3000.00 },
    { "accountNo": "1422102", "amount": 2000.00 }
  ]
}
```

### GET /api/bills/{billNumber}/status
```json
{
  "success": true,
  "data": {
    "billNumber": 1000000001,
    "billStatus": 0,
    "billStatusLabel": "UNPAID",
    "tahseelReqId": "uuid-returned-by-tahseel"
  }
}
```

### DELETE /api/bills/{billNumber}/cancel
```json
{ "cancelReason": "DUPLICATE", "cancelNote": "Created in error" }
```

---

## Security Model

| Endpoint              | Auth Method         | Who                        |
|-----------------------|---------------------|----------------------------|
| `POST /api/bills`     | JWT Bearer          | ROLE_EMPLOYEE, ROLE_ADMIN  |
| `DELETE /api/bills/*` | JWT Bearer          | ROLE_EMPLOYEE, ROLE_ADMIN  |
| `GET /api/bills/**`   | JWT Bearer          | + ROLE_INVESTOR            |
| `/sadad/**`           | XML Digital Signature (WS-Security X.509) | Tahseel only |

JWT roles mirror the original `UserDetailsServiceImpl`:
- Investor user (`IS_INV_Y_N = Y`) → `ROLE_INVESTOR`, `ROLE_COMPLEXES`
- Employee with no roles → `ROLE_EMPLOYEE`
- Employee with custom roles from `ROLE_DESC` → those exact role names

---

## Tahseel WSDL Stub Setup

The `TahseelGatewayService` contains the full SOAP call structure in comments.
To activate live calls:

1. Run wsimport against the real WSDL:
```bash
wsimport -keep -p com.mop.billing.integration.tahseel.stub \
  https://www.ecp.mof.gov.sa/agw/BillsMngAGW?wsdl
```

2. Place generated stubs in `integration/tahseel/stub/`

3. In `TahseelGatewayService.callTahseel()`, replace the comment blocks
   with the actual instantiations (every field is documented line-by-line
   in the comments).

---

## Configuration (application.yml)

Key properties to set for production:

```yaml
tahseel:
  agency-id:               ${TAHSEEL_AGENCY_ID}
  partner-id:              ${TAHSEEL_PARTNER_ID}
  keystore.file:           /etc/mop/certs/keystore.p12
  keystore.password:       ${TAHSEEL_KEYSTORE_PWD}
  ssl.trust-store:         /etc/mop/certs/truststore.jks

jwt:
  secret:                  ${JWT_SECRET}   # min 32 chars

spring:
  datasource:
    url:      jdbc:oracle:thin:@//host:1521/ORCL
    username: ${DB_USER}
    password: ${DB_PASS}

  kafka:
    bootstrap-servers: ${KAFKA_BROKERS}
```
