package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;
import com.dyllongagnier.cs4150.timing.MinTimer;
import com.dyllongagnier.template.StringExtractor;

public class ExtractorBenchmark
{
	public static class ExtractorCode implements JunkCode
	{
		private StringExtractor extractor = new StringExtractor();
		protected char[] result;
		private RandomStringFactory factory = new RandomStringFactory();
		private int stringSize;
		protected String toUse;
		
		public ExtractorCode(int stringSize)
		{
			this.stringSize = stringSize;
		}
		
		@Override
		public int getJunkState()
		{
			int sum = 0;
			for(char ch : result)
				sum += ch;
			return sum;
		}

		@Override
		public void run()
		{
			result = extractor.getArray(toUse);
		}

		@Override
		public void setup()
		{
			toUse = factory.nextString(stringSize);
		}
	}
	
	public static void main(String[] args)
	{
		int maxSize = 100;
		ExtractorCode code = new ExtractorCode(10);
		MinTimer timer = new MinTimer(50);
		timer.warmup(code);
		code = new ExtractorCode(maxSize);
		System.out.println(timer.timeCode(code));
		code = new ToCharArrayCode(10);
		timer.warmup(code);;
		code = new ToCharArrayCode(maxSize);
		System.out.println(timer.timeCode(code));
	}
	
	public static class ToCharArrayCode extends ExtractorCode
	{
		public ToCharArrayCode(int stringSize)
		{
			super(stringSize);
		}

		@Override
		public void run()
		{
			result = toUse.toCharArray();
		}
	}
}
