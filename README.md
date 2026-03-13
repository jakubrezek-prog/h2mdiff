# H2MDiff - HTML to Markdown Diff Utility

A pure Java command-line utility for fetching HTML pages, converting them to Markdown, and generating diffs between versions. Perfect for tracking changes in generated reports like Harbor Trivy CVE reports.

## Current Features (v0.1.0)

### Fetch Command
- Download HTML from URLs with automatic redirects
- Read HTML from local files
- Automatic output filename generation from domain names
- Optional HTTP metadata capture (status code, headers, fetch time)
- Comprehensive error handling with user-friendly messages

## Installation

### Prerequisites
- **Java 21 or later** - Required to run h2mdiff
- Check your Java version: `java -version`
- If Java is not installed, download from [oracle.com](https://www.oracle.com/java/technologies/downloads/#java21) or use your package manager

### Option 1: Quick Setup (Recommended)

1. **Get the latest h2mdiff.jar** from the target directory after building:
   ```bash
   mvn package
   ```

2. **Choose your platform:**

   **Linux/macOS:**
   ```bash
   # Make the wrapper executable
   chmod +x h2mdiff
   
   # Copy to a directory in your PATH, or use ./h2mdiff to run locally
   sudo cp h2mdiff /usr/local/bin/
   
   # Optional: Copy JAR to system location
   sudo mkdir -p /usr/local/lib/h2mdiff
   sudo cp target/h2mdiff.jar /usr/local/lib/h2mdiff/
   ```

   **Windows:**
   ```cmd
   # Copy both files to a directory, or add to PATH
   copy h2mdiff.bat C:\Program Files\h2mdiff\
   copy target\h2mdiff.jar C:\Program Files\h2mdiff\
   
   # Then add C:\Program Files\h2mdiff to your PATH environment variable
   ```

3. **Verify installation:**
   ```bash
   h2mdiff --version
   ```

### Option 2: Manual Setup

1. Download `h2mdiff.jar` (built with `mvn package`)
2. Place in the same directory as the wrapper script or in `../lib/`
3. Make wrapper executable (Linux/Mac): `chmod +x h2mdiff`
4. Run: `./h2mdiff` or `h2mdiff` (if in PATH)

### Option 3: Environment Variable

Set the `H2MDIFF_JAR` environment variable to point to your JAR:

```bash
# Linux/macOS
export H2MDIFF_JAR=/path/to/h2mdiff.jar
h2mdiff --version

# Windows (Command Prompt)
set H2MDIFF_JAR=C:\path\to\h2mdiff.jar
h2mdiff --version

# Windows (PowerShell)
$env:H2MDIFF_JAR="C:\path\to\h2mdiff.jar"
h2mdiff --version
```

## Usage

### Basic Commands

```bash
# Show version
h2mdiff --version

# Show help
h2mdiff --help

# Fetch HTML from URL (saves to domain-based filename)
h2mdiff fetch https://example.com

# Fetch and specify output file
h2mdiff fetch https://example.com --output page.html

# Fetch with metadata capture
h2mdiff fetch https://example.com --metadata

# Fetch with both custom output and metadata
h2mdiff fetch https://example.com -o page.html -m

# Fetch from local file
h2mdiff fetch ./local-page.html

# Fetch with short flags
h2mdiff fetch https://example.com -o report.html -m
```

### Fetch Command Details

#### Command Syntax
```
h2mdiff fetch <url|file> [options]
```

#### Arguments
- `<url|file>` - URL (http:// or https://) or local file path (required)

#### Options
- `--output, -o <path>` - Output file path (default: domain-based name for URLs, original name for files)
- `--metadata, -m` - Capture and save HTTP metadata to `.metadata.json` file

#### Examples

**Fetch a web page:**
```bash
h2mdiff fetch https://example.com
# Creates: example-com.html
```

**Fetch with custom output:**
```bash
h2mdiff fetch https://example.com/report --output myreport.html
# Creates: myreport.html
```

**Fetch with metadata (captures HTTP headers and fetch time):**
```bash
h2mdiff fetch https://example.com -m
# Creates: example-com.html and example-com.metadata.json
```

**Fetch local HTML file:**
```bash
h2mdiff fetch ./report.html --output report-copy.html
# Creates: report-copy.html
```

**Combine options:**
```bash
h2mdiff fetch https://example.com/cve-report -o cve-report-2024.html -m
# Creates:
# - cve-report-2024.html (the HTML content)
# - cve-report-2024.metadata.json (HTTP metadata)
```

### Metadata File Format

When using `--metadata` flag, metadata is saved as JSON:

```json
{
  "source": "http",
  "url": "https://example.com",
  "status_code": "200",
  "fetch_time": "2024-03-13T12:30:45.123456Z",
  "content_type": "text/html; charset=utf-8",
  "content_length": "45678",
  "last_modified": "Wed, 13 Mar 2024 10:00:00 GMT"
}
```

For local files:
```json
{
  "source": "local_file",
  "file_path": "./report.html",
  "fetch_time": "2024-03-13T12:30:45.123456Z"
}
```

## Use Cases

### Harbor Trivy CVE Reports
Track changes in security vulnerability reports:

```bash
# Initial scan
h2mdiff fetch https://harbor.example.com/trivy/report.html -o trivy-report-2024-03-13.html -m

# Later scan
h2mdiff fetch https://harbor.example.com/trivy/report.html -o trivy-report-2024-03-14.html -m

# Compare the two reports (convert to MD and diff - coming in next version)
```

### Local HTML Report Tracking
Monitor locally-generated reports:

```bash
h2mdiff fetch ./daily-report.html -o daily-report-backup.html -m
```

## Troubleshooting

### Java not found
```
Error: Java is not installed or not in PATH
```
**Solution:** Install Java 21+ from oracle.com or your package manager

### h2mdiff.jar not found
```
Error: h2mdiff.jar not found in any of the standard locations
```
**Solution:** Either:
- Place `h2mdiff.jar` in the same directory as the wrapper script
- Set `H2MDIFF_JAR` environment variable
- Copy to system location: `/usr/local/lib/h2mdiff/` (Linux/Mac) or `C:\Program Files\h2mdiff\` (Windows)

### Permission denied (Linux/Mac)
```
bash: h2mdiff: Permission denied
```
**Solution:** Make the script executable:
```bash
chmod +x h2mdiff
```

### Network timeout
```
Error fetching: Fetch interrupted
```
**Solution:** The default timeout is 30 seconds. Try again or check your internet connection.

### File not found
```
Error fetching: File not found: /path/to/file.html
```
**Solution:** Check the file path and ensure it exists

## Building from Source

### Prerequisites
- Java 21 JDK
- Maven 3.6+

### Build Steps

```bash
# Clone repository
git clone <repository-url>
cd h2mdiff

# Build the fat JAR
mvn clean package

# Run tests
mvn test

# JAR location
ls -la target/h2mdiff.jar
```

### Build Output
- `target/h2mdiff.jar` - Fat JAR with all dependencies (approximately 2.6MB)
- `target/h2mdiff-0.1.0-SNAPSHOT.jar` - Plain JAR (smaller, dependencies not included)

## Upcoming Features

- **HTML-to-Markdown Conversion** - Convert HTML to clean Markdown format
- **Markdown Diffing** - Generate diffs between Markdown versions
- **Configuration Files** - Support for `.h2mdiff.yml` config files
- **Batch Processing** - Process multiple URLs from a file
- **Output Directory Support** - Organize multiple fetches into directories

## Project Structure

```
h2mdiff/
├── src/
│   ├── main/java/com/jr/util/h2mdiff/
│   │   ├── App.java                 # Main entry point
│   │   ├── commands/
│   │   │   └── FetchCommand.java    # Fetch command implementation
│   │   └── lib/
│   │       └── HttpFetcher.java     # HTTP fetching utility
│   └── test/java/com/jr/util/h2mdiff/
│       ├── AppTest.java
│       └── commands/
│           └── FetchCommandTest.java
├── pom.xml                          # Maven configuration
├── h2mdiff                          # Shell wrapper (Linux/Mac)
├── h2mdiff.bat                      # Batch wrapper (Windows)
└── README.md                        # This file
```

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write tests for new functionality
5. Submit a pull request

## License

[License information here]

## Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Check existing documentation
- Run `h2mdiff --help` for command-line help

---

**Current Version:** 0.1.0 (Alpha - HTML Fetching Phase)

**Last Updated:** March 2024
