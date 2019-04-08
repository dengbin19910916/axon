package com.willowleaf.axon.account.query;

import com.willowleaf.axon.account.event.AccountCreatedEvent;
import com.willowleaf.axon.account.event.AccountDepositedEvent;
import com.willowleaf.axon.account.event.AccountWithdrawnEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountProjector {

    private final AccountEntityRepository repository;

    public AccountProjector(AccountEntityRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountEntity account = new AccountEntity();
        account.setId(event.getAccountId());
        repository.save(account);
    }

    @EventHandler
    public void on(AccountDepositedEvent event) {
        Optional<AccountEntity> account = repository.findById(event.getAccountId());
        account.ifPresent(entity -> {
            entity.setDeposit(entity.getDeposit() == null ? 0.0 : entity.getDeposit() + event.getAmount());
            repository.save(entity);
        });
    }

    @EventHandler
    public void on(AccountWithdrawnEvent event) {
        Optional<AccountEntity> account = repository.findById(event.getAccountId());
        account.ifPresent(entity -> {
            entity.setDeposit(entity.getDeposit() - event.getAmount());
            repository.save(entity);
        });
    }
}
