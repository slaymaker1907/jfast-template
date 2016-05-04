package com.dyllongagnier.template;

public class ObjectNotFound extends Exception
{
	private static final long serialVersionUID = 1L;
	private String name;

	public ObjectNotFound(TemplateVariable name)
	{
		this(name.toString());
	}
	
	public ObjectNotFound(String name)
	{
		super(name + " was not found.");
		this.name = name;
	}
	
	public String getName()
	{
		return name.toString();
	}
}
