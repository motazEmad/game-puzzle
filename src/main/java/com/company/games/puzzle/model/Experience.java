package com.company.games.puzzle.model;

public class Experience implements BaseObject {

	private static final long serialVersionUID = 5229699829696201068L;
	private int level;
	private int attach;
	private int defence;

	public Experience(int level, int attach, int defence) {
		super();
		this.level = level;
		this.attach = attach;
		this.defence = defence;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAttach() {
		return attach;
	}

	public void setAttach(int attach) {
		this.attach = attach;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}
}
