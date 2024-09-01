#!/usr/bin/env bash

set -x
curl -X POST http://localhost:9881/v1/api/definitions -H "Content-Type: application/json" -d @api-definition.json