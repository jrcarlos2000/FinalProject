package com.example.finalproject.Domain;

import java.io.Serializable;

public class ShareableItem implements Serializable {
    private boolean shareAsPic;
    private String Data;
    byte[] byteArray;

    public ShareableItem(boolean shareAsPic, String text, byte[] byteArray) {
        this.shareAsPic = shareAsPic;
        this.Data = text;
        this.byteArray = byteArray;
    }

    public String getData() {
        return Data;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public boolean is_pic(){
        return this.shareAsPic;
    }

}
