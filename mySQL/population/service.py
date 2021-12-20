#!/usr/bin/env python3
import math
from population import *

# a function to generate a tuple
def generate(id):
    ref = ['FIXED_PHONE', 'MOBILE_PHONE', 'FIXED_INTERNET', 'MOBILE_INTERNET']
    return [
        '{}'.format(ref[math.floor((id*1.367)%4)])
    ]

def main():

    table = 'service'  # <-- insert here the name of the table to edit
    file = 'output/{}.sql'.format(table) # <-- the file is saved to output/table.sql
    db = "telcoservice_db"
    keys = [] # <-- all columns are edited, fill with column names to edit only some columns
    output_insert(file, table, generate, 15, keys=keys, db_name=db)



if __name__ == '__main__':
    main()
