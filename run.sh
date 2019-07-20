#!/bin/bash

export GENERATED_KEY=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)
docker-compose up -d --force-recreate --build
timeout 30 bash -c 'while [[ "$(curl -s -w ''%{http_code}'' https://dev.renner.id/ip)" != "200" ]]; do sleep 5; done' || false
echo done