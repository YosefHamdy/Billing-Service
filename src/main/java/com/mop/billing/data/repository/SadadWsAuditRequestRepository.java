package com.mop.billing.data.repository;

import com.mop.billing.data.entity.SadadWsAuditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SadadWsAuditRequestRepository extends JpaRepository<SadadWsAuditRequest, Integer> {
    List<SadadWsAuditRequest> findByRequestId(String requestId);
    List<SadadWsAuditRequest> findByFunctionIdOrderByCreatedDateDesc(String functionId);
}
