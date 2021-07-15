package com.xrpdev.xrpgenerator;

public class AccountData {

    private String account;
    private String secret;

    public AccountData(String account, String secret) {
        this.account = account;
        this.secret = secret;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
