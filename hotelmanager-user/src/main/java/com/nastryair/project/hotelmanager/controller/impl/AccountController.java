package com.nastryair.project.hotelmanager.controller.impl;

import com.nastryair.project.hotelmanager.common.RestMessage;
import com.nastryair.project.hotelmanager.controller.AccountApi;
import com.nastryair.project.hotelmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(
            @RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "rememberMe", required = false, defaultValue = "false") boolean rememberMe
    ) {
        RestMessage restMessage = accountService.login(userName, password, rememberMe);
        return ResponseEntity.ok(restMessage);
    }
}
