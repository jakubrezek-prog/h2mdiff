## ADDED Requirements

### Requirement: Generate diff between Markdown files
The system SHALL produce a clear comparison between two Markdown versions.

#### Scenario: Compare two Markdown files
- **WHEN** user runs `h2mdiff diff <previous.md> <current.md>`
- **THEN** system outputs unified diff showing additions, deletions, and context

#### Scenario: Handle identical files
- **WHEN** previous and current Markdown files are identical
- **THEN** system reports "No differences found"

#### Scenario: Detect all changes
- **WHEN** user compares Markdown files with multiple additions, deletions, and modifications
- **THEN** output shows all changes with clear indicators (-, +, context lines)

### Requirement: Output diff in standard format
The system SHALL use unified diff format compatible with standard tools.

#### Scenario: Unified diff format
- **WHEN** diff is generated
- **THEN** output follows unified diff format with @@ line markers and +/- prefixes

#### Scenario: Save diff to file
- **WHEN** user provides `--output diff.md` flag
- **THEN** system writes diff output to specified file

#### Scenario: Display diff to console
- **WHEN** user does not specify output file
- **THEN** system prints diff to stdout for review

### Requirement: Provide context around changes
The system SHALL include surrounding lines to help understand impact.

#### Scenario: Include context lines
- **WHEN** diff is generated
- **THEN** output includes unchanged lines before and after each change for context

#### Scenario: Configurable context size
- **WHEN** user provides `--context N` flag (where N is number of lines)
- **THEN** diff includes N lines of context on each side of changes

#### Scenario: Default context
- **WHEN** user does not specify context
- **THEN** system uses 3 lines of context by default

### Requirement: Highlight significant content changes
The system SHALL help identify meaningful modifications in reports.

#### Scenario: Detect new security issues
- **WHEN** markdown contains security report data and new issues are added
- **THEN** diff clearly shows new lines with + prefix

#### Scenario: Detect resolved issues
- **WHEN** markdown contains security report data and issues are removed
- **THEN** diff clearly shows deleted lines with - prefix

#### Scenario: Detect severity changes
- **WHEN** issue severity or status changes (e.g., CRITICAL to HIGH)
- **THEN** diff highlights the modified line with context

### Requirement: Normalize comparison
The system SHALL reduce diff noise from formatting variations.

#### Scenario: Ignore trailing whitespace
- **WHEN** only difference between files is trailing spaces
- **THEN** system reports no significant differences (optional flag for strict mode)

#### Scenario: Handle blank line variations
- **WHEN** files differ only in number of blank lines between sections
- **THEN** diff minimizes noise while preserving structural changes

### Requirement: Provide summary information
The system SHALL include overview of changes for quick assessment.

#### Scenario: Show change summary
- **WHEN** diff completes
- **THEN** output includes count of added lines, deleted lines, and modified sections

#### Scenario: Highlight key sections
- **WHEN** changes occur in multiple sections
- **THEN** diff clearly delineates sections (e.g., from Markdown headers) for easy navigation
