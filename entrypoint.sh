#!/bin/sh
set -eu

normalize_db_url() {
  url="$1"
  if [ -n "$url" ]; then
    case "$url" in
      postgres://*|postgresql://*)
        printf '%s' "$url" | sed 's#^postgresql://#jdbc:postgresql://#; s#^postgres://#jdbc:postgresql://#'
        ;;
      *)
        printf '%s' "$url"
        ;;
    esac
  fi
}

if [ -n "${SPRING_DATASOURCE_URL:-}" ]; then
  export SPRING_DATASOURCE_URL="$(normalize_db_url "$SPRING_DATASOURCE_URL")"
fi

if [ -n "${DATABASE_URL:-}" ]; then
  export DATABASE_URL="$(normalize_db_url "$DATABASE_URL")"
fi

exec java ${JAVA_OPTS:-} -jar /app/app.jar
