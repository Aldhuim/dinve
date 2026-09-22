#!/usr/bin/env bash
# Idempotent repository bootstrap: build the application and prepare local state.
# Runs after the repository is checked out. Must terminate and be safe to re-run.
set -euo pipefail

cd "$(dirname "$0")/.."

export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-11-openjdk-amd64}"

# Directory used by the file-upload feature (relative path resolved at runtime).
mkdir -p uploads

# Build the fat jar. Tests require a running database, so they are skipped here;
# run them explicitly via ./mvnw test once MySQL is up.
chmod +x mvnw
./mvnw -B -DskipTests clean package
