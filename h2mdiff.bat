@echo off
REM h2mdiff - HTML to Markdown Diff Utility
REM Wrapper script for Windows
REM
REM This script locates the h2mdiff fat JAR and executes it with all provided arguments.
REM The JAR is searched for in multiple standard locations.

setlocal enabledelayedexpansion

REM Get the directory where this script is located
set "SCRIPT_DIR=%~dp0"

REM JAR filename
set "JAR_NAME=h2mdiff.jar"

REM Initialize JAR_PATH
set "JAR_PATH="

REM Search paths for the JAR (in order of preference)
for %%P in (
    "%SCRIPT_DIR%%JAR_NAME%"
    "%SCRIPT_DIR%..\lib\%JAR_NAME%"
    "%SCRIPT_DIR%..\target\%JAR_NAME%"
    "C:\Program Files\h2mdiff\%JAR_NAME%"
    "C:\Program Files (x86)\h2mdiff\%JAR_NAME%"
    "%APPDATA%\h2mdiff\%JAR_NAME%"
) do (
    if exist "%%~P" (
        set "JAR_PATH=%%~P"
        goto found_jar
    )
)

REM Check if H2MDIFF_JAR environment variable is set
if defined H2MDIFF_JAR (
    if exist "%H2MDIFF_JAR%" (
        set "JAR_PATH=%H2MDIFF_JAR%"
        goto found_jar
    )
)

:jar_not_found
echo Error: h2mdiff.jar not found in any of the standard locations 1>&2
echo. 1>&2
echo Searched in: 1>&2
echo   - %SCRIPT_DIR%%JAR_NAME% 1>&2
echo   - %SCRIPT_DIR%..\lib\%JAR_NAME% 1>&2
echo   - %SCRIPT_DIR%..\target\%JAR_NAME% 1>&2
echo   - C:\Program Files\h2mdiff\%JAR_NAME% 1>&2
echo   - C:\Program Files (x86)\h2mdiff\%JAR_NAME% 1>&2
echo   - %APPDATA%\h2mdiff\%JAR_NAME% 1>&2
echo. 1>&2
echo Installation options: 1>&2
echo 1. Place h2mdiff.jar in the same directory as this script 1>&2
echo 2. Place h2mdiff.jar in ..\lib\ relative to this script 1>&2
echo 3. Install to C:\Program Files\h2mdiff\ and copy h2mdiff.jar there 1>&2
echo 4. Define H2MDIFF_JAR environment variable pointing to the JAR 1>&2
exit /b 1

:found_jar
REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH 1>&2
    echo Please install Java 21 or later and ensure it's in your PATH 1>&2
    exit /b 1
)

REM Execute Java with the JAR, forwarding all arguments
java -jar "!JAR_PATH!" %*
exit /b %errorlevel%
