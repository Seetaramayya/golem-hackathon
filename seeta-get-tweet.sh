#!/usr/bin/env bash

set -e
#curl -X POST http://localhost:9881/v1/api/definitions -H "Content-Type: application/json" -d @api-definition.json

[[ -z "$1" ]] && echo "$0 <user-id>" && exit 1

USER_ID=$1

curl -v -X GET -H "Accept: application/json" "${BASE_URL}/${USER_ID}/tweets"