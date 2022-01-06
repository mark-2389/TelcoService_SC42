#!/usr/bin/env bash

USER='telco'
PASSWORD='jAv_4:Ski:FO'
FILE_PATHS="Tables Triggers Views"

for PATH in ${FILE_PATHS};
do
  for FILE in "${PATH}"/*.sql
  do
     echo 'importing '"${FILE}"
     mysql -u$USER -p$PASSWORD < "${FILE}";
     echo ' '
     echo ' '
  done
done
