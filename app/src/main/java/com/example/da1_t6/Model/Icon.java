package com.example.da1_t6.Model;

public class Icon {

    private int maIcon;
    private int Icon;

    public Icon() {
    }

    public Icon(int maIcon, int icon) {
        this.maIcon = maIcon;
        Icon = icon;
    }

    public Icon(int icon) {
        Icon = icon;
    }

    public int getMaIcon() {
        return maIcon;
    }

    public void setMaIcon(int maIcon) {
        this.maIcon = maIcon;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }
}
