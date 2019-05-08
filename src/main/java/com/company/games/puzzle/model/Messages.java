package com.company.games.puzzle.model;

public class Messages {

	private String welcomeMessage;
	private String[] winMessages;

	public Messages(String welcomeMessage, String[] winMessages) {
		this.welcomeMessage = welcomeMessage;
		this.winMessages = winMessages;
	}

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	public void setWinMessages(String[] winMessages) {
		this.winMessages = winMessages;
	}

	public String[] getWinMessages() {
		return winMessages;
	}

}
