package com.mop.billing.data.repository;

import com.mop.billing.data.entity.SadadWsBills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SadadWsBillsRepository extends JpaRepository<SadadWsBills, Integer> {

    Optional<SadadWsBills> findByBillNo(String billNo);

    /** Bills that have been persisted but not yet successfully dispatched to Tahseel (no reqId) */
    @Query("SELECT s FROM SadadWsBills s WHERE s.reqId IS NULL OR s.reqId = '' ORDER BY s.createdDate ASC")
    List<SadadWsBills> findPendingDispatch();

    List<SadadWsBills> findByBillNoIn(List<String> billNos);
}
