package com.mop.billing.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Minimal User entity for security purposes — mapped to USER_DATA table.
 * Only the fields required for authentication are loaded.
 */
@Entity
@Table(name = "USER_DATA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USER_NAME",     nullable = false, unique = true)
    private String  userName;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String  userPassword;

    @Column(name = "USER_REALNAME")
    private String  userRealname;

    @Column(name = "USER_EMAIL")
    private String  email;

    @Column(name = "USER_MOBILE")
    private String  userMobile;

    @Column(name = "IS_INV_Y_N")
    private String  isInvestor;

    @Column(name = "INV_ID")
    private Integer invId;

    @Column(name = "ACTIVE",            nullable = false)
    private boolean active           = true;

    @Column(name = "ACCOUNT_NON_LOCKED", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "FAILED_ATTEMPT",    nullable = false)
    private int     failedAttempt    = 0;

    @Column(name = "LOCK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    @Column(name = "LAST_LOGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns        = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();
}
