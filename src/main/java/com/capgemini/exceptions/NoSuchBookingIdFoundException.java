package com.capgemini.exceptions;

public class NoSuchBookingIdFoundException extends Exception {
	public NoSuchBookingIdFoundException(String message)
	{
		super(message);
	}
}
