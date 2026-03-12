## 1. Project Setup and Infrastructure

- [ ] 1.1 Initialize Node.js/TypeScript project with package.json and tsconfig.json
- [ ] 1.2 Set up build system (esbuild or tsc) for compilation
- [ ] 1.3 Create directory structure for src/commands, src/lib, src/utils
- [ ] 1.4 Set up Git repository and initial commit
- [ ] 1.5 Create basic CLI entry point (bin/h2mdiff or similar)

## 2. HTML Fetching Capability

- [ ] 2.1 Evaluate and select HTML-to-Markdown library (turndown.js recommended)
- [ ] 2.2 Implement HTTP fetch utility with error handling and timeout support
- [ ] 2.3 Implement local file read capability (detect relative/absolute paths)
- [ ] 2.4 Create fetch command handler accepting URL or file path argument
- [ ] 2.5 Add --output flag support with default naming from URL
- [ ] 2.6 Add --metadata flag to capture HTTP headers and timestamp
- [ ] 2.7 Add error handling for invalid URLs, unreachable hosts, network timeouts
- [ ] 2.8 Write unit tests for fetch command covering URLs, local files, and error cases

## 3. HTML-to-Markdown Conversion Capability

- [ ] 3.1 Integrate HTML-to-Markdown library into conversion module
- [ ] 3.2 Implement basic HTML parsing and conversion to Markdown
- [ ] 3.3 Add table conversion with proper Markdown table syntax
- [ ] 3.4 Implement link and image preservation in Markdown format
- [ ] 3.5 Create whitespace normalization (strip excess spaces, consistent line breaks)
- [ ] 3.6 Implement code block formatting with triple-backticks
- [ ] 3.7 Create convert command handler accepting input HTML file
- [ ] 3.8 Add --output flag support with default output naming
- [ ] 3.9 Implement Markdown validation to ensure valid output syntax
- [ ] 3.10 Add styling/class removal to ensure clean semantic Markdown
- [ ] 3.11 Write comprehensive unit tests for conversion with various HTML inputs
- [ ] 3.12 Test conversion against actual Harbor Trivy CVE report HTML

## 4. Markdown Diffing Capability

- [ ] 4.1 Evaluate and select diff library (diff-match-patch recommended)
- [ ] 4.2 Implement diff generation between two Markdown files
- [ ] 4.3 Implement unified diff format output with +/- prefixes and context
- [ ] 4.4 Add --context flag to control number of context lines (default 3)
- [ ] 4.5 Add --output flag for saving diff to file or stdout
- [ ] 4.6 Implement "no differences" detection and messaging
- [ ] 4.7 Create change summary (count added/deleted lines, sections changed)
- [ ] 4.8 Implement optional whitespace normalization for diff (--strict flag)
- [ ] 4.9 Add section detection and highlighting from Markdown headers
- [ ] 4.10 Write unit tests for diff generation with various file combinations
- [ ] 4.11 Test diff readability and formatting with security report data

## 5. CLI Integration and Composition

- [ ] 5.1 Create main CLI router accepting fetch, convert, diff commands
- [ ] 5.2 Implement fetch-convert composite command (fetch URL and convert to MD in one step)
- [ ] 5.3 Add fetch-convert-diff command (fetch, convert, then diff with previous version)
- [ ] 5.4 Implement help system and usage documentation
- [ ] 5.5 Add version flag and display version information
- [ ] 5.6 Create global error handling and logging

## 6. Configuration File Support

- [ ] 6.1 Create configuration module to handle YAML file parsing
- [ ] 6.2 Implement config file search (.h2mdiff.yml in current directory, then home directory)
- [ ] 6.3 Add configuration schema with validation for all supported options
- [ ] 6.4 Implement priority ordering: CLI flags > config file > built-in defaults
- [ ] 6.5 Support configurable defaults: output_dir, base_url, diff_context, etc.
- [ ] 6.6 Add --show-config command to display active configuration (merged from all sources)
- [ ] 6.7 Create example configuration file (.h2mdiff.yml.example)
- [ ] 6.8 Add error handling for invalid YAML and unknown config options
- [ ] 6.9 Write unit tests for configuration loading and merging logic

## 7. Wrapper Script Creation

- [ ] 7.1 Create shell wrapper script (h2mdiff) for Linux/Mac with JAR detection
- [ ] 7.2 Create batch wrapper script (h2mdiff.bat) for Windows with JAR detection
- [ ] 7.3 Implement JAR location detection in multiple standard paths
- [ ] 7.4 Add argument forwarding with proper escaping for special characters
- [ ] 7.5 Add error handling for missing Java runtime
- [ ] 7.6 Add error handling for missing JAR file with helpful instructions
- [ ] 7.7 Test wrapper scripts on Linux, Mac, and Windows
- [ ] 7.8 Document wrapper installation and setup (PATH configuration)

## 8. CLI Integration and Composition

- [ ] 8.1 Create main CLI router accepting fetch, convert, diff commands
- [ ] 8.2 Implement fetch-convert composite command (fetch URL and convert to MD in one step)
- [ ] 8.3 Add fetch-convert-diff command (fetch, convert, then diff with previous version)
- [ ] 8.4 Implement help system and usage documentation
- [ ] 8.5 Add version flag and display version information
- [ ] 8.6 Create global error handling and logging
- [ ] 8.7 Integrate configuration system into all commands

## 9. CLI Argument Parsing and Configuration Integration

- [ ] 9.1 Implement comprehensive CLI argument parsing for all commands and flags
- [ ] 9.2 Add application configuration system that loads config files automatically
- [ ] 9.3 Create user-friendly error messages with suggestions
- [ ] 9.4 Add progress indicators for long-running operations
- [ ] 9.5 Implement verbose/debug logging with --verbose flag

## 10. Testing and Quality Assurance

- [ ] 10.1 Set up test framework (Jest or similar)
- [ ] 10.2 Create test fixtures with sample HTML files
- [ ] 10.3 Add integration tests for complete workflows (fetch → convert → diff)
- [ ] 10.4 Test with real Harbor Trivy CVE reports
- [ ] 10.5 Add performance tests for large HTML files
- [ ] 10.6 Ensure all error paths are tested
- [ ] 10.7 Set up CI/CD pipeline for automated testing
- [ ] 10.8 Test wrapper scripts on all supported platforms

## 11. README Updates by Phase

- [ ] 11.1 After Phase 1 (HTML Fetching): Update README with fetch command examples and basic config
- [ ] 11.2 After Phase 2 (HTML-to-Markdown): Add convert command examples and usage guide
- [ ] 11.3 After Phase 3 (Markdown Diffing): Add diff command examples and report comparison use cases
- [ ] 11.4 After Phase 4 (Configuration & Wrapper): Add configuration file documentation and installation instructions
- [ ] 11.5 Final README: Include complete feature list, Harbor Trivy integration guide, and troubleshooting

## 12. Documentation and Release

- [ ] 12.1 Write comprehensive README with all command usage examples
- [ ] 12.2 Create man page or detailed help documentation
- [ ] 12.3 Write quick start guide for Harbor Trivy integration
- [ ] 12.4 Document all configuration options with examples
- [ ] 12.5 Document wrapper script installation for different platforms
- [ ] 12.6 Create CHANGELOG file
- [ ] 12.7 Package for npm distribution (or alternative package manager)
- [ ] 12.8 Create installation instructions for all platforms
- [ ] 12.9 Tag initial release and publish

## 13. Batch Processing and Extended Features

- [ ] 13.1 Implement batch mode to process multiple URLs from a file
- [ ] 13.2 Add output directory support for organizing multiple fetches
- [ ] 13.3 Create index/archive functionality to track multiple versions
- [ ] 13.4 Add option to automatically diff consecutive versions
