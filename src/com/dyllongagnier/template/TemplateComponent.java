package com.dyllongagnier.template;

public interface TemplateComponent
{
	public CharSequence applyModule(TemplateObject module) throws ObjectNotFound;
	public TemplateComponent partiallyApplyModule(TemplateObject module);
	public CharSequence applyModuleNull(TemplateObject module);
	public boolean isRealized();
}
