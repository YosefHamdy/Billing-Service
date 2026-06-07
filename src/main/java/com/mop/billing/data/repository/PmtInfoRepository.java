package com.mop.billing.data.repository;

import com.mop.billing.data.entity.PmtInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PmtInfoRepository extends JpaRepository<PmtInfo, Integer> {
    Optional<PmtInfo> findByBillNo(String billNo);
    List<PmtInfo> findByReqId(String reqId);
}
