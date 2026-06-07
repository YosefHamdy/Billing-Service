package com.mop.billing.data.repository;

import com.mop.billing.data.entity.RevenueEntry;
import com.mop.billing.data.entity.SadadWsDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RevenueEntryRepository extends JpaRepository<SadadWsDet, Integer> {
    List<SadadWsDet> findByBillNo(Long billNo);
}
