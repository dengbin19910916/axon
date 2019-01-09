package com.willowleaf.axon.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class AccountCreateCommand {

    @TargetAggregateIdentifier
    private String accountId;
}
