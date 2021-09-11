package com.example.finalproject.Domain;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class itemDomain implements Serializable {
    private String title;
    private String description;
    private HashMap<String,String> atts;
    private ArrayList<String>  subjects;
    private String related_1,related_2,related_3;
    private boolean visited;
    private String pic;
    private Date lastTimeVisited;
    private int popularity;

    public itemDomain(String title, String description, String pic, String atts,String subjects) {

        this.title = title;
        this.description = description;
        this.pic = pic;
        this.atts = new HashMap<>();
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(atts.split(",")));
        for (String list_item : list) {
            String att[] = list_item.split(":");
            this.atts.put(att[0], att[1]);
        }

        this.subjects = new ArrayList<String>(Arrays.asList(subjects.toLowerCase().split(",")));
        this.visited = false;
        this.lastTimeVisited = new Date(0);
        this.popularity = 0 + (int)(Math.random() * ((100) + 1));
        Log.d("popularity set",Integer.toString(this.popularity));
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRelated_1() {

        if(this.subjects.size()>0) {
            return this.subjects.get(0);
        }
        return " ";
    }

    public void setRelated_1(String related_1) {
        this.related_1 = related_1;
    }

    public String getRelated_2() {
        if(this.subjects.size()>1) {
            return this.subjects.get(1);
        }
        return " ";
    }

    public void setRelated_2(String related_2) {
        this.related_2 = related_2;
    }

    public String getRelated_3() {
        if(this.subjects.size()>2) {
            return this.subjects.get(2);
        }
        return " ";
    }

    public void setRelated_3(String related_3) {
        this.related_3 = related_3;
    }

    public HashMap<String, String> getAtts() {
        return atts;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public String getAttsAsString(){
        String data="";
        Iterator it = atts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            data=data+pair.getKey().toString()+": "+pair.getValue().toString()+"\n";
            it.remove(); // avoids a ConcurrentModificationException
        }
        return data;
    }
    public String getDataAsString(){
        return "we will shre this data";
    }

    public void setVisited(){
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public Date getLastTimeVisited() {
        return lastTimeVisited;
    }

    public void updateLastTimeVisited() {
        this.lastTimeVisited = Calendar.getInstance().getTime();;
    }
    public int getPopularity() {
        return popularity;
    }

    public void increasePopularity() {
        this.popularity++;
    }
}
