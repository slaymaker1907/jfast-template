package com.dyllongagnier.template;

public class TemplateString implements TemplateObject, TemplateComponent
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
	public boolean isComponent()
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

	@Override
	public CharSequence applyModule(TemplateObject module) throws ObjectNotFound
	{
		return this.data;
	}

	@Override
	public TemplateComponent partiallyApplyModule(TemplateObject module)
	{
		return this;
	}

	@Override
	public CharSequence applyModuleNull(TemplateObject module)
	{
		return this.data;
	}

	@Override
	public boolean isRealized()
	{
		return true;
	}

	@Override
	public BoundTemplate bind(SequencedModule module)
	{
		char[][] chars = new char[][]{this.data.toCharArray()};
		return new BoundTemplate(chars, new boolean[]{false}, new int[0]);
	}
	
	@Override
	public String toString()
	{
		return this.data;
	}
}
