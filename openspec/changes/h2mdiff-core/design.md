## Context

The h2mdiff project needs a well-architected pure Java CLI tool that can fetch HTML pages, convert them to markdown, and produce meaningful diffs. This is particularly useful for tracking security reports (like Harbor Trivy CVE reports) where changes need to be clearly visible and archived. The tool is built entirely in Java to avoid external runtime dependencies.

## Goals / Non-Goals

**Goals:**
- Establish a modular architecture that separates concerns (fetch, convert, diff)
- Pure Java implementation with no external runtime dependencies (only JVM required)
- Support both URL and local file inputs
- Produce clean, normalized markdown output
- Generate human-readable diffs between markdown versions
- Enable batch processing for multiple URLs
- Create a solid foundation for future extensions

**Non-Goals:**
- Advanced markdown processing features (beyond HTML-to-MD conversion)
- UI/Web interface (CLI only)
- Real-time monitoring or scheduling
- Complex authentication schemes (covered in later phases)
- Node.js, Python, or other language dependencies

## Decisions

**1. Modular Architecture with Three Command Layers**
- **Rationale**: Separating fetch, convert, and diff into distinct modules allows independent testing and potential standalone use of each phase. Users may want to fetch once and diff multiple times.
- **Implementation**: Three main command handlers that can compose together or work independently
- **Alternative**: Single monolithic command - rejected because less reusable and harder to test

**2. HTML-to-Markdown Conversion: flexmark-java**
- **Rationale**: Use flexmark-java, a pure Java markdown parser and generator, for reliable HTML-to-Markdown conversion without external dependencies
- **Implementation**: Integrate flexmark HTML parsing and conversion modules; leverage its visitor pattern for customization
- **Advantages**: No Node.js dependency, comprehensive HTML support, active maintenance, strong markdown spec compliance
- **Alternative**: Build custom parser - rejected due to complexity and maintenance overhead

**3. CLI Interface Design**
- **Rationale**: Simple, composable commands that follow Unix philosophy (each tool does one thing well)
- **Fetch**: `h2mdiff fetch <url> [--output file.html]`
- **Convert**: `h2mdiff convert <input.html> [--output file.md]`
- **Diff**: `h2mdiff diff <previous.md> <current.md> [--output diff.md]`
- **Composite**: `h2mdiff fetch-convert <url> [--output file.md]`
- **Alternative**: Single command with flags - rejected as less flexible

**4. Data Storage Strategy**
- **Rationale**: Store both HTML (raw source) and markdown (normalized) versions for audit trail and reproducibility
- **Implementation**: Simple file-based storage in user-specified directory
- **Alternative**: Database - rejected as over-engineered for Phase 1

**5. Diff Format and Library**
- **Rationale**: Use unified diff format (similar to git diff) for consistency and tooling compatibility
- **Implementation**: Use java-diff-utils library for Java diff generation in unified diff format
- **Alternative**: Custom diff format - rejected for compatibility and learning curve

**6. Build System and Packaging**
- **Rationale**: Use Maven for dependency management, compilation, and packaging; create fat JAR (all dependencies included)
- **Implementation**: Standard Maven project structure with pom.xml; maven-shade-plugin for creating executable JAR
- **Alternative**: Gradle - also viable but Maven chosen for broader ecosystem support
- **Deliverable**: Single h2mdiff.jar executable that includes all dependencies (no external libs needed at runtime)

**7. Configuration File Approach**
- **Rationale**: YAML-based configuration file (`.h2mdiff.yml` or `.h2mdiffrc`) provides persistent defaults without CLI clutter
- **Implementation**: Use SnakeYAML or similar for YAML parsing; load config from working directory or user home directory
- **Priority order**: CLI flags > config file > built-in defaults
- **Configurable items**: output directory, base URL for relative paths, default context lines for diffs
- **Alternative**: Environment variables only - rejected as less discoverable and harder to manage

**8. Wrapper Script Strategy**
- **Rationale**: Users should run `h2mdiff` directly instead of `java -jar h2mdiff.jar` for better UX
- **Implementation**: Create shell script (Linux/Mac) and batch file (Windows) that locate and invoke the JAR
- **Installation**: Scripts installed to system PATH (e.g., `/usr/local/bin/h2mdiff`)
- **Approach**: Standalone installation script, not npm-based (since this is pure Java, not a Node package)
- **Alternative**: Manual setup - rejected as poor user experience

**9. README Updates Per Phase**
- **Rationale**: Keep documentation synchronized with implementation progress
- **Implementation**: Update README.md after completing each capability phase with usage examples
- **Contents**: Add command examples, configuration details, and new feature descriptions
- **Alternative**: Single final documentation pass - rejected as documentation would lag behind features

## Risks / Trade-offs

**[Risk] HTML structure preservation in conversion**
→ **Mitigation**: Use comprehensive flexmark-java library with extensive test coverage with real reports. Validate output against actual Harbor Trivy reports early.

**[Risk] Performance with large HTML files**
→ **Mitigation**: Java's efficient handling of large files; initially optimize for correctness. Stream processing if needed later. Set reasonable file size limits in documentation.

**[Risk] Diff noise from whitespace/formatting changes**
→ **Mitigation**: Normalize markdown before diffing (strip extra whitespace, consistent indentation) to reduce false positives.

**[Trade-off] Library dependency vs. custom code**
→ Taking on external dependencies (flexmark-java, java-diff-utils, SnakeYAML) trades maintenance burden for reliability. All are proven, actively maintained Java libraries. Prefer proven libraries for Phase 1.

**[Risk] Configuration file discoverability**
→ **Mitigation**: Document well in README, provide example config file, support multiple search paths (.h2mdiff.yml in working dir and home dir).

**[Risk] Wrapper script portability**
→ **Mitigation**: Provide both shell and batch script versions, test on Linux/Mac/Windows, document installation process clearly.

**[Trade-off] Pure Java vs. language flexibility**
→ Committing to pure Java simplifies deployment (only requires JVM) but restricts future contributions to Java ecosystem. Benefit: single unified codebase, no polyglot maintenance burden.

## Migration Plan

This is a new tool, so no migration needed. Initial release will be straightforward installation and basic usage documentation.

## Technology Stack

**Java Version:** Java 21
- Target release: Java 21
- Leverage modern Java features (records, text blocks, pattern matching, virtual threads if applicable)
- Maven compiler target: 21

**Key Dependencies:**
- flexmark-java: HTML → Markdown conversion
- java-diff-utils: Unified diff generation
- SnakeYAML: YAML configuration parsing
- Apache Commons CLI: Command-line argument parsing
- JUnit 5: Testing framework

## Open Questions

**For Phase 1 (keeping it simple):**
- None - proceeding with straightforward implementation

**For Future Phases (tracked for later consideration):**
- Environment variable support for configuration (should env vars override config file?)
- Streaming support for very large files (what size threshold?)
- Docker image as deployment alternative (containerized environments)
- GitHub Actions integration for automated releases
- Support for authenticated/private URLs (basic auth, API keys)
