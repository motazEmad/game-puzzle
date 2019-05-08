package com.company.games.puzzle.controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Predicate;

import com.company.games.puzzle.exception.CommandException;

public class ConsoleReaderUtils {

	private static PrintStream console = System.out;
	private static PrintStream errorconsole = System.err;
	
	public static String readInput(BufferedReader userinput) {
		while (true) {
			try {
				String input = userinput.readLine();
				return input;
			} catch (IOException e) {
				errorconsole.println("failed to read input, please try again");
			}
		}
	}
	
	public static String readInput(BufferedReader userinput, Predicate<String> validator, String failMessage) {
		while (true) {
			try {
				String input = userinput.readLine();
				if(validator.test(input))
					return input;
				else {
					console.println(failMessage);
				}
			} catch (IOException e) {
				errorconsole.println("failed to read input, please try again");
			}
		}
	}
	
	public static int readIntInput(BufferedReader userinput, Predicate<Integer> validator, String failMessage) {
		while (true) {
			try {
				String input = userinput.readLine();
				int x = Integer.parseInt(input);
				if(validator.test(x))
					return x;
				else {
					console.println(failMessage);
				}
			} catch (NumberFormatException e) {
				errorconsole.println("failed to read input, please enter a number");
			} catch (IOException e) {
				errorconsole.println("failed to read input, please try again");
			}
		}
	}
	
	public static int readIntInput(BufferedReader userinput, String failMessage, Predicate<Integer> validator) throws CommandException {
		while (true) {
			try {
				String input = userinput.readLine();
				if(input.startsWith(":"))
					throw new CommandException(input);
				int x = Integer.parseInt(input);
				if(validator.test(x))
					return x;
				else {
					console.println(failMessage);
				}
			} catch (NumberFormatException e) {
				errorconsole.println("failed to read input, please enter a number");
			} catch (IOException e) {
				errorconsole.println("failed to read input, please try again");
			}
		}
	}
}
