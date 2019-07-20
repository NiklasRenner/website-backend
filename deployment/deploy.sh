#!/bin/bash

export GENERATED_KEY=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)
docker-compose up -d --force-recreate --build

attempt_counter=0
max_attempts=10
until $(curl --output /dev/null --silent --head --fail http://localhost:1337/ip); do
    if [ ${attempt_counter} -eq ${max_attempts} ];then
      echo "Max attempts reached"
      exit 1
    fi

    printf '.'
    attempt_counter=$(($attempt_counter+1))
    sleep 2
done

printf "\nendpoint was up after $attempt_counter attempts, output: $(curl -s https://dev.renner.id/ip)"