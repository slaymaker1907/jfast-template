package com.dyllongagnier.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateFactory
{
	private TemplateShared shared = new TemplateShared();
	
	public Template getTemplate(String template) throws InvalidTemplate
	{
		return new Template(template, shared);
	}
	
	public Template loadFromFile(String filename) throws InvalidTemplate, IOException
	{
		return this.getTemplate(new String(Files.readAllBytes(Paths.get(filename)), "UTF-8"));
	}
}
