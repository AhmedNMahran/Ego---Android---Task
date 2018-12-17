
package com.ahmednmahran.egoshopping.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class BannerCategory {

    @Expose
    private List<App> apps;
    @Expose
    private String name;
    @Expose
    private String type;

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
