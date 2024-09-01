#!/usr/bin/env bash

golem-cli api-definition add api-definition.json
golem-cli api-deployment deploy --definition twitter-api-v1/0.0.1 --host localhost:9006