#!/usr/bin/env bash
# Per-boot runtime initialization: bring up MySQL and ensure the app database
# and user exist. Must tolerate restarts and reach a clear ready state.
set -euo pipefail

DB_NAME="mesa_dinve"
DB_USER="admindinve"
# Matches the credentials committed in src/main/resources/application.properties
# so the app connects with no code changes (only the datasource URL is pointed
# at localhost via the terminal command).
DB_PASS="dinve-1bd346"

# Ensure the MySQL data directory is initialized (first boot from a fresh image).
# /var/lib/mysql is only readable by the mysql user, so probe it via sudo.
if ! sudo test -d /var/lib/mysql/mysql; then
  echo "Initializing MySQL data directory..."
  sudo mkdir -p /var/lib/mysql
  sudo chown -R mysql:mysql /var/lib/mysql
  sudo mysqld --initialize-insecure --user=mysql
fi

# Start the MySQL service (idempotent: no-op if already running).
sudo service mysql start || sudo mysqld_safe --user=mysql &

# Wait until the server accepts connections.
echo "Waiting for MySQL to become ready..."
for i in $(seq 1 60); do
  if sudo mysqladmin ping >/dev/null 2>&1; then
    echo "MySQL is ready."
    break
  fi
  sleep 1
done

# Create the database and application user idempotently.
sudo mysql <<SQL
CREATE DATABASE IF NOT EXISTS ${DB_NAME} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED WITH mysql_native_password BY '${DB_PASS}';
CREATE USER IF NOT EXISTS '${DB_USER}'@'127.0.0.1' IDENTIFIED WITH mysql_native_password BY '${DB_PASS}';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'127.0.0.1';
FLUSH PRIVILEGES;
SQL

echo "Database '${DB_NAME}' and user '${DB_USER}' are ready."
