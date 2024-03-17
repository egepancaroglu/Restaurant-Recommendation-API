#!/bin/bash

psql -U postgres -c "CREATE DATABASE review-service;"
psql -U postgres -c "CREATE USER postgres WITH PASSWORD 'postgres';"
psql -U postgres -c "ALTER ROLE postgres SET client_encoding TO 'utf8';"
psql -U postgres -c "ALTER ROLE postgres SET default_transaction_isolation TO 'read committed';"
psql -U postgres -c "ALTER ROLE postgres SET timezone TO 'UTC';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE review-service TO postgres;