package com.mop.billing.data.repository;

import java.util.Optional;


import com.mop.billing.data.entity.PayMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayMasterRepository extends JpaRepository<PayMaster, Long> {
    boolean existsByParentAccountNumber(String parentAccountNumber);
}
