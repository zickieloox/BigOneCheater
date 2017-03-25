package com.zic.bigonecheater.data;

public class Account {

    private String fid, password;
    private int lineNumber;

    public Account(int lineNumber, String fid) {
        this.lineNumber = lineNumber;
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
