import json

infile = open("input", "r")

data = infile.readline()

data = json.loads(data)

def parse(data):
	temp = 0
	if type(data) is list:
		for i in data:
			temp += parse(i)
	

	if type(data) is int:
		return data

	if type(data) is dict:
		for key in data:
			if data[key] == 'red':
				return 0
			else:
				temp += parse(data[key])

	return temp

total = 0

for entry in data:
	total += parse(entry)

print(total)

