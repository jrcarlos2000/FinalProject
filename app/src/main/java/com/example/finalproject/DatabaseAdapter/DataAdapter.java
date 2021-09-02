package com.example.finalproject.DatabaseAdapter;
import com.example.finalproject.Domain.itemDomain;
import com.example.finalproject.Domain.userDomain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAdapter {
    //实体
    public static ArrayList<itemDomain> AllItems;
    //用户数据
    public static userDomain User;
    private BufferedReader csvReader;
    private String link;
    public DataAdapter(String link,BufferedReader csvReader) {
        this.link = link;
        this.csvReader = csvReader;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {

        //init User
        initUser();
        //init Data

        String row;
        AllItems = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            System.out.println(row);
            String[] data = row.split("\\|");
            System.out.println(data[0]+" \n"+data[2]+" \n"+data[1]);
            itemDomain item_1 = new itemDomain(data[0],data[2],data[1],data[3],data[4]);
            AllItems.add(item_1);
        }
        csvReader.close();
    }

    public static void initUser() {
        User = new userDomain();
    }

    private void setUserData(String S){

    }

}
