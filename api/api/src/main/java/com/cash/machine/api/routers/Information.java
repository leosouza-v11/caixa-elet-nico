package com.cash.machine.api.routers;

import java.util.ArrayList;
import java.util.List;

import com.cash.machine.api.classes.HttpCode;
import com.cash.machine.api.classes.Session;
import com.cash.machine.api.controllers.AccountController;
import com.cash.machine.api.controllers.TransactionController;
import com.cash.machine.api.models.database.DatabaseAccount;
import com.cash.machine.api.models.database.DatabaseTransaction;
import com.cash.machine.api.models.response.AccountResponse;
import com.cash.machine.api.models.response.HttpResponse;
import com.cash.machine.api.models.response.TransactionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Information {
    private Session session = Session.getInstance();
    private AccountController accountController = AccountController.getInstance();
    private TransactionController transactionController = TransactionController.getInstance();

    @CrossOrigin
    @GetMapping("/info/account")
    public ResponseEntity<HttpResponse> account(@RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        if (id <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta e Obrigatório.").responseGenerator();
        }

        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());

            AccountResponse accountResponse = AccountResponse.build()
                                                                .setAccount(account.getAccount())
                                                                .setAvailableBalance(account.getAvailableBalance())
                                                                .setUsername(account.getUsername());

            return response.setCode(HttpCode.OK).setMessage("Sucesso em Carregar Conta.").setStatus(true).setResponse(accountResponse).responseGenerator();
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }

    @CrossOrigin
    @GetMapping("/info/transaction")
    public ResponseEntity<HttpResponse> transaction(@RequestParam int code, @RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        if (code <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Código da Transação e Obrigatório.").responseGenerator();
        }

        if (id <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta e Obrigatório.").responseGenerator();
        }

        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());
            DatabaseTransaction transaction = this.transactionController.getDataByID(code);
            DatabaseAccount targeAccount = null;


            if (transaction.getAccount() != account.getAccount()) {
                return response.setCode(HttpCode.BAD_REQUEST).setMessage("Transação Não Pertence a Essa Conta.").responseGenerator();
            }

            if (transaction.getTargetAccount() != 0) {
                targeAccount = this.accountController.getDataByID(transaction.getTargetAccount());
            }

            TransactionResponse transactionResponse = TransactionResponse.build()
                                                                            .setId(transaction.getId())
                                                                            .setAccount(account.getAccount())
                                                                            .setAccountUsername(account.getUsername())
                                                                            .setAvailableBalance(transaction.getAvailableBalance())
                                                                            .setAvailableBalanceAfeterOperation(transaction.getAvailableBalanceAfeterOperation())
                                                                            .setDateTime(transaction.getDateTime())
                                                                            .setLog(transaction.getLog())
                                                                            .setOperation(transaction.getOperation())
                                                                            .setOperationValue(transaction.getOperationValue())
                                                                            .setStatus(transaction.getStatus());
            
            if (targeAccount != null) {
                transactionResponse.setTargetAccount(targeAccount.getAccount()).setTargetAccountUsername(targeAccount.getUsername());
            }

            return response.setCode(HttpCode.OK).setMessage("Sucesso em Carregar Transação.").setStatus(true).setResponse(transactionResponse).responseGenerator();
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }

    @CrossOrigin
    @GetMapping("/info/transactions")
    public ResponseEntity<HttpResponse> transactions(@RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        if (id <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta e Obrigatório.").responseGenerator();
        }

        try {
            DatabaseAccount session = this.session.getSession(id);
            DatabaseAccount account = this.accountController.getDataByID(session.getAccount());
            List<DatabaseTransaction> transactions = this.transactionController.getAllDataByAccount(id);

            List<TransactionResponse> transactionResponses = new ArrayList<TransactionResponse>();

            for (DatabaseTransaction transaction : transactions) {
                DatabaseAccount targeAccount = null;

                if (transaction.getTargetAccount() != 0) {
                    targeAccount = this.accountController.getDataByID(transaction.getTargetAccount());
                }
    
                TransactionResponse transactionResponse = TransactionResponse.build()
                                                                            .setId(transaction.getId())
                                                                            .setAccount(account.getAccount())
                                                                            .setAccountUsername(account.getUsername())
                                                                            .setAvailableBalance(transaction.getAvailableBalance())
                                                                            .setAvailableBalanceAfeterOperation(transaction.getAvailableBalanceAfeterOperation())
                                                                            .setDateTime(transaction.getDateTime())
                                                                            .setLog(transaction.getLog())
                                                                            .setOperation(transaction.getOperation())
                                                                            .setOperationValue(transaction.getOperationValue())
                                                                            .setStatus(transaction.getStatus());
                                                                            

                if (targeAccount != null) {
                    transactionResponse.setTargetAccount(targeAccount.getAccount()).setTargetAccountUsername(targeAccount.getUsername());
                }

                transactionResponses.add(transactionResponse);
            }

            return response.setCode(HttpCode.OK).setMessage("Sucesso em Carregar Transação.").setStatus(true).setResponse(transactionResponses).responseGenerator();
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }
}
