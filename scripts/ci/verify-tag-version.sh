#!/usr/bin/env bash
# Verify git tag matches Gradle coreVersionName / VERSION file. No publish.
set -euo pipefail

TAG="${CI_COMMIT_TAG:-${1:-}}"
if [[ -z "${TAG}" ]]; then
  echo "ERROR: no tag provided (CI_COMMIT_TAG or arg1)."
  exit 1
fi

# Accept v2.6.1 or 2.6.1
VERSION="${TAG#v}"
if [[ ! "${VERSION}" =~ ^[0-9]+\.[0-9]+\.[0-9]+([.-][A-Za-z0-9]+)?$ ]]; then
  echo "ERROR: tag '${TAG}' is not a semver version (expected vX.Y.Z or X.Y.Z)."
  exit 1
fi

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "${ROOT}"

GRADLE_VERSION="$(sed -n "s/.*coreVersionName = '\\([^']*\\)'.*/\\1/p" build.gradle | head -1)"
FILE_VERSION="$(tr -d '[:space:]' < VERSION)"

echo "Tag version:     ${VERSION}"
echo "build.gradle:    ${GRADLE_VERSION}"
echo "VERSION file:    ${FILE_VERSION}"

if [[ "${VERSION}" != "${GRADLE_VERSION}" ]]; then
  echo "ERROR: tag version (${VERSION}) != build.gradle coreVersionName (${GRADLE_VERSION})."
  exit 1
fi

if [[ "${VERSION}" != "${FILE_VERSION}" ]]; then
  echo "ERROR: tag version (${VERSION}) != VERSION file (${FILE_VERSION})."
  exit 1
fi

echo "OK: tag matches project version ${VERSION}."
