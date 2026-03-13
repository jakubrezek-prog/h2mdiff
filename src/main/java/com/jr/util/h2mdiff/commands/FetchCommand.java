package com.jr.util.h2mdiff.commands;

import com.jr.util.h2mdiff.lib.HttpFetcher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 * Command handler for fetching HTML from URLs or local files.
 * 
 * Handles URL validation, local file detection, and optional metadata capture.
 */
public class FetchCommand {
    
    private final String[] args;
    private final HttpFetcher fetcher = new HttpFetcher();
    
    /**
     * Creates a FetchCommand with command-line arguments.
     *
     * @param args command-line arguments (source, --output, --metadata)
     */
    public FetchCommand(String[] args) {
        this.args = args;
    }
    
    /**
     * Executes the fetch command.
     * 
     * @throws IOException if fetch fails
     * @throws IllegalArgumentException if arguments are invalid
     */
    public void execute() throws IOException, IllegalArgumentException {
        // Parse arguments
        ParsedArgs parsed = parseArguments();
        
        if (parsed.source == null) {
            printUsage();
            throw new IllegalArgumentException("Source URL or file path is required");
        }
        
        // Fetch the HTML content
        HttpFetcher.FetchResult result = fetcher.fetch(parsed.source);
        
        // Determine output file name
        String outputPath = parsed.outputFile != null ? 
                parsed.outputFile : generateOutputFilename(result.source());
        
        // Save HTML content
        Files.writeString(Paths.get(outputPath), result.htmlContent(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
        
        System.out.println("✓ HTML saved to: " + outputPath);
        
        // Save metadata if requested
        if (parsed.captureMetadata) {
            saveMetadata(result.metadata(), outputPath);
        }
    }
    
    /**
     * Parses command-line arguments.
     *
     * @return ParsedArgs containing parsed values
     */
    private ParsedArgs parseArguments() {
        ParsedArgs result = new ParsedArgs();
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--output") || args[i].equals("-o")) {
                if (i + 1 < args.length) {
                    result.outputFile = args[++i];
                }
            } else if (args[i].equals("--metadata") || args[i].equals("-m")) {
                result.captureMetadata = true;
            } else if (!args[i].startsWith("--") && !args[i].startsWith("-")) {
                result.source = args[i];
            }
        }
        
        return result;
    }
    
    /**
     * Generates output filename from source URL or path.
     *
     * @param source the source URL or file path
     * @return generated filename
     */
    private String generateOutputFilename(String source) {
        String filename;
        
        if (source.startsWith("http://") || source.startsWith("https://")) {
            // Extract domain and clean it for filename
            try {
                String domain = new java.net.URL(source).getHost();
                filename = domain.replace(".", "-") + ".html";
            } catch (Exception e) {
                filename = "fetched-" + System.currentTimeMillis() + ".html";
            }
        } else {
            // For local files, keep the name but ensure .html extension
            filename = Paths.get(source).getFileName().toString();
            if (!filename.endsWith(".html")) {
                filename = filename + ".html";
            }
        }
        
        return filename;
    }
    
    /**
     * Saves fetch metadata to a separate file.
     *
     * @param metadata the metadata map
     * @param htmlFilePath the path to the HTML file
     * @throws IOException if metadata file cannot be written
     */
    private void saveMetadata(Map<String, String> metadata, String htmlFilePath) throws IOException {
        String metadataPath = htmlFilePath.replaceAll("\\.html$", ".metadata.json");
        
        StringBuilder json = new StringBuilder("{\n");
        metadata.forEach((key, value) ->
                json.append("  \"").append(key).append("\": \"")
                    .append(escapeJson(value)).append("\",\n"));
        
        // Remove trailing comma
        if (json.length() > 2) {
            json.setLength(json.length() - 2);
        }
        json.append("\n}\n");
        
        Files.writeString(Paths.get(metadataPath), json.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
        
        System.out.println("✓ Metadata saved to: " + metadataPath);
    }
    
    /**
     * Escapes special characters in JSON string values.
     *
     * @param value the string to escape
     * @return escaped string safe for JSON
     */
    private String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    /**
     * Prints usage information for fetch command.
     */
    private void printUsage() {
        System.out.println("Usage: h2mdiff fetch <url|file> [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --output, -o <path>    Output file path");
        System.out.println("  --metadata, -m          Save HTTP metadata");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  h2mdiff fetch https://example.com");
        System.out.println("  h2mdiff fetch https://example.com --output page.html");
        System.out.println("  h2mdiff fetch ./local.html --metadata");
    }
    
    /**
     * Simple POJO for parsed command arguments.
     */
    private static class ParsedArgs {
        String source;
        String outputFile;
        boolean captureMetadata = false;
    }
}
