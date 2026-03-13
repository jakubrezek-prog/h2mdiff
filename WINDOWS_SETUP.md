# Windows Setup Guide

## Quick Setup for Windows

### Prerequisites
- Java 21 or later installed
- Command Prompt or PowerShell

### Run Immediately

**Command Prompt (cmd.exe):**
```cmd
h2mdiff.bat --version
```

**PowerShell:**
```powershell
.\h2mdiff.bat --version
```

### Common Commands

**Command Prompt:**
```cmd
REM Fetch a website
h2mdiff.bat fetch https://example.com

REM Fetch with custom output and metadata
h2mdiff.bat fetch https://example.com --output report.html --metadata

REM Fetch local file
h2mdiff.bat fetch report.html --output backup.html

REM Show help
h2mdiff.bat --help
```

**PowerShell:**
```powershell
# Fetch a website
.\h2mdiff.bat fetch https://example.com

# Fetch with custom output and metadata
.\h2mdiff.bat fetch https://example.com -o report.html -m

# Fetch local file
.\h2mdiff.bat fetch report.html -o backup.html

# Show help
.\h2mdiff.bat --help
```

## Make It Global (Add to PATH)

### Option 1: Using Environment Variables UI

1. Copy `h2mdiff.bat` and `h2mdiff.jar` to a folder (e.g., `C:\h2mdiff\`)
2. Open **Settings** → **System** → **About** → **Advanced system settings**
3. Click **Environment Variables**
4. Under "User variables" or "System variables", click **New**
5. Variable name: `Path`
6. Variable value: `C:\h2mdiff` (or your chosen path)
7. Click **OK** and restart Command Prompt

Then you can run: `h2mdiff.bat --version` from anywhere

### Option 2: Using Command Prompt (Admin)

```cmd
REM Create h2mdiff directory
mkdir C:\h2mdiff

REM Copy files
copy h2mdiff.bat C:\h2mdiff\
copy h2mdiff.jar C:\h2mdiff\

REM Add to PATH permanently using setx
setx PATH "%PATH%;C:\h2mdiff"

REM Restart Command Prompt and test
h2mdiff.bat --version
```

### Option 3: Using PowerShell (Admin)

```powershell
# Create h2mdiff directory
New-Item -ItemType Directory -Path "C:\h2mdiff" -Force

# Copy files
Copy-Item -Path "h2mdiff.bat" -Destination "C:\h2mdiff\"
Copy-Item -Path "h2mdiff.jar" -Destination "C:\h2mdiff\"

# Add to PATH
[System.Environment]::SetEnvironmentVariable("PATH", 
  [System.Environment]::GetEnvironmentVariable("PATH", [System.EnvironmentVariableTarget]::User) + ";C:\h2mdiff",
  [System.EnvironmentVariableTarget]::User)

# Restart PowerShell and test
.\h2mdiff.bat --version
```

## Troubleshooting

### "Java is not installed or not in PATH"
- Download Java 21 from oracle.com
- Install it
- Restart your Command Prompt/PowerShell

### "The term 'h2mdiff.bat' is not recognized" (PowerShell)
- Use `.\h2mdiff.bat` instead of `h2mdiff.bat`
- Or use Command Prompt instead of PowerShell

### "h2mdiff.jar not found"
- Make sure `h2mdiff.jar` is in the same directory as `h2mdiff.bat`
- Or set the `H2MDIFF_JAR` environment variable:
  ```cmd
  set H2MDIFF_JAR=C:\path\to\h2mdiff.jar
  h2mdiff.bat --version
  ```

## See Also

- [README.md](README.md) - Full documentation
- [QUICK_START.md](QUICK_START.md) - Quick reference
