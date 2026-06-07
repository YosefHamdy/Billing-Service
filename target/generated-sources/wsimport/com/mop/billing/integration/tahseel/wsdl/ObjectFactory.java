
package com.mop.billing.integration.tahseel.wsdl;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mop.billing.integration.tahseel.wsdl package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _BillsMngAGWRq_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillsMngAGWRq");
    private static final QName _BillsMngAGWRs_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillsMngAGWRs");
    private static final QName _BillInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillInfo");
    private static final QName _BillList_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillList");
    private static final QName _Status_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "Status");
    private static final QName _ClientDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "ClientDt");
    private static final QName _EchoData_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "EchoData");
    private static final QName _FuncId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "FuncId");
    private static final QName _MsgRecDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MsgRecDt");
    private static final QName _PartyIdNum_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartyIdNum");
    private static final QName _PartyIdType_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartyIdType");
    private static final QName _RqMode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "RqMode");
    private static final QName _RqUID_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "RqUID");
    private static final QName _SCId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "SCId");
    private static final QName _SPRefNum_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "SPRefNum");
    private static final QName _StatusCode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "StatusCode");
    private static final QName _StatusDesc_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "StatusDesc");
    private static final QName _Version_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "Version");
    private static final QName _UserId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "UserId");
    private static final QName _ProxyUserId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "ProxyUserId");
    private static final QName _PartyId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartyId");
    private static final QName _MsgRqHdr_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MsgRqHdr");
    private static final QName _MsgRsHdr_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MsgRsHdr");
    private static final QName _DueDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "DueDt");
    private static final QName _PresDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PresDt");
    private static final QName _ActionDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "ActionDt");
    private static final QName _BillAcct_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillAcct");
    private static final QName _BillNum_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillNum");
    private static final QName _BillCycle_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillCycle");
    private static final QName _BenPOI_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BenPOI");
    private static final QName _BenName_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BenName");
    private static final QName _BillStatusCode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillStatusCode");
    private static final QName _BillAction_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillAction");
    private static final QName _BillSummList_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillSummList");
    private static final QName _BillSummInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillSummInfo");
    private static final QName _BillCategory_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillCategory");
    private static final QName _BillAmt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillAmt");
    private static final QName _Amt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "Amt");
    private static final QName _ExpDt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "ExpDt");
    private static final QName _BillRefInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillRefInfo");
    private static final QName _BillDesc_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillDesc");
    private static final QName _AmtCode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "AmtCode");
    private static final QName _POINum_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "POINum");
    private static final QName _POIType_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "POIType");
    private static final QName _AgencyId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "AgencyId");
    private static final QName _PartnerType_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartnerType");
    private static final QName _PartnerId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartnerId");
    private static final QName _PartnerInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartnerInfo");
    private static final QName _PartnerCode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PartnerCode");
    private static final QName _BatchId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BatchId");
    private static final QName _RevenueEntryList_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "RevenueEntryList");
    private static final QName _RevenueEntryInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "RevenueEntryInfo");
    private static final QName _ActionReason_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "ActionReason");
    private static final QName _GFSCode_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "GFSCode");
    private static final QName _BenAgencyId_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BenAgencyId");
    private static final QName _DisplayLabelAr_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "DisplayLabelAr");
    private static final QName _DisplayLabelEn_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "DisplayLabelEn");
    private static final QName _BenInfo_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BenInfo");
    private static final QName _MinPartialPmtAmt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MinPartialPmtAmt");
    private static final QName _MinAdvancePmtAmt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MinAdvancePmtAmt");
    private static final QName _MaxAdvancePmtAmt_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "MaxAdvancePmtAmt");
    private static final QName _PmtOptions_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "PmtOptions");
    private static final QName _BillIBAN_QNAME = new QName("https://www.ecp.mof.gov.sa/agw", "BillIBAN");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mop.billing.integration.tahseel.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BillsMngAGWRqType }
     * 
     * @return
     *     the new instance of {@link BillsMngAGWRqType }
     */
    public BillsMngAGWRqType createBillsMngAGWRqType() {
        return new BillsMngAGWRqType();
    }

    /**
     * Create an instance of {@link BillsMngAGWRsType }
     * 
     * @return
     *     the new instance of {@link BillsMngAGWRsType }
     */
    public BillsMngAGWRsType createBillsMngAGWRsType() {
        return new BillsMngAGWRsType();
    }

    /**
     * Create an instance of {@link BillInfoType }
     * 
     * @return
     *     the new instance of {@link BillInfoType }
     */
    public BillInfoType createBillInfoType() {
        return new BillInfoType();
    }

    /**
     * Create an instance of {@link BillListType }
     * 
     * @return
     *     the new instance of {@link BillListType }
     */
    public BillListType createBillListType() {
        return new BillListType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     * @return
     *     the new instance of {@link StatusType }
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link PartyIdType }
     * 
     * @return
     *     the new instance of {@link PartyIdType }
     */
    public PartyIdType createPartyIdType() {
        return new PartyIdType();
    }

    /**
     * Create an instance of {@link MsgRqHdrType }
     * 
     * @return
     *     the new instance of {@link MsgRqHdrType }
     */
    public MsgRqHdrType createMsgRqHdrType() {
        return new MsgRqHdrType();
    }

    /**
     * Create an instance of {@link MsgRsHdrType }
     * 
     * @return
     *     the new instance of {@link MsgRsHdrType }
     */
    public MsgRsHdrType createMsgRsHdrType() {
        return new MsgRsHdrType();
    }

    /**
     * Create an instance of {@link POIType }
     * 
     * @return
     *     the new instance of {@link POIType }
     */
    public POIType createPOIType() {
        return new POIType();
    }

    /**
     * Create an instance of {@link BillSummListType }
     * 
     * @return
     *     the new instance of {@link BillSummListType }
     */
    public BillSummListType createBillSummListType() {
        return new BillSummListType();
    }

    /**
     * Create an instance of {@link BillSummInfoType }
     * 
     * @return
     *     the new instance of {@link BillSummInfoType }
     */
    public BillSummInfoType createBillSummInfoType() {
        return new BillSummInfoType();
    }

    /**
     * Create an instance of {@link PartnerInfoType }
     * 
     * @return
     *     the new instance of {@link PartnerInfoType }
     */
    public PartnerInfoType createPartnerInfoType() {
        return new PartnerInfoType();
    }

    /**
     * Create an instance of {@link RevenueEntryListType }
     * 
     * @return
     *     the new instance of {@link RevenueEntryListType }
     */
    public RevenueEntryListType createRevenueEntryListType() {
        return new RevenueEntryListType();
    }

    /**
     * Create an instance of {@link RevenueEntryInfoType }
     * 
     * @return
     *     the new instance of {@link RevenueEntryInfoType }
     */
    public RevenueEntryInfoType createRevenueEntryInfoType() {
        return new RevenueEntryInfoType();
    }

    /**
     * Create an instance of {@link BenInfoType }
     * 
     * @return
     *     the new instance of {@link BenInfoType }
     */
    public BenInfoType createBenInfoType() {
        return new BenInfoType();
    }

    /**
     * Create an instance of {@link PmtOptionsType }
     * 
     * @return
     *     the new instance of {@link PmtOptionsType }
     */
    public PmtOptionsType createPmtOptionsType() {
        return new PmtOptionsType();
    }

    /**
     * Create an instance of {@link BillsMngAGWRqBodyType }
     * 
     * @return
     *     the new instance of {@link BillsMngAGWRqBodyType }
     */
    public BillsMngAGWRqBodyType createBillsMngAGWRqBodyType() {
        return new BillsMngAGWRqBodyType();
    }

    /**
     * Create an instance of {@link BillsMngAGWRsBodyType }
     * 
     * @return
     *     the new instance of {@link BillsMngAGWRsBodyType }
     */
    public BillsMngAGWRsBodyType createBillsMngAGWRsBodyType() {
        return new BillsMngAGWRsBodyType();
    }

    /**
     * Create an instance of {@link BillRecType }
     * 
     * @return
     *     the new instance of {@link BillRecType }
     */
    public BillRecType createBillRecType() {
        return new BillRecType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillsMngAGWRqType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillsMngAGWRqType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillsMngAGWRq")
    public JAXBElement<BillsMngAGWRqType> createBillsMngAGWRq(BillsMngAGWRqType value) {
        return new JAXBElement<>(_BillsMngAGWRq_QNAME, BillsMngAGWRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillsMngAGWRsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillsMngAGWRsType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillsMngAGWRs")
    public JAXBElement<BillsMngAGWRsType> createBillsMngAGWRs(BillsMngAGWRsType value) {
        return new JAXBElement<>(_BillsMngAGWRs_QNAME, BillsMngAGWRsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillInfoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillInfoType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillInfo")
    public JAXBElement<BillInfoType> createBillInfo(BillInfoType value) {
        return new JAXBElement<>(_BillInfo_QNAME, BillInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillListType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillList")
    public JAXBElement<BillListType> createBillList(BillListType value) {
        return new JAXBElement<>(_BillList_QNAME, BillListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "Status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<>(_Status_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "ClientDt")
    public JAXBElement<XMLGregorianCalendar> createClientDt(XMLGregorianCalendar value) {
        return new JAXBElement<>(_ClientDt_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "EchoData")
    public JAXBElement<String> createEchoData(String value) {
        return new JAXBElement<>(_EchoData_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "FuncId")
    public JAXBElement<String> createFuncId(String value) {
        return new JAXBElement<>(_FuncId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MsgRecDt")
    public JAXBElement<String> createMsgRecDt(String value) {
        return new JAXBElement<>(_MsgRecDt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartyIdNum")
    public JAXBElement<String> createPartyIdNum(String value) {
        return new JAXBElement<>(_PartyIdNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartyIdType")
    public JAXBElement<String> createPartyIdType(String value) {
        return new JAXBElement<>(_PartyIdType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "RqMode")
    public JAXBElement<Long> createRqMode(Long value) {
        return new JAXBElement<>(_RqMode_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "RqUID")
    public JAXBElement<String> createRqUID(String value) {
        return new JAXBElement<>(_RqUID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SCIdType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SCIdType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "SCId")
    public JAXBElement<SCIdType> createSCId(SCIdType value) {
        return new JAXBElement<>(_SCId_QNAME, SCIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "SPRefNum")
    public JAXBElement<String> createSPRefNum(String value) {
        return new JAXBElement<>(_SPRefNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "StatusCode")
    public JAXBElement<String> createStatusCode(String value) {
        return new JAXBElement<>(_StatusCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "StatusDesc")
    public JAXBElement<String> createStatusDesc(String value) {
        return new JAXBElement<>(_StatusDesc_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "Version")
    public JAXBElement<String> createVersion(String value) {
        return new JAXBElement<>(_Version_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "UserId")
    public JAXBElement<String> createUserId(String value) {
        return new JAXBElement<>(_UserId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "ProxyUserId")
    public JAXBElement<String> createProxyUserId(String value) {
        return new JAXBElement<>(_ProxyUserId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartyIdType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartyIdType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartyId")
    public JAXBElement<PartyIdType> createPartyId(PartyIdType value) {
        return new JAXBElement<>(_PartyId_QNAME, PartyIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgRqHdrType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MsgRqHdrType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MsgRqHdr")
    public JAXBElement<MsgRqHdrType> createMsgRqHdr(MsgRqHdrType value) {
        return new JAXBElement<>(_MsgRqHdr_QNAME, MsgRqHdrType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MsgRsHdrType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MsgRsHdrType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MsgRsHdr")
    public JAXBElement<MsgRsHdrType> createMsgRsHdr(MsgRsHdrType value) {
        return new JAXBElement<>(_MsgRsHdr_QNAME, MsgRsHdrType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "DueDt")
    public JAXBElement<String> createDueDt(String value) {
        return new JAXBElement<>(_DueDt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PresDt")
    public JAXBElement<String> createPresDt(String value) {
        return new JAXBElement<>(_PresDt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "ActionDt")
    public JAXBElement<String> createActionDt(String value) {
        return new JAXBElement<>(_ActionDt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillAcct")
    public JAXBElement<String> createBillAcct(String value) {
        return new JAXBElement<>(_BillAcct_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillNum")
    public JAXBElement<String> createBillNum(String value) {
        return new JAXBElement<>(_BillNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillCycle")
    public JAXBElement<String> createBillCycle(String value) {
        return new JAXBElement<>(_BillCycle_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link POIType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link POIType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BenPOI")
    public JAXBElement<POIType> createBenPOI(POIType value) {
        return new JAXBElement<>(_BenPOI_QNAME, POIType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BenName")
    public JAXBElement<String> createBenName(String value) {
        return new JAXBElement<>(_BenName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillStatusCodeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillStatusCodeType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillStatusCode")
    public JAXBElement<BillStatusCodeType> createBillStatusCode(BillStatusCodeType value) {
        return new JAXBElement<>(_BillStatusCode_QNAME, BillStatusCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillActionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillActionType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillAction")
    public JAXBElement<BillActionType> createBillAction(BillActionType value) {
        return new JAXBElement<>(_BillAction_QNAME, BillActionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillSummListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillSummListType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillSummList")
    public JAXBElement<BillSummListType> createBillSummList(BillSummListType value) {
        return new JAXBElement<>(_BillSummList_QNAME, BillSummListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillSummInfoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BillSummInfoType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillSummInfo")
    public JAXBElement<BillSummInfoType> createBillSummInfo(BillSummInfoType value) {
        return new JAXBElement<>(_BillSummInfo_QNAME, BillSummInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillCategory")
    public JAXBElement<String> createBillCategory(String value) {
        return new JAXBElement<>(_BillCategory_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillAmt")
    public JAXBElement<BigDecimal> createBillAmt(BigDecimal value) {
        return new JAXBElement<>(_BillAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "Amt")
    public JAXBElement<BigDecimal> createAmt(BigDecimal value) {
        return new JAXBElement<>(_Amt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "ExpDt")
    public JAXBElement<String> createExpDt(String value) {
        return new JAXBElement<>(_ExpDt_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillRefInfo")
    public JAXBElement<String> createBillRefInfo(String value) {
        return new JAXBElement<>(_BillRefInfo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillDesc")
    public JAXBElement<String> createBillDesc(String value) {
        return new JAXBElement<>(_BillDesc_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AmtCodeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AmtCodeType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "AmtCode")
    public JAXBElement<AmtCodeType> createAmtCode(AmtCodeType value) {
        return new JAXBElement<>(_AmtCode_QNAME, AmtCodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "POINum")
    public JAXBElement<String> createPOINum(String value) {
        return new JAXBElement<>(_POINum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link POITypeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link POITypeType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "POIType")
    public JAXBElement<POITypeType> createPOIType(POITypeType value) {
        return new JAXBElement<>(_POIType_QNAME, POITypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "AgencyId")
    public JAXBElement<String> createAgencyId(String value) {
        return new JAXBElement<>(_AgencyId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerTypeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartnerTypeType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartnerType")
    public JAXBElement<PartnerTypeType> createPartnerType(PartnerTypeType value) {
        return new JAXBElement<>(_PartnerType_QNAME, PartnerTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartnerId")
    public JAXBElement<String> createPartnerId(String value) {
        return new JAXBElement<>(_PartnerId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerInfoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PartnerInfoType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartnerInfo")
    public JAXBElement<PartnerInfoType> createPartnerInfo(PartnerInfoType value) {
        return new JAXBElement<>(_PartnerInfo_QNAME, PartnerInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PartnerCode")
    public JAXBElement<String> createPartnerCode(String value) {
        return new JAXBElement<>(_PartnerCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BatchId")
    public JAXBElement<String> createBatchId(String value) {
        return new JAXBElement<>(_BatchId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevenueEntryListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RevenueEntryListType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "RevenueEntryList")
    public JAXBElement<RevenueEntryListType> createRevenueEntryList(RevenueEntryListType value) {
        return new JAXBElement<>(_RevenueEntryList_QNAME, RevenueEntryListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevenueEntryInfoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RevenueEntryInfoType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "RevenueEntryInfo")
    public JAXBElement<RevenueEntryInfoType> createRevenueEntryInfo(RevenueEntryInfoType value) {
        return new JAXBElement<>(_RevenueEntryInfo_QNAME, RevenueEntryInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "ActionReason")
    public JAXBElement<String> createActionReason(String value) {
        return new JAXBElement<>(_ActionReason_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "GFSCode")
    public JAXBElement<String> createGFSCode(String value) {
        return new JAXBElement<>(_GFSCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BenAgencyId")
    public JAXBElement<String> createBenAgencyId(String value) {
        return new JAXBElement<>(_BenAgencyId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "DisplayLabelAr")
    public JAXBElement<String> createDisplayLabelAr(String value) {
        return new JAXBElement<>(_DisplayLabelAr_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "DisplayLabelEn")
    public JAXBElement<String> createDisplayLabelEn(String value) {
        return new JAXBElement<>(_DisplayLabelEn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BenInfoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BenInfoType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BenInfo")
    public JAXBElement<BenInfoType> createBenInfo(BenInfoType value) {
        return new JAXBElement<>(_BenInfo_QNAME, BenInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MinPartialPmtAmt")
    public JAXBElement<BigDecimal> createMinPartialPmtAmt(BigDecimal value) {
        return new JAXBElement<>(_MinPartialPmtAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MinAdvancePmtAmt")
    public JAXBElement<BigDecimal> createMinAdvancePmtAmt(BigDecimal value) {
        return new JAXBElement<>(_MinAdvancePmtAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "MaxAdvancePmtAmt")
    public JAXBElement<BigDecimal> createMaxAdvancePmtAmt(BigDecimal value) {
        return new JAXBElement<>(_MaxAdvancePmtAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PmtOptionsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PmtOptionsType }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "PmtOptions")
    public JAXBElement<PmtOptionsType> createPmtOptions(PmtOptionsType value) {
        return new JAXBElement<>(_PmtOptions_QNAME, PmtOptionsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.ecp.mof.gov.sa/agw", name = "BillIBAN")
    public JAXBElement<String> createBillIBAN(String value) {
        return new JAXBElement<>(_BillIBAN_QNAME, String.class, null, value);
    }

}
