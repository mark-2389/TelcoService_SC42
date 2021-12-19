#!/usr/bin/env python3

from population import *

# a function to generate a user
def generate_user(id):
    return [
        'user{}'.format(id),
        'user{}@mail.com'.format(id),
        '{}'.format(id*3.7),
        '{}/{}/{}'.format(id, id, id)
    ]

def main():
    tests = {'client' : generate_user}

    for k, v in tests.items():
        file = 'tests/{}.sql'.format(k)
        table = '{}'.format(k)
        db = "telcoservice_db"
        output_insert(file, table, v, 10, db_name=db)



if __name__ == '__main__':
    main()
