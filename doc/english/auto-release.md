# Auto-release (tag → Maven Central)

Publish is triggered by a **version tag**. Secrets never live in git.

## Effect on SDK users

**None.** Smartech Nexus is used only while **this repo builds/publishes** in CI (to download AGP and other build tools when Google is filtered).

App developers still use:

```gradle
implementation 'io.adtrace:android-sdk:3.0.0'
```

from **Maven Central** (plus `maven.myket.ir` only if they add the Myket plugin). They do **not** need `repo.smartech.ir`.

## One-time GitLab setup (secure)

In **GitLab → Settings → CI/CD → Variables**, add:

| Key | Value | Flags |
| --- | --- | --- |
| `SONATYPE_USERNAME` | Maven Central / OSSRH username | **Masked**, **Protected** |
| `SONATYPE_PASSWORD` | Password or token | **Masked**, **Protected** |
| `SIGNING_KEY` | Full ASCII-armored private key (`-----BEGIN PGP PRIVATE KEY BLOCK-----` …) | **Protected** (Masked if GitLab allows the length) |
| `SIGNING_PASSWORD` | PGP passphrase | **Masked**, **Protected** |
| `NEXUS_USERNAME` | Smartech Nexus user (`repo.smartech.ir`) | **Masked**, **Protected** |
| `NEXUS_PASSWORD` | Smartech Nexus password | **Masked**, **Protected** |

Also:

1. Protect the release branch (`stage` / `main`) and allow only maintainers to push tags.
2. **Protect tags** matching `v*` (or `*`) so Protected variables are injected.
3. Do **not** put credentials in `gradle.properties`, `local.properties`, or CI YAML.
4. Ask DevOps to confirm `maven-sdk-group` proxies **Google Maven** (so AGP can be cached/fetched).

### Export your signing key (local machine)

```bash
gpg --list-secret-keys --keyid-format LONG
gpg --armor --export-secret-keys YOUR_KEY_ID
```

Paste the armored output into `SIGNING_KEY`. Never commit it.

## Release checklist (each version)

1. Bump `coreVersionName` in `build.gradle`, `VERSION`, and `CHANGE_LOG.md`.
2. Merge to the release branch (`stage`).
3. Tag and push (does **not** publish yet):

```bash
git tag v3.0.0
git push origin v3.0.0
```

4. Open the tag pipeline in GitLab.
5. Confirm `verify_release` is green.
6. Click **Play** on `publish_maven` (manual gate).
7. In Sonatype / Maven Central Portal, close & release the staging repository if your account still uses OSSRH staging.
8. After a successful publish, merge `stage` → `main`.

If you fix CI after tagging, move the tag to the new commit:

```bash
git tag -f v3.0.0
git push origin -f v3.0.0
```

## Security notes

- Password/token and PGP key exist only as GitLab CI variables.
- `publish_maven` is **manual** so a mistaken tag cannot publish alone.
- Rotate any credential that was shared in chat, email, or tickets; set the new value only in GitLab variables.
- Prefer a Sonatype **user token** over the account password when available.
