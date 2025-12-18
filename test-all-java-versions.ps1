# Script para probar el proyecto con múltiples versiones de Java
Write-Host "=== Probando el proyecto con múltiples versiones de Java ===" -ForegroundColor Cyan

$javaVersions = @(
    @{Version="17"; Path="C:\Users\alerg\.jdk\jdk-17.0.16(1)"},
    @{Version="21"; Path="C:\Program Files\Java\jdk-21"}
)

$results = @()

foreach ($java in $javaVersions) {
    Write-Host "`n--- Probando con Java $($java.Version) ---" -ForegroundColor Yellow
    
    if (Test-Path $java.Path) {
        $env:JAVA_HOME = $java.Path
        $env:PATH = "$($java.Path)\bin;$env:PATH"
        
        Write-Host "Compilando..." -ForegroundColor Gray
        mvn clean compile -q
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Ejecutando tests..." -ForegroundColor Gray
            mvn test -q
            
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✓ Java $($java.Version): EXITOSO" -ForegroundColor Green
                $results += "✓ Java $($java.Version): EXITOSO"
            } else {
                Write-Host "✗ Java $($java.Version): FALLÓ EN TESTS" -ForegroundColor Red
                $results += "✗ Java $($java.Version): FALLÓ EN TESTS"
            }
        } else {
            Write-Host "✗ Java $($java.Version): FALLÓ EN COMPILACIÓN" -ForegroundColor Red
            $results += "✗ Java $($java.Version): FALLÓ EN COMPILACIÓN"
        }
    } else {
        Write-Host "✗ Java $($java.Version): NO ENCONTRADO en $($java.Path)" -ForegroundColor Red
        $results += "✗ Java $($java.Version): NO ENCONTRADO"
    }
}

Write-Host "`n=== RESUMEN ===" -ForegroundColor Cyan
$results | ForEach-Object { Write-Host $_ }
