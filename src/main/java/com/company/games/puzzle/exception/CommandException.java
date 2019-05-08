package com.company.games.puzzle.exception;

public class CommandException extends Exception {

	private static final long serialVersionUID = -7098531823853189583L;
	private String command;

	
	public CommandException(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
