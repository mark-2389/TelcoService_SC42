#!/usr/bin/env python3

from population import *

# a function to generate a tuple
def generate(id):
	i = id + 1
	return [
		"Employee{}".format(i),
		"Employee{}Employee{}".format(i, i),
		"Employee{}@mail.com".format(i).lower()
	]

def main():
	table = 'employee'
	file = 'output/{}.sql'.format(table)
	db = "telcoservice_db"
	keys = ["username", "password", "email"]
	num_entries = 5

	output_insert(file, table, generate, num_entries, keys=keys, db_name=db)
	

if __name__ == '__main__':
	main()
