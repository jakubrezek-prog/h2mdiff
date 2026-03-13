## Why

The need to track and compare generated reports (such as security CVE reports from tools like Harbor Trivy) requires a reliable way to fetch, normalize, and diff HTML content. Currently, there's no unified CLI tool to convert HTML pages to markdown and produce meaningful diffs, requiring users to manually manage multiple formats and comparison workflows.

## What Changes

- Add CLI capability to fetch HTML pages from URLs or local files
- Implement HTML-to-Markdown conversion pipeline with configurable options
- Create diff generation between markdown versions to identify changes
- Support batch processing of multiple URLs for tracking changes over time
- Generate human-readable diff output suitable for reports and archives
- Add configuration file support for persistent defaults (output directory, base URL, etc.)
- Provide wrapper script so users can run `h2mdiff` instead of `java -jar h2mdiff.jar`
- Update README.md after each phase to document new capabilities

## Capabilities

### New Capabilities
- `html-fetching`: Fetch HTML content from command line parameters (URLs and local files)
- `html-to-markdown`: Convert fetched HTML to markdown format with proper structure preservation
- `markdown-diffing`: Produce diffs between consecutive markdown versions to identify changes
- `configuration`: Read and apply configuration defaults from config file (.h2mdiff.yml, .h2mdiffrc, etc.)
- `wrapper-script`: Provide convenient shell/batch wrapper script for easy CLI invocation

### Modified Capabilities
<!-- No existing capabilities modified in this phase -->

## Impact

- Creates new CLI entry point for h2mdiff command
- Introduces dependencies on HTML parsing and markdown conversion libraries
- Establishes data flow pattern: HTML → Markdown → Diff
- Affects future integrations with report tracking systems
