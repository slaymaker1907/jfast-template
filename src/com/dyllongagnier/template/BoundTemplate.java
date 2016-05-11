package com.dyllongagnier.template;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.util.Arrays;

public final class BoundTemplate
{
	private char[][] components;
	private int minimumSize;
	private int[] queryOrder, variableFrequency;
	private boolean[] variablePositions;
	private StringExtractor extractor = new StringExtractor();
	
	public BoundTemplate(char[][] components, boolean[] variablePositions, int[] queryOrder)
	{
		minimumSize = 0;
		for(char[] component : components)
			minimumSize += component.length;
		
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
		return new ObjectSequence(queryOrder, this.variableFrequency, extractor);
	}
	
	public int variableCount()
	{
		return this.variableFrequency.length;
	}
	
	public String applyTemplate(ObjectSequence sequence)
	{
		char[] result = new char[minimumSize + sequence.getTotalLength()];
		int charPos = 0;
		int resultPos = 0;
		for(int i = 0; i < variablePositions.length; i++)
		{
			char[] current;
			if (variablePositions[i])
				current = sequence.consumeObject();
			else
				current = components[charPos++];
			resultPos = CharArrays.append(result, resultPos, current);
		}
		
		return new String(result);
	}
		
	public BoundTemplate append(BoundTemplate other)
	{
		int[] newOrder = Arrays.copyOf(this.queryOrder, this.queryOrder.length + other.queryOrder.length);
		for(int i = 0; i < other.queryOrder.length; i++)
			newOrder[i + this.queryOrder.length] = other.queryOrder[i];
		
		char[][] newComponents;
		boolean[] newPositions;
		
		int pos = 0;
		for(int query : this.queryOrder)
			newOrder[pos++] = query;
		for(int query : other.queryOrder)
			newOrder[pos++] = query;
		
		if (variablePositions.length > 0 && !variablePositions[variablePositions.length - 1] && !other.variablePositions[0])
		{
			char[] middleString = CharArrays.concat(components[components.length - 1], other.components[0]);
			newComponents = new char[this.components.length + other.components.length - 1][];
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
	
	public void saveToDisk(String filename) throws IOException
	{
		try(FileChannel channel = FileChannel.open(Paths.get(filename));)
		{
			MappedByteBuffer result = channel.map(MapMode.READ_WRITE, 0, this.getMaximumSize());
			result.putInt(components.length);
			for(char[] component : components)
			{
				result.putInt(component.length);
				for(char ch : component)
					result.putChar(ch);
			}
			
			result.putInt(variablePositions.length);
			for(boolean var : variablePositions)
				result.put((byte)(var ? 1 : 0));
			
			result.putInt(queryOrder.length);
			for(int query : queryOrder)
				result.putInt(query);
			result.force();
		}
	}
	
	public static BoundTemplate loadFromDisk(String filename) throws IOException
	{
		try(FileChannel channel = FileChannel.open(Paths.get(filename));)
		{
			MappedByteBuffer result = channel.map(MapMode.READ_ONLY, 0, channel.size());
			result.load();
			
			char[][] components = new char[result.getInt()][];
			for(int i = 0; i < components.length; i++)
			{
				char[] component = new char[result.getInt()];
				for(int compPos = 0; compPos < component.length; compPos++)
					component[compPos] = result.getChar();
				components[i] = component;
			}
			
			boolean[] variables = new boolean[result.getInt()];
			for(int i = 0; i < variables.length; i++)
				variables[i] = result.get() == 1;
			
			int[] queryOrder = new int[result.getInt()];
			for(int i = 0; i < queryOrder.length; i++)
				queryOrder[i] = result.getInt();
			
			if (result.remaining() != 0)
				throw new IOException("Invalid file format.");
			return new BoundTemplate(components, variables, queryOrder);
		}
	}
	
	private int getMaximumSize()
	{
		int maxSize = Integer.BYTES * 3; // To store length of 4 arrays.
		
		for(char[] component : components)
		{
			maxSize += component.length * Character.BYTES + Integer.BYTES; // Need 4 bytes to distinguish components.
		}
		
		maxSize += variablePositions.length;
		maxSize += queryOrder.length * Integer.BYTES;	
		return maxSize;
	}
}
