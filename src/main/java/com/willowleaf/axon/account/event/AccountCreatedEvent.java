package com.willowleaf.axon.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreatedEvent {

    private String accountId;
}
