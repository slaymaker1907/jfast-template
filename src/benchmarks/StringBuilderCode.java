package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;

public class StringBuilderCode implements JunkCode
{
	private int stringSize;
	private RandomStringFactory factory;
	private String[] variables = new String[3];
	private String result;
	private int maxSize = 0;
	
	public StringBuilderCode(int stringSize)
	{
		this.stringSize = stringSize;
		this.factory = new RandomStringFactory();
	}
	
	@Override
	public void run()
	{
		StringBuilder result = new StringBuilder(maxSize);
		result.append("{ \"username\":");
		result.append(variables[0]);
		result.append(", \"password\":");
		result.append(variables[1]);
		result.append("\", \"description\":");
		result.append(variables[2]);
		result.append(" }");
		
		this.result = result.toString();
	}

	@Override
	public void setup()
	{
		for(int i = 0; i < variables.length; i++)
			variables[i] = factory.nextString(stringSize);
		maxSize = "{ \"username\":".length() + ", \"password\":".length() + "\", \"description\":".length() + " }".length();
		for(String variable : variables)
			maxSize += variable.length();
	}

	@Override
	public int getJunkState()
	{
		return result.hashCode();
	}
}
