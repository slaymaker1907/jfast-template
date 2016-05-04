package com.dyllongagnier.template;

import java.util.ArrayList;

public class Template implements TemplateComponent
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
		StringBuilder builder = new StringBuilder(minimumLength * 2);
		for(TemplateComponent component : components)
			builder.append(component.applyModule(module));
		return builder;
	}
	
	@Override
	public TemplateComponent partiallyApplyModule(TemplateObject module)
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
					newComponents.add(new StringComponent(builder.toString()));
				builder = new StringBuilder();
				newComponents.add(newComp);
			}
			else
			{
				builder.append(toAdd);
			}
		}
		
		return new Template(newComponents.toArray(new TemplateComponent[0]));
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
		BoundTemplate result = new BoundTemplate(new String[0], new boolean[0], new int[0]);
		for(TemplateComponent component : components)
			result = result.append(component.bind(module));
		
		return result;
	}
}
