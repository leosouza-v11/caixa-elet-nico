package com.cash.machine.api.routers;

import com.cash.machine.api.classes.HttpCode;
import com.cash.machine.api.classes.Session;
import com.cash.machine.api.controllers.AccountController;
import com.cash.machine.api.controllers.TransactionController;
import com.cash.machine.api.models.database.DatabaseAccount;
import com.cash.machine.api.models.database.DatabaseTransaction;
import com.cash.machine.api.models.request.RequestTrasnfer;
import com.cash.machine.api.models.response.HttpResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Operation {
    private Session session = Session.getInstance();
    private AccountController accountController = AccountController.getInstance();
    private TransactionController transactionController = TransactionController.getInstance();
    
    @CrossOrigin
    @PostMapping("/operation/withdraw")
    public ResponseEntity<HttpResponse> withdraw(@RequestParam double withdraw, @RequestHeader int id) {
        HttpResponse response = HttpResponse.build();
        
        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());

            if (withdraw <= 0) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("O Valor de Saque Deve ser Maior que 0.").responseGenerator();
            }

            DatabaseTransaction transaction = DatabaseTransaction.build()
                                                                    .setAccount(account.getAccount())
                                                                    .setOperation("Saque")
                                                                    .setStatus("Processando Operação")
                                                                    .setAvailableBalance(account.getAvailableBalance())
                                                                    .setOperationValue(withdraw);

            transaction.setId(this.transactionController.insert(transaction));
            try {
                if (account.getAvailableBalance() < withdraw) {
                    transaction.setStatus("Falha na Operação").setLog("Saldo Insuficiente Para Saque.");
                    this.transactionController.update(transaction);
                    return response.setCode(HttpCode.BAD_REQUEST).setMessage("Saldo Insuficiente Para Saque.").responseGenerator();
                }

                account.setAvailableBalance(account.getAvailableBalance() - withdraw);
                
                this.accountController.update(account);

                transaction.setAvailableBalanceAfeterOperation(account.getAvailableBalance()).setStatus("Sucesso na Operação");

                this.transactionController.update(transaction);

                return response.setCode(HttpCode.OK).setMessage("Saque Efetuado com Sucesso.").setStatus(true).setResponse(transaction).responseGenerator();
            } catch (Exception e) {

                transaction.setStatus("Falha na Operação").setLog(e.getMessage());
                this.transactionController.update(transaction);

                return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
            }
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }

    @CrossOrigin
    @PostMapping("/operation/deposit")
    public ResponseEntity<HttpResponse> deposit(@RequestParam double deposit, @RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());

            if (deposit <= 0) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("O Valor de Depósito Deve ser Maior que 0.").responseGenerator();
            }

            DatabaseTransaction transaction = DatabaseTransaction.build()
                                                                    .setAccount(account.getAccount())
                                                                    .setOperation("Depósito")
                                                                    .setStatus("Processando Operação")
                                                                    .setAvailableBalance(account.getAvailableBalance())
                                                                    .setOperationValue(deposit);

            transaction.setId(this.transactionController.insert(transaction));
            try {
                account.setAvailableBalance(account.getAvailableBalance() + deposit);
                
                this.accountController.update(account);

                transaction.setAvailableBalanceAfeterOperation(account.getAvailableBalance()).setStatus("Sucesso na Operação");

                this.transactionController.update(transaction);

                return response.setCode(HttpCode.OK).setMessage("Depósito Efetuado com Sucesso.").setStatus(true).setResponse(transaction).responseGenerator();
            } catch (Exception e) {
                transaction.setStatus("Falha na Operação").setLog(e.getMessage());
                this.transactionController.update(transaction);

                return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
            }
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }
    
    @CrossOrigin
    @PostMapping("/operation/transfer")
    public ResponseEntity<HttpResponse> transfer(@RequestBody RequestTrasnfer trasnfer, @RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());

            if (trasnfer.getValue() <= 0) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("O Valor de Transferência Deve ser Maior que 0.").responseGenerator();
            }
    
            if (trasnfer.getTargetAccount() <= 0) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta de Destino Invalida. Tente Novamente.").responseGenerator();
            }

            if (trasnfer.getTargetAccount() == id) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta de Destino Igual a Conta Usada. Tente Novamente.").responseGenerator();
            }

            DatabaseAccount targeAccount = this.accountController.getDataByID(trasnfer.getTargetAccount());

            DatabaseTransaction transaction = DatabaseTransaction.build()
                                                                    .setAccount(account.getAccount())
                                                                    .setTargetAccount(targeAccount.getAccount())
                                                                    .setOperation("Transferência")
                                                                    .setStatus("Processando Operação")
                                                                    .setAvailableBalance(account.getAvailableBalance())
                                                                    .setOperationValue(trasnfer.getValue());
        
            transaction.setId(this.transactionController.insert(transaction));

            try {
                
                if (account.getAvailableBalance() < trasnfer.getValue()) {
                    transaction.setStatus("Falha na Operação").setLog("Saldo Insuficiente Para Transferência.");
                    this.transactionController.update(transaction);
                    return response.setCode(HttpCode.BAD_REQUEST).setMessage("Saldo Insuficiente Para Transferência.").responseGenerator();
                }

                account.setAvailableBalance(account.getAvailableBalance() - trasnfer.getValue());
                targeAccount.setAvailableBalance(targeAccount.getAvailableBalance() + trasnfer.getValue());

                this.accountController.update(account);
                this.accountController.update(targeAccount);

                transaction.setAvailableBalanceAfeterOperation(account.getAvailableBalance()).setStatus("Sucesso na Operação");

                this.transactionController.update(transaction);

                return response.setCode(HttpCode.OK).setMessage("Transferência Efetuado com Sucesso.").setStatus(true).setResponse(transaction).responseGenerator();

            } catch (Exception e) {
                transaction.setStatus("Falha na Operação").setLog(e.getMessage());
                this.transactionController.update(transaction);

                return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
            }

        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }
}
