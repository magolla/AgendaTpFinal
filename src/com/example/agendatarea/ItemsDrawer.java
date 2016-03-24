package com.example.agendatarea;

public class ItemsDrawer {

	 private String name;
	    private int iconId;

	    public ItemsDrawer(String name, int iconId) {
	        this.name = name;
	        this.iconId = iconId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getIconId() {
	        return iconId;
	    }

	    public void setIconId(int iconId) {
	        this.iconId = iconId;
	    }
	
}
