package com.company.games.puzzle.model;

public class Character implements BaseObject {

	private static final long serialVersionUID = 2586339547107790815L;
	private String name;
	private Experience experience;
	private Location currentLocation;

	public Character(String name, Experience experience) {
		super();
		this.name = name;
		this.experience = experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	@Override
	public String toString() {
		return getName() + (getExperience() != null ?  " Lv." + getExperience().getLevel() : ""); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Character )
			return this.getName().equals(((Character) obj).getName());
		return super.equals(obj);
	}
}
