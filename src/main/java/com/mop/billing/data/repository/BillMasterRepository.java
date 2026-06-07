package com.mop.billing.data.repository;

import com.mop.billing.data.entity.BillMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillMasterRepository extends JpaRepository<BillMaster, Long> {

    Optional<BillMaster> findByBillNumber(Long billNumber);

    Integer countByBillDateGBetween(Date today, Date tomorrow);


    List<BillMaster> findByInvIdAndBillStatus(Integer invId, Integer billStatus);

    List<BillMaster> findByLicNumberAndBillStatus(Integer licNumber, Integer billStatus);

    @Query("SELECT b FROM BillMaster b WHERE b.billStatus = 0 AND b.active = 1")
    List<BillMaster> findAllUnpaidActive();

    @Query("SELECT b FROM BillMaster b WHERE b.nationalId = :nationalId ORDER BY b.billDateG DESC")
    List<BillMaster> findByNationalId(String nationalId);
}
