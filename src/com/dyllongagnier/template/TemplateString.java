package com.dyllongagnier.template;

public class TemplateString implements TemplateObject
{
	private String data;
	
	public TemplateString(String data)
	{
		this.data = data;
	}
	
	@Override
	public boolean isConcrete()
	{
		return true;
	}

	@Override
	public TemplateObject getObject(TemplateVariable name)
			throws ObjectNotFound
	{
		throw new ObjectNotFound(name);
	}

	@Override
	public String getString() throws NonConcreteObject
	{
		return data;
	}
}
