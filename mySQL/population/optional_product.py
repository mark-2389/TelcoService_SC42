#!/usr/bin/env python3

from population import *

# a function to generate a tuple
def generate(id):
    ref = ['1', '2','3','4','5','6']
    return [
        'optional_product_{}'.format(ref[id]),
        '{}'.format((id+1)*1.64),
    ]

def main():

    table = 'optional_product'  # <-- insert here the name of the table to edit
    file = 'output/{}.sql'.format(table) # <-- the file is saved to output/table.sql
    db = "telcoservice_db"
    keys = ["name", "monthly_fee"] # <-- all columns are edited, fill with column names to edit only some columns
    output_insert(file, table, generate, 6, keys=keys, db_name=db)



if __name__ == '__main__':
    main()
