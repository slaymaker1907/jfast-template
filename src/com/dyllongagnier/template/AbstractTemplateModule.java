package com.dyllongagnier.template;

public interface AbstractTemplateModule extends TemplateObject
{
	public void setObject(TemplateVariable name, TemplateObject object);
	public default boolean isConcrete()
	{
		return false;
	}

}
