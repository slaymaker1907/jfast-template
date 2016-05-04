package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;

public class StringBuilderCode implements JunkCode
{
	private int stringSize;
	private RandomStringFactory factory;
	private String[] variables = new String[3];
	private StringBuilder result;
	private int maxSize = 0;
	
	public StringBuilderCode(int stringSize)
	{
		this.stringSize = stringSize;
		this.factory = new RandomStringFactory();
	}
	
	@Override
	public void run()
	{
		result = new StringBuilder(maxSize);
		for(int i = 0; i < 10000; i++)
		{
			result.append(variables[0]);
			result.append(", ");
			result.append(variables[1]);
			result.append(", ");
			result.append(variables[2]);
		}
	}

	@Override
	public void setup()
	{
		for(int i = 0; i < variables.length; i++)
			variables[i] = factory.nextString(stringSize);
		maxSize = ", ".length() * 2 * 10000;
		for(String variable : variables)
			maxSize += variable.length() * 10000;
	}

	@Override
	public int getJunkState()
	{
		return result.hashCode();
	}
}
