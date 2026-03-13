package com.jr.util.h2mdiff;

import com.jr.util.h2mdiff.commands.FetchCommand;
import java.io.IOException;

/**
 * Main entry point for H2MDiff CLI application.
 * 
 * H2MDiff is a command-line utility for fetching HTML pages, converting them to Markdown,
 * and generating diffs between Markdown versions. It's designed to track and compare
 * generated reports such as security CVE reports.
 * 
 * Supported commands:
 * <ul>
 *   <li>fetch - Retrieve HTML from URL or local file</li>
 *   <li>convert - Convert HTML to Markdown format</li>
 *   <li>diff - Generate diff between Markdown files</li>
 * </ul>
 */
public class App {

    /**
     * Main entry point for the H2MDiff application.
     *
     * @param args command-line arguments: &lt;command&gt; [options]
     *             Supported commands: fetch, convert, diff
     *             Use --help or -h to display usage information
     */
    public static void main(String[] args) {
        System.out.println("H2MDiff v0.1.0");
        System.out.println("HTML to Markdown Diff Utility");
        
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        String command = args[0];
        String[] commandArgs = java.util.Arrays.copyOfRange(args, 1, args.length);
        
        try {
            switch (command) {
                case "fetch" -> new FetchCommand(commandArgs).execute();
                case "convert" -> System.out.println("Convert command not yet implemented");
                case "diff" -> System.out.println("Diff command not yet implemented");
                case "--help", "-h" -> printUsage();
                case "--version" -> System.out.println("H2MDiff v0.1.0");
                default -> {
                    System.err.println("Unknown command: " + command);
                    printUsage();
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("  Cause: " + e.getCause().getMessage());
            }
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("✗ Invalid argument: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints the usage information for H2MDiff to standard output.
     */
    private static void printUsage() {
        String usage = """
                Usage: h2mdiff <command> [options]
                
                Commands:
                  fetch <url>               Fetch HTML from URL or local file
                  convert <file.html>       Convert HTML file to Markdown
                  diff <file1.md> <file2.md>  Generate diff between Markdown files
                  
                Options:
                  --help, -h                Show this help message
                  --version                 Show version information
                  
                Examples:
                  h2mdiff fetch https://example.com --output page.html
                  h2mdiff convert page.html --output page.md
                  h2mdiff diff old.md new.md --output diff.md
                """;
        System.out.println(usage);
    }
}
