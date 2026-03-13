## 1. Project Setup and Infrastructure

- [x] 1.1 Initialize Maven project with pom.xml and standard Java 21 directory structure
- [x] 1.2 Configure pom.xml with Maven compiler settings (source and target: 21)
- [x] 1.3 Add Maven dependencies: flexmark-java, java-diff-utils, SnakeYAML, commons-cli
- [x] 1.4 Set up maven-shade-plugin to create fat JAR with all dependencies included
- [x] 1.5 Create src/main/java directory structure: com.h2mdiff.commands, com.h2mdiff.lib, com.h2mdiff.utils
- [x] 1.6 Create main entry point class with command dispatcher
- [x] 1.7 Configure Maven to generate h2mdiff.jar as output artifact
- [x] 1.8 Leverage Java 21 features: records for data classes, text blocks for multi-line strings where applicable

## 2. HTML Fetching Capability

- [x] 2.1 Create HttpFetcher class using Java's HttpClient (or OkHttp if needed for advanced features)
- [x] 2.2 Implement URL validation and parsing with error handling
- [x] 2.3 Implement local file detection and reading capability
- [x] 2.4 Add timeout and connection error handling
- [x] 2.5 Create FetchCommand class with CLI argument handling
- [x] 2.6 Add --output flag support with default naming from URL
- [x] 2.7 Add --metadata flag to capture HTTP headers and fetch timestamp
- [x] 2.8 Implement error messages for invalid URLs, unreachable hosts, network timeouts
- [x] 2.9 Write unit tests for fetch command covering URLs, local files, and error cases

## 3. HTML-to-Markdown Conversion Capability

- [ ] 3.1 Add flexmark-java dependencies to pom.xml
- [ ] 3.2 Create HtmlToMarkdownConverter class using flexmark visitor pattern
- [ ] 3.3 Implement basic HTML parsing and conversion to Markdown
- [ ] 3.4 Add table conversion with proper Markdown table syntax using flexmark
- [ ] 3.5 Implement link and image preservation in Markdown format
- [ ] 3.6 Create whitespace normalization utilities (strip excess spaces, consistent line breaks)
- [ ] 3.7 Implement code block formatting with triple-backticks
- [ ] 3.8 Create ConvertCommand class accepting input HTML file
- [ ] 3.9 Add --output flag support with default output naming
- [ ] 3.10 Implement Markdown validation to ensure valid output syntax
- [ ] 3.11 Add styling/class removal to ensure clean semantic Markdown
- [ ] 3.12 Write comprehensive unit tests for conversion with various HTML inputs (JUnit 5)
- [ ] 3.13 Test conversion against actual Harbor Trivy CVE report HTML

## 4. Markdown Diffing Capability

- [ ] 4.1 Add java-diff-utils dependency to pom.xml
- [ ] 4.2 Create MarkdownDiffer class using java-diff-utils library
- [ ] 4.3 Implement unified diff format generation with +/- prefixes and context
- [ ] 4.4 Add --context flag to control number of context lines (default 3)
- [ ] 4.5 Create DiffCommand class with CLI argument handling
- [ ] 4.6 Add --output flag for saving diff to file or stdout
- [ ] 4.7 Implement "no differences" detection and messaging
- [ ] 4.8 Create change summary generator (count added/deleted lines, sections changed)
- [ ] 4.9 Implement optional whitespace normalization for diff
- [ ] 4.10 Add section detection and highlighting from Markdown headers
- [ ] 4.11 Write unit tests for diff generation with various file combinations
- [ ] 4.12 Test diff readability and formatting with security report data

## 5. Configuration File Support

- [ ] 5.1 Add SnakeYAML dependency to pom.xml
- [ ] 5.2 Create Configuration class to parse YAML config files
- [ ] 5.3 Implement config file search (.h2mdiff.yml in current directory, then home directory)
- [ ] 5.4 Add configuration schema with validation for all supported options
- [ ] 5.5 Implement priority ordering: CLI flags > config file > built-in defaults
- [ ] 5.6 Support configurable defaults: output_dir, base_url, diff_context, etc.
- [ ] 5.7 Create ConfigManager class to merge configurations from all sources
- [ ] 5.8 Add --show-config command to display active configuration (merged from all sources)
- [ ] 5.9 Create example configuration file (.h2mdiff.yml.example)
- [ ] 5.10 Add error handling for invalid YAML and unknown config options
- [ ] 5.11 Write unit tests for configuration loading and merging logic

## 6. Wrapper Script Creation

- [ ] 6.1 Create shell wrapper script (h2mdiff) for Linux/Mac with JAR detection
- [ ] 6.2 Create batch wrapper script (h2mdiff.bat) for Windows with JAR detection
- [ ] 6.3 Implement JAR location detection in multiple standard paths
- [ ] 6.4 Add argument forwarding with proper escaping for special characters
- [ ] 6.5 Add error handling for missing Java runtime with helpful instructions
- [ ] 6.6 Add error handling for missing JAR file with helpful installation instructions
- [ ] 6.7 Test wrapper scripts on Linux, Mac, and Windows
- [ ] 6.8 Document wrapper installation and setup (PATH configuration)

## 7. CLI Integration and Composition

- [ ] 7.1 Create CommandRouter class to dispatch to appropriate command handler
- [ ] 7.2 Implement fetch-convert composite command (fetch URL and convert to MD in one step)
- [ ] 7.3 Add fetch-convert-diff command (fetch, convert, then diff with previous version)
- [ ] 7.4 Implement help system and usage documentation (--help, -h flags)
- [ ] 7.5 Add version flag and display version information (--version)
- [ ] 7.6 Create global error handling and logging framework
- [ ] 7.7 Integrate configuration system into all commands

## 8. CLI Argument Parsing

- [ ] 8.1 Use Apache Commons CLI or similar for comprehensive argument parsing
- [ ] 8.2 Implement argument validation for all commands and flags
- [ ] 8.3 Create user-friendly error messages with suggestions
- [ ] 8.4 Add progress indicators for long-running operations (HTTP fetches)
- [ ] 8.5 Implement verbose/debug logging with --verbose flag

## 9. Testing and Quality Assurance

- [ ] 9.1 Set up JUnit 5 test framework in pom.xml
- [ ] 9.2 Create test fixtures with sample HTML files in src/test/resources
- [ ] 9.3 Add integration tests for complete workflows (fetch → convert → diff)
- [ ] 9.4 Test with real Harbor Trivy CVE reports
- [ ] 9.5 Add performance tests for large HTML files
- [ ] 9.6 Ensure all error paths are tested (network errors, file not found, etc.)
- [ ] 9.7 Set up CI/CD pipeline (GitHub Actions, GitLab CI, or Jenkins)
- [ ] 9.8 Test wrapper scripts on all supported platforms
- [ ] 9.9 Add code coverage requirements (aim for 80%+)

## 10. README Updates by Phase

- [ ] 10.1 After Phase 1 (HTML Fetching): Update README with fetch command examples and basic config
- [ ] 10.2 After Phase 2 (HTML-to-Markdown): Add convert command examples and usage guide
- [ ] 10.3 After Phase 3 (Markdown Diffing): Add diff command examples and report comparison use cases
- [ ] 10.4 After Phase 4 (Configuration & Wrapper): Add configuration file documentation and installation instructions
- [ ] 10.5 Final README: Include complete feature list, Harbor Trivy integration guide, and troubleshooting

## 11. Documentation and Release

- [ ] 11.1 Write comprehensive README with all command usage examples
- [ ] 11.2 Create man page or detailed help documentation
- [ ] 11.3 Write quick start guide for Harbor Trivy integration
- [ ] 11.4 Document all configuration options with examples
- [ ] 11.5 Document wrapper script installation for different platforms
- [ ] 11.6 Create CHANGELOG file documenting all features
- [ ] 11.7 Build and package fat JAR for distribution
- [ ] 11.8 Create installation instructions for all platforms (manual download, package managers)
- [ ] 11.9 Tag initial release in Git and create release notes
- [ ] 11.10 Publish release artifacts (JAR, wrapper scripts)

## 12. Batch Processing and Extended Features

- [ ] 12.1 Implement batch mode to process multiple URLs from a file
- [ ] 12.2 Add output directory support for organizing multiple fetches
- [ ] 12.3 Create index/archive functionality to track multiple versions
- [ ] 12.4 Add option to automatically diff consecutive versions
