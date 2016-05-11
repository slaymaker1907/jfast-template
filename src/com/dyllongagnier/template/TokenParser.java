package com.dyllongagnier.template;

import java.util.ArrayList;

import com.dyllongagnier.template.TemplateVariable.TemplateVariableBuilder;

public class TokenParser
{
	private StringBuilder buffer = new StringBuilder();
	private TemplateVariableBuilder variable = null;
	private ArrayList<String> constants = new ArrayList<>();
	private ArrayList<TemplateVariable> variables = new ArrayList<>();
	private ArrayList<TemplateComponent> components = new ArrayList<>();
	
	private boolean parsingVariable()
	{
		return variable != null;
	}
	
	public TokenParser(Iterable<String> tokens) throws InvalidTemplate
	{
		for(String token : tokens)
		{
			switch(token)
			{
				case "{":
					processLeftBrace();
					break;
				case "}":
					processRightBrace();
					break;
				case ".":
					parseDot();
					break;
				case "{{":
					parseOther("{");
					break;
				case "}}":
					parseOther("}");
					break;
				default:
					parseOther(token);
					break;
			}
		}
		
		String toAdd = emptyBuffer();
		constants.add(toAdd);
		components.add(new TemplateString(toAdd));
		
		if (variable != null)
			throw new InvalidTemplate("Input of template ended without finishing variable name.");
	}
	
	public TemplateVariable[] getVariables()
	{
		return variables.toArray(new TemplateVariable[0]);
	}
	
	public String[] getConstants()
	{
		return constants.toArray(new String[0]);
	}
	
	public TemplateComponent[] getComponents()
	{
		return components.toArray(new TemplateComponent[0]);
	}
	
	private void parseOther(String token)
	{
		buffer.append(token);
	}
	
	private void processLeftBrace() throws InvalidTemplate
	{
		if (parsingVariable())
			throw new InvalidTemplate("Had { in variable name.");
		String toAdd = emptyBuffer();
		constants.add(toAdd);
		components.add(new TemplateString(toAdd));
		setParsingVariable(true);
	}
	
	private void processRightBrace() throws InvalidTemplate
	{
		if (!parsingVariable())
			throw new InvalidTemplate("Unexpected } encountered.");
		TemplateVariable toAdd = variable.addModule(emptyBuffer()).build();
		variables.add(toAdd);
		components.add(toAdd);
		setParsingVariable(false);
	}
	
	private String emptyBuffer()
	{
		String result = buffer.toString();
		buffer = new StringBuilder(buffer.capacity());
		return result;
	}
	
	private void setParsingVariable(boolean parseVariable)
	{
		if (parseVariable)
			variable = new TemplateVariableBuilder();
		else
			variable = null;
	}
	
	private void parseDot()
	{
		if (!parsingVariable())
		{
			buffer.append(".");
		}
		else
		{
			variable.addModule(emptyBuffer());
		}
	}
}
