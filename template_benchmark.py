import timeit
import random

class RandomStringFactory:
	def next_char(self):
		return chr(random.randint(32, 128))
	
	def next_string(self, size):
		return ''.join([self.next_char() for i in range(size)])
	
class StringFormatter:
	def __init__(self):
		factory = RandomStringFactory()
		self.variables = [factory.next_string(10000) for i in range(3)]
		self.template = ''.join(['{username}, {password}, {description}' for i in range(10000)])
	
	def run(self):
		self.result = self.template.format(username=self.variables[0], password=self.variables[1], description=self.variables[2])

run_count = 100

setup = '''
import template_benchmark as t
formatter = t.StringFormatter()
'''

formatter = StringFormatter()
print(timeit.timeit(stmt='formatter.run()', globals=globals(), number=run_count) / run_count * 1000)
print(len(formatter.result))