## ADDED Requirements

### Requirement: Read configuration from file
The system SHALL load configuration defaults from a YAML-formatted file in the user's project or home directory.

#### Scenario: Load config from working directory
- **WHEN** user runs h2mdiff command in a directory containing `.h2mdiff.yml`
- **THEN** system reads configuration values from that file for defaults

#### Scenario: Load config from home directory
- **WHEN** `.h2mdiff.yml` is not found in working directory
- **THEN** system searches home directory (`~/.h2mdiff.yml`) for configuration

#### Scenario: Use built-in defaults when no config found
- **WHEN** no configuration file exists in working or home directory
- **THEN** system uses hard-coded built-in defaults for all settings

### Requirement: Support configurable settings
The system SHALL allow users to set persistent defaults via configuration file.

#### Scenario: Configure output directory
- **WHEN** configuration file contains `output_dir: ./reports/`
- **THEN** system uses this directory as default for all output files

#### Scenario: Configure base URL
- **WHEN** configuration file contains `base_url: https://harbor.example.com`
- **THEN** system prepends this URL to relative paths in fetch commands

#### Scenario: Configure diff context
- **WHEN** configuration file contains `diff_context: 5`
- **THEN** system uses 5 lines of context for diffs by default (can be overridden by CLI flag)

### Requirement: CLI flags override configuration file
The system SHALL prioritize command-line arguments over configuration file settings.

#### Scenario: CLI flag overrides config file
- **WHEN** `.h2mdiff.yml` specifies `output_dir: ./default/` AND user runs `h2mdiff fetch <url> --output ./custom/file.html`
- **THEN** system uses `./custom/file.html` instead of `./default/`

#### Scenario: Priority order applied
- **WHEN** configuration and CLI flags both provide a value
- **THEN** system applies priority: CLI flags > config file > built-in defaults

### Requirement: Config file validation
The system SHALL validate configuration file syntax and values.

#### Scenario: Invalid YAML syntax
- **WHEN** configuration file contains invalid YAML
- **THEN** system displays error message and uses built-in defaults instead

#### Scenario: Unknown configuration options
- **WHEN** configuration file contains unknown keys
- **THEN** system warns user about unrecognized options but continues with known settings

#### Scenario: Valid config accepted
- **WHEN** configuration file contains valid YAML with recognized keys
- **THEN** system loads and applies all settings successfully
