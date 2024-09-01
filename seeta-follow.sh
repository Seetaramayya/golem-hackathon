#!/usr/bin/env bash

set -e
#curl -X POST http://localhost:9881/v1/api/definitions -H "Content-Type: application/json" -d @api-definition.json

[[ -z "$2" ]] && echo "$0 <user-id> <whom-to-follow>" && exit 1

USER_ID=$1
FOLLOW="$2"

set -x
curl -v -X POST -H "Accept: application/json" "${BASE_URL}/${USER_ID}/follow/${FOLLOW}"