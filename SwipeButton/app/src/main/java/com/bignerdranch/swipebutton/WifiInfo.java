package com.bignerdranch.swipebutton;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class WifiInfo implements Serializable {
    public WifiInfo(String SSID, String password, String type, boolean hidden, Date date,
                    String state) {
        this.SSID = SSID;
        this.password = password;
        this.type = type;
        this.hidden = hidden;
        this.date = date;
        this.state = state;
    }

    public String ID;
    public String SSID;
    public String password;
    public String type;
    public boolean hidden;
    public String state;
    public Date date;

    public WifiInfo() {
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", ID);
        result.put("SSID", SSID);
        result.put("password", password);
        result.put("type", type);
        result.put("hidden", hidden);
        result.put("date", date);
        result.put("state", state);

        return result;
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof WifiInfo) {
            WifiInfo temp = (WifiInfo) obj;
            if (this.SSID.equals(temp.SSID) && this.password.equals(temp.password) && this.type.equals(
                    temp.type) && this.hidden == temp.hidden) {
                return true;
            }
        }
        return false;
    }

    @Override public int hashCode() {
        return (this.SSID.hashCode() + this.password.hashCode() + this.type.hashCode());
    }
}
