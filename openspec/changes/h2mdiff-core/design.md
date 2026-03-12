## Context

The h2mdiff project needs a well-architected CLI tool that can fetch HTML pages, convert them to markdown, and produce meaningful diffs. This is particularly useful for tracking security reports (like Harbor Trivy CVE reports) where changes need to be clearly visible and archived. The tool needs to handle multiple phases: fetching, conversion, and diffing.

## Goals / Non-Goals

**Goals:**
- Establish a modular architecture that separates concerns (fetch, convert, diff)
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

## Decisions

**1. Modular Architecture with Three Command Layers**
- **Rationale**: Separating fetch, convert, and diff into distinct modules allows independent testing and potential standalone use of each phase. Users may want to fetch once and diff multiple times.
- **Implementation**: Three main command handlers that can compose together or work independently
- **Alternative**: Single monolithic command - rejected because less reusable and harder to test

**2. HTML-to-Markdown Conversion Library**
- **Rationale**: Using an established library (e.g., turndown.js or html2md) rather than custom parsing reduces bugs and maintenance burden
- **Implementation**: Evaluate turndown.js for Node.js/TypeScript compatibility and feature set
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

**5. Diff Format**
- **Rationale**: Use unified diff format (similar to git diff) for consistency and tooling compatibility
- **Implementation**: Library-based diff generation (e.g., diff-match-patch)
- **Alternative**: Custom diff format - rejected for compatibility and learning curve

**6. Configuration File Approach**
- **Rationale**: YAML-based configuration file (`.h2mdiff.yml` or `.h2mdiffrc`) provides persistent defaults without CLI clutter
- **Implementation**: Load config from working directory or user home directory; CLI flags override config file settings
- **Priority order**: CLI flags > config file > built-in defaults
- **Configurable items**: output directory, base URL for relative paths, default context lines for diffs
- **Alternative**: Environment variables only - rejected as less discoverable and harder to manage

**7. Wrapper Script Strategy**
- **Rationale**: Users should run `h2mdiff` directly instead of `java -jar h2mdiff.jar` for better UX
- **Implementation**: Create shell script (Linux/Mac) and batch file (Windows) that locate and invoke the JAR
- **Installation**: Scripts installed to system PATH (e.g., `/usr/local/bin/h2mdiff` or via npm bin)
- **Approach**: Use npm package wrapper or standalone installation script
- **Alternative**: Manual setup - rejected as poor user experience

**8. README Updates Per Phase**
- **Rationale**: Keep documentation synchronized with implementation progress
- **Implementation**: Update README.md after completing each capability phase with usage examples
- **Contents**: Add command examples, configuration details, and new feature descriptions
- **Alternative**: Single final documentation pass - rejected as documentation would lag behind features

## Risks / Trade-offs

**[Risk] HTML structure preservation in conversion**
→ **Mitigation**: Use comprehensive HTML-to-Markdown library and extensive test coverage with real reports. Validate output against actual Harbor Trivy reports early.

**[Risk] Performance with large HTML files**
→ **Mitigation**: Stream processing if needed later; initially optimize for correctness. Set reasonable file size limits in documentation.

**[Risk] Diff noise from whitespace/formatting changes**
→ **Mitigation**: Normalize markdown before diffing (strip extra whitespace, consistent indentation) to reduce false positives.

**[Trade-off] Library dependency vs. custom code**
→ Taking on external dependencies (turndown.js, diff-match-patch) trades maintenance burden for reliability. Prefer proven libraries for Phase 1.

**[Risk] Configuration file discoverability**
→ **Mitigation**: Document well in README, provide example config file, support multiple search paths (.h2mdiff.yml in working dir and home dir).

**[Risk] Wrapper script portability**
→ **Mitigation**: Provide both shell and batch script versions, test on Linux/Mac/Windows, consider npm package for easier distribution.

## Migration Plan

This is a new tool, so no migration needed. Initial release will be straightforward installation and basic usage documentation.

## Open Questions

- Which HTML-to-Markdown library should we use? Need to evaluate turndown.js vs. alternatives for Node.js
- Should configuration also support environment variable overrides?
- Should we support streaming for very large files, or start simple?
- Should wrapper script be distributed via npm, direct installation, or both?
