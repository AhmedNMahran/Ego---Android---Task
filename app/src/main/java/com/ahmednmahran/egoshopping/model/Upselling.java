
package com.ahmednmahran.egoshopping.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Upselling {

    @Expose
    private BannerCategory bannerCategory;
    @Expose
    private List<Category> categories;

    public BannerCategory getBannerCategory() {
        return bannerCategory;
    }

    public void setBannerCategory(BannerCategory bannerCategory) {
        this.bannerCategory = bannerCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
