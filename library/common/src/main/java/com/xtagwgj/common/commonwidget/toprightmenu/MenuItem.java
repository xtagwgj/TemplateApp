package com.xtagwgj.common.commonwidget.toprightmenu;

/**
 * Created by xtagwgj on 2017/1/1.
 */

public class MenuItem {
    private int icon;
    private String text;

    public MenuItem() {}

    public MenuItem(String text) {
        this.text = text;
    }

    public MenuItem(int iconId, String text) {
        this.icon = iconId;
        this.text = text;
    }

    public int getIcon() {
        return icon;

    }
    public void setIcon(int iconId) {
        this.icon = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}