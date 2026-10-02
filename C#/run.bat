@echo off
if not exist "%~dp0OnlineShoppingSystem.exe" (
    echo Executable not found. Compiling first...
    C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe /nologo /out:"%~dp0OnlineShoppingSystem.exe" "%~dp0src\*.cs"
)
if exist "%~dp0OnlineShoppingSystem.exe" (
    "%~dp0OnlineShoppingSystem.exe"
) else (
    echo Failed to build executable.
    pause
)
