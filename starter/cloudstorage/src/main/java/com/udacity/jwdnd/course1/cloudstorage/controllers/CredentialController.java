package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private HashService hashService;

    public CredentialController(CredentialService credentialService, UserService userService, HashService hashService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.hashService = hashService;
    }

    @PostMapping(value = {"/credentials"})
    public String insertCredential(Authentication authentication, Credential credential, Model model) {

        String errorMessage;
        int rowsAffected;

        if (credential.getCredentialid() != null) {
            rowsAffected = credentialService.updateCredential(credential);
        }
        else {
            credential.setUserid(userService.getUser(authentication.getName()).getUserid());
            rowsAffected = credentialService.insertCredential(credential);
        }

        if (rowsAffected <= 0) {
            errorMessage = "An unexpected error occurred. Please try again later.";
            model.addAttribute("errorMessage", errorMessage);
            return "result";
        }

        return "result";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid) {
        credentialService.deleteCredential(credentialid);
        return "result";
    }
}
