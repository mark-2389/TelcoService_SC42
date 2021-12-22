#!/usr/bin/env bash

USER='telco'
PASSWORD='jAv_4:Ski:FO'
FILE_PATH=Triggers

for FILE in "${FILE_PATH}"/*.sql
do
   echo 'importing '"${FILE}"
   mysql -u$USER -p$PASSWORD < "${FILE}";
   echo ' '
   echo ' '
done
