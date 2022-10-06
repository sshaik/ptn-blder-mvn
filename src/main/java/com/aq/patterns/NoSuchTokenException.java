package com.aq.patterns;

public class NoSuchTokenException extends Throwable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1967505396398251619L;
	public NoSuchTokenException()
	{
		super();
	}
	public NoSuchTokenException(String message)
	{
		super(message);
	}
	
	public NoSuchTokenException(String message, Throwable t)
	{
		super(message,t);
	}
}
