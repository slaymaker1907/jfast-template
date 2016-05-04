package benchmarks;

import com.dyllongagnier.cs4150.timing.JunkCode;
import com.dyllongagnier.template.BoundTemplate;
import com.dyllongagnier.template.ObjectSequence;
import com.dyllongagnier.template.SequencedModule;
import com.dyllongagnier.template.Template;
import com.dyllongagnier.template.TemplateFactory;
import com.dyllongagnier.template.TemplateVariable;
import com.dyllongagnier.template.TemplateVariable.TemplateVariableBuilder;
import com.dyllongagnier.template.SequencedModule.SequencedModuleBuilder;

public class FastTemplateCode implements JunkCode
{
	private BoundTemplate template;
	private RandomStringFactory factory = new RandomStringFactory();
	private String[] variables = new String[3];
	private StringBuilder result;
	private int stringSize;
	private ObjectSequence sequence;
	
	public FastTemplateCode(TemplateFactory factory, int stringSize)
	{
		this.stringSize = stringSize;
		try
		{
			String[] copies = new String[10000];
			for(int i = 0; i < copies.length; i++)
				copies[i] = "{user.username}{user.password}{user.description}";
			String template = String.join(", ", copies);
			Template temp = factory.getTemplate(template);
			TemplateVariable username = new TemplateVariableBuilder()
					.addModule("user")
					.addModule("username")
					.build();
			TemplateVariable password = new TemplateVariableBuilder()
					.addModule("user")
					.addModule("password")
					.build();
			TemplateVariable description = new TemplateVariableBuilder()
					.addModule("user")
					.addModule("description")
					.build();
			SequencedModule module = new SequencedModuleBuilder()
					.addVariable(description)
					.addVariable(username)
					.addVariable(password)
					.build();
			this.template = temp.bind(module);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int getJunkState()
	{
		return result.hashCode();
	}

	@Override
	public void run()
	{
		sequence = this.template.newObjectSequence();
		for(int i = 0; i < variables.length; i++)
			sequence.addObject(variables[i]);
		result = template.applyTemplate(sequence);
	}

	@Override
	public void setup()
	{
		sequence = this.template.newObjectSequence();
		for(int i = 0; i < variables.length; i++)
			variables[i] = factory.nextString(stringSize);
	}

}
