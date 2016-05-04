package com.dyllongagnier.template;

import java.util.Arrays;

public class BoundTemplate
{
	private String[] components;
	private int minimumSize;
	private int[] queryOrder, variableFrequency;
	private boolean[] variablePositions;
	
	public BoundTemplate(String[] components, boolean[] variablePositions, int[] queryOrder)
	{
		minimumSize = 0;
		for(CharSequence component : components)
			minimumSize += component.length();
		this.components = components;
		this.variablePositions = variablePositions;
		this.queryOrder = queryOrder;
		
		int max = -1;
		for(int variable : queryOrder)
			max = Math.max(variable, max);
		
		this.variableFrequency = new int[max + 1];
		for(int variable : queryOrder)
			this.variableFrequency[variable]++;
	}
	
	public ObjectSequence newObjectSequence()
	{
		return new ObjectSequence(queryOrder, this.variableFrequency);
	}
	
	public int variableCount()
	{
		return this.variableFrequency.length;
	}
	
	public StringBuilder applyTemplate(ObjectSequence sequence)
	{
		StringBuilder result = new StringBuilder(minimumSize + sequence.getTotalLength());
		int charPos = 0;
		for(int i = 0; i < variablePositions.length; i++)
			if (variablePositions[i])
				result.append(sequence.consumeObject());
			else
				result.append(components[charPos++]);
		
		return result;
	}
	
	public BoundTemplate append(BoundTemplate other)
	{
		int[] newOrder = Arrays.copyOf(this.queryOrder, this.queryOrder.length + other.queryOrder.length);
		for(int i = 0; i < other.queryOrder.length; i++)
			newOrder[i + this.queryOrder.length] = other.queryOrder[i];
		
		String[] newComponents;
		boolean[] newPositions;
		
		int pos = 0;
		for(int query : this.queryOrder)
			newOrder[pos++] = query;
		for(int query : other.queryOrder)
			newOrder[pos++] = query;
		
		if (variablePositions.length > 0 && !variablePositions[variablePositions.length - 1] && !other.variablePositions[0])
		{
			String middleString = components[components.length - 1] + other.components[0];
			newComponents = new String[this.components.length + other.components.length - 1];
			newPositions = Arrays.copyOf(this.variablePositions, this.variablePositions.length + other.variablePositions.length - 1);
			int newPos;
			for(newPos = 0; newPos < this.components.length - 1; newPos++)
			{
				newComponents[newPos] = this.components[newPos];
			}
			
			newComponents[newPos++] = middleString;
			
			for(int i = 1; i < other.components.length; i++, newPos++)
			{
				newComponents[newPos] = other.components[i];
			}
			
			newPos = this.variablePositions.length;
			for(int i = 1; newPos < newPositions.length; newPos++, i++)
				newPositions[newPos] = other.variablePositions[i];
		}
		else
		{
			newComponents = Arrays.copyOf(this.components, this.components.length + other.components.length);
			for(int newPos = this.components.length, i = 0; i < other.components.length; i++, newPos++)
				newComponents[newPos] = other.components[i];
			
			
			newPositions = Arrays.copyOf(this.variablePositions, this.variablePositions.length + other.variablePositions.length);
			for(int newPos = this.variablePositions.length, i = 0; i < other.variablePositions.length; i++, newPos++)
				newPositions[newPos] = other.variablePositions[i];
		}
		
		return new BoundTemplate(newComponents, newPositions, newOrder);
	}	
}
