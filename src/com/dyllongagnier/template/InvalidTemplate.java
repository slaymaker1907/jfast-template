package com.dyllongagnier.template;

public class InvalidTemplate extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidTemplate()
	{
	}
	
	public InvalidTemplate(String message)
	{
		super(message);
	}
}
