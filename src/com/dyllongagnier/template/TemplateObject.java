package com.dyllongagnier.template;

public interface TemplateObject
{
	public boolean isConcrete();
	public TemplateObject getObject(TemplateVariable name) throws ObjectNotFound;
	public CharSequence getString() throws NonConcreteObject;
	
	public default TemplateObject getObjectNull(TemplateVariable name)
	{
		try
		{
			return getObject(name);
		}
		catch (ObjectNotFound e)
		{
			return null;
		}
	}
	
	public default boolean hasObject(TemplateVariable name)
	{
		return getObjectNull(name) != null;
	}
}
