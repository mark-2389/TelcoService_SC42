#!/usr/bin/env python3

from population import *

# a function to generate a tuple
def generate(id):
    ref = ['Davide', 'Giammarco', 'Marco', 'Niccolo', 'Paolo', 'Cecilia', 'Elisa']

    return [
        '{}'.format(ref[id]),
        '{}{}'.format(ref[id], ref[id]),
        '{}@mail.com'.format(ref[id].lower())
    ]

def meme_generate(id):
    ref = ['CristianoRonaldo', 'Cavlo']
    pwd = ['Siuuum', 'FovzaFevvavi']
    return [
        '{}'.format(ref[id]),
        '{}'.format(pwd[id]),
        '{}@mail.com'.format(ref[id].lower())
    ]

def main():
    table = 'client'
    file = 'output/{}.sql'.format(table)
    db = "telcoservice_db"
    keys = ["username", "password", "email"]
    output_insert(file, table, generate, 7, keys=keys, db_name=db)



if __name__ == '__main__':
    main()
