#!/bin/sh

docker run --name postgresql -p 5432:5432 -e POSTGRES_PASSWORD=admin123 -e POSTGRES_USER=postgresql -d postgres:14.12