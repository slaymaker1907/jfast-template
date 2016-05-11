package com.dyllongagnier.template;

import java.util.ArrayList;

public class Template implements TemplateComponent, TemplateObject
{
	private TemplateComponent[] components;
	private int minimumLength;
	
	public Template(String template, TemplateShared shared) throws InvalidTemplate
	{
		ArrayList<String> tokens = shared.getTokens(template);
		TokenParser parser = new TokenParser(tokens);
		components = parser.getComponents();
	}
	
	public Template(TemplateComponent[] components)
	{
		this.components = components;
	}
	
	@Override
	public CharSequence applyModule(TemplateObject module) throws ObjectNotFound
	{
		Template result = this.partiallyApplyModule(module);
		StringBuilder builder = new StringBuilder(minimumLength * 2);
		for(TemplateComponent component : result.components)
			builder.append(component.applyModule(module));
		return builder.toString();
	}
	
	@Override
	public Template partiallyApplyModule(TemplateObject module)
	{
		ArrayList<TemplateComponent> newComponents = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		for(TemplateComponent component : components)
		{
			TemplateComponent newComp = component.partiallyApplyModule(module);
			CharSequence toAdd = newComp.applyModuleNull(module);
			if (toAdd == null)
			{
				if (builder.length() != 0)
					newComponents.add(new TemplateString(builder.toString()));
				builder = new StringBuilder();
				newComponents.add(newComp);
			}
			else
			{
				builder.append(toAdd);
			}
		}
		
		if (builder.length() != 0)
			newComponents.add(new TemplateString(builder.toString()));
		
		return new Template(newComponents.toArray(new TemplateComponent[newComponents.size()]));
	}

	@Override
	public CharSequence applyModuleNull(TemplateObject module)
	{
		try
		{
			return this.applyModule(module);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public boolean isRealized()
	{
		boolean realized = true;
		for(TemplateComponent component : components)
			realized = realized && component.isRealized();
		return realized;
	}
	
	public BoundTemplate bind(SequencedModule module)
	{
		BoundTemplate result = new BoundTemplate(new char[0][0], new boolean[0], new int[0]);
		for(TemplateComponent component : components)
			result = result.append(component.bind(module));
		
		return result;
	}

	@Override
	public boolean isConcrete()
	{
		return true;
	}

	@Override
	public TemplateObject getObject(TemplateVariable name) throws ObjectNotFound
	{
		throw new ObjectNotFound(name);
	}

	@Override
	public CharSequence getString() throws NonConcreteObject
	{
		throw new NonConcreteObject();
	}
	
	@Override
	public boolean isComponent()
	{
		return true;
	}
	
	@Override
	public TemplateComponent getComponent()
	{
		return this;
	}
}
