package com.willowleaf.axon.account.query;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "tb_account")
public class AccountEntity {

    @Id
    private String id;

    private Double deposit;
}
