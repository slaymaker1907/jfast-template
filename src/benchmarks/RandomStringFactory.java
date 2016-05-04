package benchmarks;

import java.util.Random;

public class RandomStringFactory
{
	private Random gen = new Random();
	
	public String nextString(int length)
	{
		StringBuilder builder = new StringBuilder(length);
		for(int i = 0; i < length; i++)
			builder.append(nextChar());
		return builder.toString();
	}
	
	public char nextChar()
	{
		return (char) (gen.nextInt(charRange()) + minChar());
	}
	
	public char minChar()
	{
		return 32;
	}
	
	public char maxChar()
	{
		return 127;
	}
	
	public int charRange()
	{
		return maxChar() - minChar();
	}
}
