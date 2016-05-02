package com.dyllongagnier.template;

public class TemplateFactory
{
	private TemplateShared shared = new TemplateShared();
	
	public Template getTemplate(String template) throws InvalidTemplate
	{
		return new Template(template, shared);
	}
}
