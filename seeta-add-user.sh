#!/usr/bin/env bash

set -e
#curl -X POST http://localhost:9881/v1/api/definitions -H "Content-Type: application/json" -d @api-definition.json

[[ -z "$1" ]] && echo "$0 <name-of-the-user>" && exit 1

name=$1

curl -v -X POST -H "Accept: application/json" "${BASE_URL}/add-user/${name}"