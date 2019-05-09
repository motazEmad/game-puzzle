package com.company.games.puzzle.controller.util;

import java.io.InputStream;
import java.io.PrintStream;

public class StreamFactory {
	
	public InputStream getInputStream() {
		return System.in;
	}
	
	public PrintStream getOutputStream() {
		return System.out;
	}
	
	public PrintStream getErrorStream() {
		return System.err;
	}

}
