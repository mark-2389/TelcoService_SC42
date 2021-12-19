#!/usr/bin/env python3

from population import *

# a function to generate a tuple
def generate(id):
    return [
        # insert here the values of the tuple to return 
    ]

def main():

    table = 'client'  # <-- insert here the name of the table to edit
    file = 'output/{}.sql'.format(table) # <-- the file is saved to output/table.sql
    db = "telcoservice_db"
    keys = [] # <-- all columns are edited, fill with column names to edit only some columns
    output_insert(file, table, generate, 10, keys=keys, db_name=db)



if __name__ == '__main__':
    main()
