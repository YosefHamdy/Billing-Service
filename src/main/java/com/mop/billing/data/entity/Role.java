package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROLE_DESC")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "ROLE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ROLE_NAME", nullable = false, unique = true)
    private String roleName;

    @Column(name = "ROLE_DESC")
    private String roleDesc;

    @Column(name = "ROLE_DESC_EN")
    private String roleDescEn;
}
