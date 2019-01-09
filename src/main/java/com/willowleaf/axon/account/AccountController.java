package com.willowleaf.axon.account;

import com.willowleaf.axon.account.command.AccountCreateCommand;
import com.willowleaf.axon.account.command.AccountDepositCommand;
import com.willowleaf.axon.account.command.AccountWithdrawCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CommandGateway commandGateway;

    public AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<Object> crate() {
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand command = new AccountCreateCommand(accountId.toString().replaceAll("-", ""));
        return commandGateway.send(command);
    }

    @PutMapping("/{accountId}/deposit/{amount}")
    public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
        commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdraw(@PathVariable String accountId, @PathVariable Double amount) {
        return commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }
}
