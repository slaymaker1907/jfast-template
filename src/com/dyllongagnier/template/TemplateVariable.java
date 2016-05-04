package com.dyllongagnier.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.common.collect.Iterators;


public class TemplateVariable implements Iterable<String>, TemplateComponent
{
	public static class TemplateVariableBuilder
	{
		private ArrayList<String> modules = new ArrayList<>();
		
		public TemplateVariableBuilder addModule(String module)
		{
			modules.add(module);
			return this;
		}
		
		public TemplateVariable build()
		{
			String[] modules = new String[this.modules.size()];
			this.modules.toArray(modules);
			return new TemplateVariable(modules);
		}
	}
	
	private String[] modules;
	private TemplateVariable nextVariable;
	private int hash;
	
	protected TemplateVariable(String[] modules)
	{
		this.modules = modules;
		if(modules.length > 0)
			nextVariable = new TemplateVariable(Arrays.copyOfRange(modules, 1, modules.length));
		hash = 0;
		for(String module : modules)
			hash += module.hashCode();
	}
	
	/**
	 * This method returns a template variable with one less name.
	 * @return
	 */
	public TemplateVariable partiallyApply()
	{
		return nextVariable;
	}
	
	public int size()
	{
		return modules.length;
	}
	
	public String getModule(int index)
	{
		return modules[index];
	}

	@Override
	public Iterator<String> iterator()
	{
		return Iterators.forArray(modules);
	}
	
	@Override
	public int hashCode()
	{
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		try
		{
			TemplateVariable other = (TemplateVariable)o;
			if (this.modules.length != other.modules.length)
				return false;
			for(int i = 0; i < this.modules.length; i++)
				if (!this.modules[i].equals(other.modules[i]))
					return false;
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return String.join(".", modules);	
	}

	@Override
	public CharSequence applyModule(TemplateObject module) throws ObjectNotFound
	{
		try
		{
			return module.getObject(this).getString();
		} catch (NonConcreteObject e)
		{
			throw new ObjectNotFound(this);
		}
	}

	@Override
	public TemplateComponent partiallyApplyModule(TemplateObject module)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CharSequence applyModuleNull(TemplateObject module)
	{
		TemplateObject obj = module.getObjectNull(this);
		if (obj != null && obj.isConcrete())
		{
			try
			{
				return obj.getString();
			}
			catch (Exception e)
			{
				return null;
			}
		}
		else
			return null;
	}

	@Override
	public boolean isRealized()
	{
		return false;
	}

	@Override
	public BoundTemplate bind(SequencedModule module)
	{
		int[] queryOrder = {module.getIndex(this)};
		boolean[] varPos = {true};
		return new BoundTemplate(new String[0], varPos, queryOrder);
	}
}
