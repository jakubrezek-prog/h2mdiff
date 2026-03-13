# H2MDiff Quick Start

## Get Running in 30 Seconds

### Prerequisites
- Java 21 or later installed

### Run Immediately

**Linux/macOS:**
```bash
chmod +x h2mdiff
./h2mdiff --version
```

**Windows (Command Prompt):**
```cmd
h2mdiff.bat --version
```

**Windows (PowerShell):**
```powershell
.\h2mdiff.bat --version
```

## Common Commands

```bash
# Fetch a website
h2mdiff fetch https://example.com

# Fetch and save to specific file
h2mdiff fetch https://example.com --output report.html

# Fetch and capture HTTP metadata
h2mdiff fetch https://example.com -m

# Fetch local HTML file
h2mdiff fetch ./report.html

# Fetch with both options
h2mdiff fetch https://example.com -o report.html -m
```

## Troubleshooting

**"Java not found"**
- Install Java 21+ from oracle.com or your package manager

**"h2mdiff.jar not found"**
- The JAR should be in the same directory as h2mdiff (or h2mdiff.bat on Windows)
- If missing, rebuild: `mvn clean package && cp target/h2mdiff.jar .`

**Permission denied (Linux/Mac)**
- Make script executable: `chmod +x h2mdiff`

## Full Documentation

See [README.md](README.md) for complete documentation, installation options, and detailed usage examples.
