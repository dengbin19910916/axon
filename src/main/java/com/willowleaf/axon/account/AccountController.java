package com.willowleaf.axon.account;

import com.willowleaf.axon.account.command.AccountCreateCommand;
import com.willowleaf.axon.account.command.AccountDepositCommand;
import com.willowleaf.axon.account.command.AccountWithdrawCommand;
import com.willowleaf.axon.account.query.AccountEntity;
import com.willowleaf.axon.account.query.AccountEntityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CommandGateway commandGateway;
    private final AccountEntityRepository repository;

    @Autowired
    public AccountController(CommandGateway commandGateway, AccountEntityRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    /**
     * 创建账户。
     */
    @PostMapping
    public CompletableFuture<Object> crate() {
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand command = new AccountCreateCommand(accountId.toString().replaceAll("-", ""));
        return commandGateway.send(command);
    }

    /**
     * 存款。
     */
    @PutMapping("/{accountId}/deposit/{amount}")
    public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
        commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    /**
     * 取款。
     */
    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }

    @GetMapping
    public List<AccountEntity> all() {
        return repository.findAll();
    }
}
