#!/usr/bin/env bash
set -euo pipefail

APP_NAME="AiFactory"
VERSION="${1:-1.0.0}"
IDENTITY="${IDENTITY:-Developer ID Application: TU_NOMBRE (TEAMID)}"
PROFILE="${AC_PROFILE:-AC_PROFILE}" # xcrun notarytool store-credentials --apple-id ... --team-id ... --keychain-profile AC_PROFILE

APP="desktop/build/compose/binaries/main/app/${APP_NAME}.app"
DMG="desktop/build/compose/binaries/main/dmg/${APP_NAME}-${VERSION}.dmg"
ENT="desktop/src/jvmMain/resources/macos/entitlements.plist"

echo "▶️ Gradle distributable"
./gradlew :desktop:createDistributable

echo "🔏 Codesign app"
codesign --deep --force --options runtime --timestamp \
  --entitlements "$ENT" \
  --sign "$IDENTITY" "$APP"
codesign --verify --deep --strict "$APP"

echo "📝 Notarize DMG"
xcrun notarytool submit "$DMG" --keychain-profile "$PROFILE" --wait

echo "📎 Staple"
xcrun stapler staple "$DMG"

echo "✅ Done: $DMG"
