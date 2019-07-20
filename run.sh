#!/bin/bash

export GENERATED_KEY=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)
docker-compose up --force-recreate --build