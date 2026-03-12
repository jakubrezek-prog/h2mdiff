## ADDED Requirements

### Requirement: Wrapper script for easy invocation
The system SHALL provide wrapper scripts so users can run `h2mdiff` command directly without typing full Java command.

#### Scenario: Run on Linux/Mac with shell script
- **WHEN** user runs `h2mdiff fetch <url>` on Linux or Mac
- **THEN** shell wrapper script (`h2mdiff`) locates the JAR and executes it with arguments

#### Scenario: Run on Windows with batch script
- **WHEN** user runs `h2mdiff.bat fetch <url>` or `h2mdiff fetch <url>` on Windows
- **THEN** batch wrapper script locates the JAR and executes it with arguments

#### Scenario: Script in system PATH
- **WHEN** wrapper scripts are installed to system PATH (e.g., `/usr/local/bin/` or `C:\Program Files\h2mdiff\`)
- **THEN** user can run `h2mdiff` from any directory without specifying full path

### Requirement: Script locates JAR executable
The system SHALL automatically find the h2mdiff JAR file location.

#### Scenario: JAR in same directory as script
- **WHEN** wrapper script and JAR are in the same directory
- **THEN** script executes JAR directly

#### Scenario: JAR in standard installation path
- **WHEN** JAR is installed to standard location (e.g., `/opt/h2mdiff/` or `C:\Program Files\h2mdiff\`)
- **THEN** script looks for JAR in known installation paths

#### Scenario: JAR via npm bin
- **WHEN** h2mdiff is installed as npm package
- **THEN** npm wrapper automatically finds and executes the JAR

### Requirement: Script passes arguments correctly
The system SHALL forward all command-line arguments to the Java application.

#### Scenario: Pass arguments without modification
- **WHEN** user runs `h2mdiff fetch https://example.com --output file.html`
- **THEN** wrapper script passes all arguments to JAR exactly as provided

#### Scenario: Handle special characters in arguments
- **WHEN** user provides arguments with spaces, quotes, or special characters
- **THEN** wrapper script correctly escapes and passes arguments to Java

#### Scenario: Pass through all flags and options
- **WHEN** user runs `h2mdiff convert input.html --output output.md --verbose`
- **THEN** wrapper script preserves all flags and options in correct order

### Requirement: Graceful error handling
The system SHALL provide helpful error messages if wrapper script fails.

#### Scenario: JAR not found
- **WHEN** wrapper script cannot locate h2mdiff JAR file
- **THEN** display clear error message with instructions for installation

#### Scenario: Java not found
- **WHEN** Java runtime is not installed or not in PATH
- **THEN** display error message suggesting Java installation
