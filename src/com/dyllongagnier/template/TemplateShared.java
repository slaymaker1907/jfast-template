package com.dyllongagnier.template;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateShared
{
	private Pattern tokenReg = Pattern.compile("\\{\\{|\\}\\}|\\{|\\}|\\.|[^{}.]+", Pattern.DOTALL);
	
	public Pattern getTokenReg()
	{
		return tokenReg;
	}
	
	public ArrayList<String> getTokens(String template)
	{
		Matcher matcher = tokenReg.matcher(template);
		ArrayList<String> result = new ArrayList<>();
		while(matcher.find())
		{
			result.add(matcher.group());
		}
		
		return result;
	}
}
