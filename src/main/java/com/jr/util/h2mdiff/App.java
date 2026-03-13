package com.jr.util.h2mdiff;

/**
 * H2MDiff - CLI utility for fetching HTML, converting to Markdown, and producing diffs.
 * 
 * Main entry point for the application.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("H2MDiff v0.1.0");
        System.out.println("HTML to Markdown Diff Utility");
        
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }
        
        String command = args[0];
        String[] commandArgs = java.util.Arrays.copyOfRange(args, 1, args.length);
        
        switch (command) {
            case "fetch" -> System.out.println("Fetch command not yet implemented");
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
    }

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
