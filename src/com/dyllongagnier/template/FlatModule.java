package com.dyllongagnier.template;

import java.util.HashMap;

public class FlatModule implements AbstractTemplateModule
{
	private HashMap<String, TemplateObject> memory = new HashMap<>();
	
	@Override
	public boolean isConcrete()
	{
		return false;
	}

	@Override
	public TemplateObject getObject(TemplateVariable name) throws ObjectNotFound
	{
		TemplateObject result = memory.get(name.toString());
		if (result == null)
			throw new ObjectNotFound(name);
		return result;
	}
	
	public TemplateObject getObject(String name) throws ObjectNotFound
	{
		TemplateObject result = this.getObjectNull(name);
		if (result == null)
			throw new ObjectNotFound(name);
		return result;
	}
	
	public TemplateObject getObjectNull(String name)
	{
		return memory.get(name);
	}

	@Override
	public CharSequence getString() throws NonConcreteObject
	{
		throw new NonConcreteObject();
	}

	@Override
	public void setObject(TemplateVariable name, TemplateObject object)
	{
		this.setObject(name.toString(), object);
	}
	
	public void setObject(String name, TemplateObject object)
	{
		memory.put(name, object);
	}	
}
