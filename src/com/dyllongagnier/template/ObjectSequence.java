package com.dyllongagnier.template;

public final class ObjectSequence
{
	private int insertPos, consumePos;
	private char[][] objects;
	private int[] copiesNeeded;
	private int[] reverseLookup;
	private int totalLength;
	private StringExtractor extractor;
	
	// Don't mutate the insertion order.
	public ObjectSequence(int[] queryOrder, int[] copiesNeeded, StringExtractor extractor)
	{
		this.reverseLookup = queryOrder;
		this.copiesNeeded = copiesNeeded;
		objects = new char[copiesNeeded.length][];
		this.extractor = extractor;
		this.reset();
	}
	
	public void addObject(String object)
	{
		totalLength += object.length() * copiesNeeded[insertPos];
		objects[insertPos++] = extractor.getArray(object);
	}
	
	public char[] consumeObject()
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
