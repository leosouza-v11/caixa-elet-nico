package com.cash.machine.api.routers;

import com.cash.machine.api.classes.HttpCode;
import com.cash.machine.api.classes.Session;
import com.cash.machine.api.controllers.AccountController;
import com.cash.machine.api.models.database.DatabaseAccount;
import com.cash.machine.api.models.request.RequestLogin;
import com.cash.machine.api.models.request.RequestRegister;
import com.cash.machine.api.models.response.HttpResponse;
import com.cash.machine.api.validators.StringValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {
    
    private Session session = Session.getInstance();
    private AccountController accountController = AccountController.getInstance();

    @CrossOrigin
    @PostMapping("/auth/login")
    public ResponseEntity<HttpResponse> login(@RequestBody RequestLogin login) {
        HttpResponse response = HttpResponse.build();
        String validatorMessage = "";
        
        if (login.getAccount() <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta e Obrigatório.").responseGenerator();
        }

        validatorMessage = StringValidator.isValidString(login.getPassword(), "Senha", 12, 6);
        
        if (!validatorMessage.isEmpty()) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage(validatorMessage).responseGenerator();
        }

        try {
            DatabaseAccount account = this.accountController.getDataByID(login.getAccount());

            if (!account.getPassword().equals(login.getPassword())) {
                return response.setCode(HttpCode.UNAUTHORIZED).setMessage("Senha Incorreta. Tente Novamente.").responseGenerator();
            }

            this.session.startSession(account);

            return response.setCode(HttpCode.OK).setMessage("Sessão Iniciada com Sucesso.").setStatus(true).responseGenerator();

        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }

    @CrossOrigin
    @GetMapping("/auth/logout")
    public ResponseEntity<HttpResponse> logout(@RequestHeader int id) {
        HttpResponse response = HttpResponse.build();

        if (id <= 0) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage("Conta e Obrigatório.").responseGenerator();
        }

        try {
            this.session.getSession(id);
            this.session.endSession(id);
            return response.setCode(HttpCode.OK).setMessage("Sessão Finalizada com Sucesso.").setStatus(true).responseGenerator();
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }
    }

    @CrossOrigin
    @PostMapping("/auth/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RequestRegister register) {
        HttpResponse response = HttpResponse.build();
        String validatorMessage = "";

        validatorMessage = StringValidator.isValidString(register.getUsername(), "Nome", 80, 3);

        if (!validatorMessage.isEmpty()) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage(validatorMessage).responseGenerator();
        }

        validatorMessage = StringValidator.isValidString(register.getPassword(), "Senha", 12, 6);
        
        if (!validatorMessage.isEmpty()) {
            return response.setCode(HttpCode.BAD_REQUEST).setMessage(validatorMessage).responseGenerator();
        }

        try {
            DatabaseAccount account = DatabaseAccount.build()
                                                    .setUsername(register.getUsername())
                                                    .setPassword(register.getPassword())
                                                    .setAvailableBalance(0);
            int id = this.accountController.insert(account);
            return response.setCode(HttpCode.CREATED).setMessage("Conta Criada com Sucesso.").setStatus(true).setResponse(id).responseGenerator();
        } catch (Exception e) {
            return response.setCode(HttpCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage()).setResponse(e).responseGenerator();
        }

    }

}
