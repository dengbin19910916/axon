package com.willowleaf.axon.account;

import com.willowleaf.axon.account.command.AccountCreateCommand;
import com.willowleaf.axon.account.command.AccountDepositCommand;
import com.willowleaf.axon.account.command.AccountWithdrawCommand;
import com.willowleaf.axon.account.event.AccountCreatedEvent;
import com.willowleaf.axon.account.event.AccountDepositedEvent;
import com.willowleaf.axon.account.event.AccountWithdrawnEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@Data
@NoArgsConstructor
@Aggregate
@Entity(name = "tb_account")
public class Account {

    @Id
    private String id;

    private Double deposit;

    @CommandHandler
    public Account(AccountCreateCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId()));
    }

    @CommandHandler
    public void handle(AccountDepositCommand command) {
        apply(new AccountDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(AccountWithdrawCommand command) {
        if (this.deposit >= command.getAmount()) {
            apply(new AccountWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.id = event.getAccountId();
        this.deposit = 0.0;
    }

    @EventSourcingHandler
    public void on(AccountDepositedEvent event) {
        this.deposit += event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountWithdrawnEvent event) {
        this.deposit -= event.getAmount();
    }
}
