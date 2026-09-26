#!/usr/bin/env bash
# Publish signed AARs to Maven Central (OSSRH). Secrets come only from CI env vars.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "${ROOT}"

"${ROOT}/scripts/ci/verify-tag-version.sh"

required_vars=(
  SONATYPE_USERNAME
  SONATYPE_PASSWORD
  SIGNING_KEY
  SIGNING_PASSWORD
)

for var in "${required_vars[@]}"; do
  if [[ -z "${!var:-}" ]]; then
    echo "ERROR: missing required CI variable: ${var}"
    echo "Set it in GitLab → Settings → CI/CD → Variables (Masked + Protected)."
    exit 1
  fi
done

# Never print secret values. Confirm presence only.
echo "Sonatype user: ${SONATYPE_USERNAME}"
echo "Signing key:   present ($(wc -c <<<"${SIGNING_KEY}" | tr -d ' ') bytes armored)"
echo "Signing pass:  present"

# Map to Gradle project properties without writing secrets to disk.
export ORG_GRADLE_PROJECT_sonatypeUsername="${SONATYPE_USERNAME}"
export ORG_GRADLE_PROJECT_sonatypePassword="${SONATYPE_PASSWORD}"
export ORG_GRADLE_PROJECT_signingKey="${SIGNING_KEY}"
export ORG_GRADLE_PROJECT_signingPassword="${SIGNING_PASSWORD}"

# Modules published together on each version tag (same coreVersionName).
MODULES=(
  :android-sdk
  :android-sdk-plugin-oaid
  :android-sdk-plugin-webbridge
  :android-sdk-imei-plugin
  :android-sdk-samsung-plugin
  :android-sdk-xiaomi-plugin
  :android-sdk-vivo-plugin
  :android-sdk-myket-plugin
)

PUBLISH_TASKS=()
for module in "${MODULES[@]}"; do
  PUBLISH_TASKS+=("${module}:publish")
done

echo "Publishing: ${PUBLISH_TASKS[*]}"

# assemble + publish to OSSRH staging. Does NOT auto-close/release the staging repo
# unless you add a separate Central Portal / nexus-staging step later.
./gradlew --no-daemon --stacktrace "${PUBLISH_TASKS[@]}"

echo "Publish tasks finished. Complete staging → release in Sonatype / Maven Central Portal if required."
