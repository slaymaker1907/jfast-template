package com.dyllongagnier.template;

public class ObjectSequence
{
	private int insertPos, consumePos;
	private CharSequence[] objects;
	private int[] copiesNeeded;
	private int[] reverseLookup;
	private int totalLength;
	
	// Don't mutate the insertion order.
	public ObjectSequence(int[] queryOrder, int[] copiesNeeded)
	{
		this.reverseLookup = queryOrder;
		this.copiesNeeded = copiesNeeded;
		objects = new CharSequence[copiesNeeded.length];
		this.reset();
	}
	
	public void addObject(CharSequence object)
	{
		totalLength += object.length() * copiesNeeded[insertPos];
		objects[insertPos++] = object;
	}
	
	public CharSequence consumeObject()
	{
		return objects[reverseLookup[consumePos++]];
	}
	
	public int size()
	{
		return objects.length;
	}
	
	public boolean isFullyConsumed()
	{
		return consumePos == this.size();
	}
	
	public boolean isPartiallyConsumed()
	{
		return consumePos != 0;
	}
	
	public void reset()
	{
		consumePos = 0;
		insertPos = 0;
		totalLength = 0;
	}
	
	public int getTotalLength()
	{
		return totalLength;
	}
}
