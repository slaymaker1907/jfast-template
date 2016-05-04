package com.dyllongagnier.template;

import java.util.ArrayList;
import java.util.HashMap;

public class SequencedModule
{
	private HashMap<TemplateVariable, Integer> numberAlignment;
	
	protected SequencedModule(HashMap<TemplateVariable, Integer> numberAlignment)
	{
		this.numberAlignment = numberAlignment;
	}
	
	// Is null if not exists.
	public Integer getIndex(TemplateVariable variable)
	{
		return numberAlignment.get(variable);
	}
	
	public static class SequencedModuleBuilder
	{
		private ArrayList<TemplateVariable> variables = new ArrayList<>();
		
		public SequencedModuleBuilder addVariable(TemplateVariable variableName)
		{
			variables.add(variableName);
			return this;
		}
		
		public SequencedModule build()
		{
			HashMap<TemplateVariable, Integer> numberAlignment = new HashMap<>();
			for(int i = 0; i < variables.size(); i++)
				numberAlignment.put(variables.get(i), i);
			return new SequencedModule(numberAlignment);
		}
	}
}
