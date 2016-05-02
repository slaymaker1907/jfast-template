package com.dyllongagnier.template;

public class StringComponent implements TemplateComponent
{
	private String data;
	
	public StringComponent(String data)
	{
		this.data = data;
	}
	
	@Override
	public String applyModule(TemplateObject module) throws ObjectNotFound
	{
		return data;
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
}
