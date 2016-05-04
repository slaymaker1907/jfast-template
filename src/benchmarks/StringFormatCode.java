package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;

public class StringFormatCode implements JunkCode
{
	private String format = "{'username':%s, 'password':%s, 'description':%s }";
	private int stringSize;
	private RandomStringFactory factory;
	private Object[] variables = new String[3];
	private String result;
	
	public StringFormatCode(int stringSize)
	{
		this.stringSize = stringSize;
		this.factory = new RandomStringFactory();
	}
	
	@Override
	public void run()
	{
		result = String.format(format, variables);
	}

	@Override
	public void setup()
	{
		for(int i = 0; i < variables.length; i++)
			variables[i] = factory.nextString(stringSize);
	}

	@Override
	public int getJunkState()
	{
		return result.hashCode();
	}
}
