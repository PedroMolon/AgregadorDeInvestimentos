package com.pedromolon.agregadordeinvestimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "billingaddresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddress {

    @Id
    @Column(name = "account_id")
    private Long billingAddressId;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

}
