## ADDED Requirements

### Requirement: Fetch HTML from URL
The system SHALL retrieve HTML content from a given URL and store it locally.

#### Scenario: Successful URL fetch
- **WHEN** user runs `h2mdiff fetch <url>`
- **THEN** system downloads the HTML content and saves to default output file or specified path

#### Scenario: Fetch from local file
- **WHEN** user specifies a local file path starting with `/` or `./`
- **THEN** system reads the file directly without attempting HTTP request

#### Scenario: Error on invalid URL
- **WHEN** user provides malformed URL or unreachable host
- **THEN** system displays clear error message with suggestion to verify URL

### Requirement: Support command-line parameters
The system SHALL accept flexible command-line arguments for fetch operations.

#### Scenario: Output file specification
- **WHEN** user provides `--output filename.html` flag
- **THEN** system saves fetched content to specified filename

#### Scenario: Default output naming
- **WHEN** user does not provide output flag
- **THEN** system generates filename from URL (e.g., `github-com-page.html`) in current directory

### Requirement: Handle common HTML sources
The system SHALL properly handle HTML content from typical web pages and reports.

#### Scenario: Fetch web page
- **WHEN** user provides standard HTTPS URL (e.g., https://example.com)
- **THEN** system retrieves complete HTML including all elements

#### Scenario: Fetch security report
- **WHEN** user provides URL to security report (e.g., Harbor Trivy CVE report)
- **THEN** system retrieves HTML with all table data, links, and formatting intact

### Requirement: Preserve HTTP metadata
The system SHALL optionally capture headers and metadata for audit purposes.

#### Scenario: Capture fetch metadata
- **WHEN** user runs fetch with `--metadata` flag
- **THEN** system saves HTTP headers, fetch timestamp, and URL to separate file alongside HTML

#### Scenario: No metadata by default
- **WHEN** user runs fetch without flags
- **THEN** system only saves HTML content, no extra metadata files created
