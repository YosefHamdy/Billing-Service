package com.mop.billing.data.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "SADAD_PAY_MASTER1")
public class PayMaster implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "NAME_L")
    private String nameL;
    @Column(name = "ACCOUNT_NO")
    private String accountNumber;
    @Column(name = "VALUE")
    private Double value;
    @Column(name = "PARENT_ACNO")
    private String parentAccountNumber;
    @Column(name = "PAY_TYPE")
    private Integer payTypeColm;
}
