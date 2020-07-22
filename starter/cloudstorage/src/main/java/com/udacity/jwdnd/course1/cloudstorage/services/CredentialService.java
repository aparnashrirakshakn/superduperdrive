package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public int insertCredential(Credential credential) {
        return credentialMapper.insertCredential(encryptPassword(credential));
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.updateCredential(encryptPassword(credential));
    }

    public List<Credential> getCredentials(String username) {

        List<Credential> credentials = credentialMapper.selectAllCredentials(userMapper.selectUser(username).getUserid());

        if (credentials == null) {
            return new ArrayList<>();
        }

        for (Credential credential : credentials) {
            credential.setDecryptedPassword(decryptPassword(credential.getPassword(),
                    credential.getKey()));
        }

        return credentials;
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    private Credential encryptPassword(Credential credential) {
        if (credential.getKey() == null) {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            credential.setKey(Base64.getEncoder().encodeToString(key));
        }

        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    private String decryptPassword(String password, String key) {
        return encryptionService.decryptValue(password, key);
    }

}
