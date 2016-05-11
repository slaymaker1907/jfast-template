package com.dyllongagnier.template;

import java.util.HashMap;

public class TemplateModule implements AbstractTemplateModule
{
	private HashMap<String, TemplateObject> objects = new HashMap<>();
	
	public TemplateModule()
	{
	}
	
	@Override
	public void setObject(TemplateVariable name, TemplateObject object)
	{
		String firstName = name.getModule(0);
		if (name.size() == 1)
		{
			objects.put(firstName, object);
		}
		else
		{
			TemplateObject ob = objects.get(firstName);
			if (ob == null || ob.isConcrete())
			{
				ob = new TemplateModule();
				objects.put(firstName, ob);
			}
			
			((TemplateModule)ob).setObject(name.partiallyApply(), object);
		}
	}

	@Override
	public TemplateObject getObject(TemplateVariable name) throws ObjectNotFound
	{
		String firstName = name.getModule(0);
		TemplateObject result = objects.get(firstName);
		if (result == null)
			throw new ObjectNotFound(name);
		
		if (name.size() > 1)
		{
			return result.getObject(name.partiallyApply());
		}
		else
		{
			return result;
		}
	}
	
	@Override
	public TemplateObject getObjectNull(TemplateVariable name)
	{
		String firstName = name.getModule(0);
		TemplateObject result = objects.get(firstName);
		if (result == null)
			return null;
		
		if (name.size() > 1)
		{
			return result.getObjectNull(name);
		}
		else
		{
			return result;
		}
	}

	@Override
	public CharSequence getString() throws NonConcreteObject
	{
		throw new NonConcreteObject();
	}
	
	public void setObject(String variableName, String object)
	{
		TemplateVariable var = TemplateVariable.fromString(variableName);
		this.setObject(var, new TemplateString(object));
	}
	
	public void setObject(String variableName, TemplateObject object)
	{
		TemplateVariable var = TemplateVariable.fromString(variableName);
		this.setObject(var, object);
	}
	
	public void setObject(TemplateVariable variable, String object)
	{
		this.setObject(variable, new TemplateString(object));
	}

	@Override
	public boolean isConcrete()
	{
		return false;
	}
}
