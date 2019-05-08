package com.company.games.puzzle.model;

public class Location implements BaseObject {

	private static final long serialVersionUID = -5532123016223477486L;
	private String location;

	public Location(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return getLocation();
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Location)
			return getLocation().equals(((Location) obj).getLocation());
		return super.equals(obj);
	}
}
