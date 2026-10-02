@echo off
echo Compiling C# Online Shopping System...
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\csc.exe /nologo /out:"%~dp0OnlineShoppingSystem.exe" "%~dp0src\*.cs"
if %ERRORLEVEL% EQU 0 (
    echo Compilation successful: %~dp0OnlineShoppingSystem.exe
) else (
    echo Compilation failed!
)
pause
