#! /bin/bash

INITDB=$(cd $(dirname $0)/initdb.d; pwd)

docker run --rm -it -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v $INITDB:/docker-entrypoint-initdb.d postgres:latest