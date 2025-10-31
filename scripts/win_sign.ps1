param(
  [string]$Version = "1.0.0"
)

$Pfx = $env:PFX_PATH       # ruta al .pfx
$Pass = $env:PFX_PASS      # password del .pfx
$Ts  = "http://timestamp.digicert.com"
$Base = "desktop\build\compose\binaries\main"

$Exe = Join-Path $Base "exe\AiFactory-$Version.exe"
$Msi = Join-Path $Base "msi\AiFactory-$Version.msi"

Write-Host "▶️ Gradle distributable"
./gradlew :desktop:packageDistributionForCurrentOS

Write-Host "🔏 Sign EXE"
signtool sign /f "$Pfx" /p "$Pass" /tr $Ts /td SHA256 /fd SHA256 "$Exe"

Write-Host "🔏 Sign MSI"
signtool sign /f "$Pfx" /p "$Pass" /tr $Ts /td SHA256 /fd SHA256 "$Msi"

Write-Host "✅ Signed: $Exe , $Msi"
