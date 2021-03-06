package com.example.finalproject.Domain;

import java.io.Serializable;
import java.util.ArrayList;

/*
    FORMAT OF THE USER
 */

public class userDomain implements Serializable {

    private String username,password,lastLog,alias,data;
    private ArrayList<String> search_log;
    private boolean logged;

    public userDomain(String username, String password, String lastLog, String alias, String data) {
        this.username = username;
        this.password = password;
        this.lastLog = lastLog;
        this.alias = alias;
        this.data = data;
        this.logged = true;
    }
    public userDomain() {
        this.username ="NO_NAME";
        this.password ="";
        this.lastLog ="NO_DATA";
        this.alias = "NO_DATA";
        this.data ="";
        this.logged = false;
        this.search_log = new ArrayList<>();
    }
    public void log(){
        this.logged = true;
    }
    public boolean isLogged(){
        return logged;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastLog() {
        return lastLog;
    }

    public String getAlias() {
        return alias;
    }

    public String getData() {
        return data;
    }

    public ArrayList<String> getSearch_log() {
        return search_log;
    }
    public void setSearchLog(ArrayList<String> e){
        this.search_log = e;
    }

    public void add_search_log(String s) {
        this.search_log.add(s);
    }
}
