package com.dyllongagnier.template;

import java.lang.reflect.Field;

public class StringExtractor
{
	private Field arrayField;
	
	public StringExtractor()
	{
		try
		{
			arrayField = String.class.getDeclaredField("value");
			arrayField.setAccessible(true);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public char[] getArray(String string)
	{
		try
		{
			return (char[]) arrayField.get(string);
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}	
}
