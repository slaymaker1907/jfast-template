package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;
import com.dyllongagnier.cs4150.timing.MinTimer;
import com.dyllongagnier.template.TemplateFactory;;

public class BenchmarkMain
{
	public static void main(String[] args)
	{
		int maxSize = 255;
		 MinTimer timer = new MinTimer(50);
		 TemplateFactory factory = new TemplateFactory();
		 JunkCode code = new FastTemplateCode(factory, 1);
		 timer.warmup(code);
		 System.out.println("Warmed up.");
		 
		 code = new FastTemplateCode(factory, maxSize);
		 System.out.println(timer.timeCode(code).toNanos());
		 
		 code = new StringFormatCode(1);
		 timer.warmup(code);
		 code = new StringFormatCode(maxSize);
		 System.out.println(timer.timeCode(code).toNanos());
		 
		 code = new StringBuilderCode(1);
		 timer.warmup(code);
		 code = new StringBuilderCode(maxSize);
		 System.out.println(timer.timeCode(code).toNanos());
	}
}
