package io.ichi_go.ichigo;

import android.content.Context;
import android.view.View;

/**
 * The items that are displayed in the navigation drawer
 */
public class NavItem {
    private String title;
    private int iconId;

    /**
     * Constructor for a simple Navigation Item in the navigation drawer
     * @param title the title of the item
     * @param iconId the id for the icon to be displayed
     */
    public NavItem(String title, int iconId){
        this.title = title;
        this.iconId = iconId;
    }

    /**
     * Gets the title of the item
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the id of the icon of the item
     * @return the id of the icon
     */
    public int getIconId() {
        return iconId;
    }

    /**
     * Sets the title of the item
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the id of the icon of the item
     * @param iconId the new id of the icon
     */
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
