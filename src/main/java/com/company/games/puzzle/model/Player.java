package com.company.games.puzzle.model;

public class Player implements BaseObject {

	private static final long serialVersionUID = -4641412970457291606L;
	private String name;
	private int age;
	private Gender gender;
	private int life;
	private Experience experience;
	private Location currentLocation;
	private boolean win;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getLife() {
		return life;
	}
	
	public boolean isWin() {
		return win;
	}
	
	public void setWin(boolean win) {
		this.win = win;
	}

	public enum Gender {
		Male, Female
	}
}
