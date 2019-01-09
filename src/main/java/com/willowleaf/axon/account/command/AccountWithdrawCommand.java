package com.willowleaf.axon.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class AccountWithdrawCommand {

    @TargetAggregateIdentifier
    private String accountId;

    private Double amount;
}
