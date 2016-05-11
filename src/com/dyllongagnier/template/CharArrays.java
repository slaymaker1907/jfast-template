package com.dyllongagnier.template;

public class CharArrays
{
	public static char[] concat(char[] first, char[] second)
	{
		char[] result = new char[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	public static int append(char[] toAppend, int appendPos, char[] toAdd)
	{
		System.arraycopy(toAdd, 0, toAppend, appendPos, toAdd.length);
		return toAdd.length + appendPos;
	}
}
