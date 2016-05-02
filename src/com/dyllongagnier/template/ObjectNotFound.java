package com.dyllongagnier.template;

public class ObjectNotFound extends Exception
{
	private static final long serialVersionUID = 1L;
	private TemplateVariable name;

	public ObjectNotFound(TemplateVariable name)
	{
		super(name.toString() + " was not found.");
		this.name = name;
	}
	
	public TemplateVariable getName()
	{
		return name;
	}
}
