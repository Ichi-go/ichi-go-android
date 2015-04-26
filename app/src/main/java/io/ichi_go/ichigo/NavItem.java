package io.ichi_go.ichigo;

import android.content.Context;
import android.view.View;

/**
 * Created by Russell on 2/22/2015.
 */
public class NavItem {
    private String title;
    private int iconId;

    public NavItem(String title, int iconId){
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
