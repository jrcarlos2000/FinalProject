package com.example.finalproject.DatabaseAdapter;
import com.example.finalproject.Domain.categoryDomain;
import com.example.finalproject.Domain.itemDomain;
import com.example.finalproject.Domain.userDomain;
import com.facebook.all.All;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class DataAdapter {
    //实体
    public static ArrayList<itemDomain> AllItems;
    public static ArrayList<categoryDomain> AllCategories;
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
        initCategories();
    }

    private void initCategories() {
        AllCategories = new ArrayList<>();
        AllCategories.add(new categoryDomain("Physics","physics_icon"));
        AllCategories.add(new categoryDomain("Math","math_icon"));
        AllCategories.add(new categoryDomain("Chemistry","chemistry_icon"));
        AllCategories.add(new categoryDomain("History","history_icon"));
        AllCategories.add(new categoryDomain("Biology","biology_icon"));
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
    /*
     private void init() throws IOException {

        //init User
        initUser();
        //init Data

        String row;
        AllItems = readDataFromHttp(args[]);

    }

    private ArrayList<itemDomain> readDataFromHttp(args[]){

            ArrayList<itemDomain> newList = new  ArrayList<>();

            //请求：参考下面怎么读入assets 的 datacsv 然后开一个新的itemdomain加入list
            while ((row = csvReader.readLine()) != null) {
            System.out.println(row);
            String[] data = row.split("\\|");
            System.out.println(data[0]+" \n"+data[2]+" \n"+data[1]);
            itemDomain item_1 = new itemDomain(data[0],data[2],data[1],data[3],data[4]);
            AllItems.add(item_1);
            }

            return newList

    }
     */

    public static void initUser() {
        User = new userDomain();
    }

    private void setUserData(String S){

    }
    public static ArrayList<itemDomain> getRecents(){
        ArrayList<itemDomain> recents = new ArrayList<>();
        recents.addAll(AllItems);
        Collections.sort(recents, new Comparator<itemDomain>() {
            public int compare(itemDomain o1, itemDomain o2) {
                return o1.getLastTimeVisited().compareTo(o2.getLastTimeVisited());
            }
        });
        Collections.reverse(recents);
        recents.subList(3,recents.size()).clear();
        Date initialDate = new Date(0);

        Iterator<itemDomain> iterator = recents.iterator();
        while(iterator.hasNext()) {
            itemDomain next = iterator.next();
            if(next.getLastTimeVisited().compareTo(initialDate)==0) {
                iterator.remove();
            }
        }
        return recents;
    }

    public static ArrayList<itemDomain> getPopulars(){
        ArrayList<itemDomain> populars = new ArrayList<>();
        populars.addAll(AllItems);
        Collections.sort(populars, new Comparator<itemDomain>() {
            public int compare(itemDomain o1, itemDomain o2) {
                return o1.getPopularity()-o2.getPopularity();
            }
        });
        Collections.reverse(populars);
        populars.subList(3,populars.size()).clear();

        return populars;
    }
}
