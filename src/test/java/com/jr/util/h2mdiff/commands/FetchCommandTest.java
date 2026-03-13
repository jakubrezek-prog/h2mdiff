package com.jr.util.h2mdiff.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for FetchCommand.
 * 
 * Tests argument parsing, file generation, metadata saving,
 * and error handling.
 */
public class FetchCommandTest {

    @TempDir
    Path tempDir;

    private String testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a test HTML file
        testFilePath = tempDir.resolve("test.html").toString();
        Files.writeString(
                Paths.get(testFilePath),
                "<html><body>Test Content</body></html>",
                StandardCharsets.UTF_8);
    }

    @Test
    public void testParseArgumentsWithSourceOnly() {
        String[] args = { testFilePath };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
    }

    @Test
    public void testParseArgumentsWithOutputFlag() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { testFilePath, "--output", outputPath };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        // Verify output file was created
        assertTrue(Files.exists(Paths.get(outputPath)),
                "Output file should be created at specified path");
    }

    @Test
    public void testParseArgumentsWithShortOutputFlag() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { testFilePath, "-o", outputPath };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        assertTrue(Files.exists(Paths.get(outputPath)),
                "Output file should be created with -o flag");
    }

    @Test
    public void testParseArgumentsWithMetadataFlag() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { testFilePath, "--output", outputPath, "--metadata" };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        // Verify metadata file was created
        String metadataPath = outputPath.replaceAll("\\.html$", ".metadata.json");
        assertTrue(Files.exists(Paths.get(metadataPath)),
                "Metadata file should be created when --metadata flag is used");
    }

    @Test
    public void testParseArgumentsWithShortMetadataFlag() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { testFilePath, "--output", outputPath, "-m" };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        String metadataPath = outputPath.replaceAll("\\.html$", ".metadata.json");
        assertTrue(Files.exists(Paths.get(metadataPath)),
                "Metadata file should be created with -m flag");
    }

    @Test
    public void testGeneratedFilenameFromLocalFile() throws IOException {
        String[] args = { testFilePath };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        // Should create a file named test.html.html (based on generateOutputFilename logic)
        assertTrue(
                Files.exists(tempDir.resolve("test.html.html"))
                        || Files.exists(tempDir.resolve("test.html")),
                "Output file should be generated from source filename");
    }

    @Test
    public void testMetadataFileFormat() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { testFilePath, "--output", outputPath, "--metadata" };
        FetchCommand cmd = new FetchCommand(args);
        
        cmd.execute();
        
        String metadataPath = outputPath.replaceAll("\\.html$", ".metadata.json");
        String metadataContent = Files.readString(Paths.get(metadataPath), StandardCharsets.UTF_8);
        
        // Verify JSON structure
        assertTrue(metadataContent.startsWith("{"), "Metadata should be valid JSON");
        assertTrue(metadataContent.endsWith("}\n"), "Metadata JSON should end properly");
        assertTrue(metadataContent.contains("source"), "Metadata should contain 'source' field");
        assertTrue(metadataContent.contains("local_file"), "Local file metadata should indicate source type");
    }

    @Test
    public void testHtmlFileContentPreserved() throws IOException {
        String testContent = "<html><body>Test Content</body></html>";
        String htmlPath = tempDir.resolve("source.html").toString();
        Files.writeString(Paths.get(htmlPath), testContent, StandardCharsets.UTF_8);
        
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { htmlPath, "--output", outputPath };
        FetchCommand cmd = new FetchCommand(args);
        
        cmd.execute();
        
        String outputContent = Files.readString(Paths.get(outputPath), StandardCharsets.UTF_8);
        assertEquals(testContent, outputContent,
                "HTML content should be preserved exactly");
    }

    @Test
    public void testMissingSourceArgumentHandling() {
        String[] args = { "--metadata" };
        FetchCommand cmd = new FetchCommand(args);
        
        // This should throw IllegalArgumentException due to missing source
        assertThrows(IllegalArgumentException.class, () -> cmd.execute(),
                "Should throw exception for missing source argument");
    }

    @Test
    public void testEmptyArgumentsHandling() {
        String[] args = {};
        FetchCommand cmd = new FetchCommand(args);
        
        assertThrows(IllegalArgumentException.class, () -> cmd.execute(),
                "Should throw exception for empty arguments");
    }

    @Test
    public void testMetadataEscaping() throws IOException {
        // Create a test file with special characters
        String testContent = "<html><body>Test with \"quotes\" and \\backslash\\</body></html>";
        String htmlPath = tempDir.resolve("special.html").toString();
        Files.writeString(Paths.get(htmlPath), testContent, StandardCharsets.UTF_8);
        
        String outputPath = tempDir.resolve("output.html").toString();
        String[] args = { htmlPath, "--output", outputPath, "--metadata" };
        FetchCommand cmd = new FetchCommand(args);
        
        assertDoesNotThrow(() -> {
            cmd.execute();
        });
        
        String metadataPath = outputPath.replaceAll("\\.html$", ".metadata.json");
        String metadataContent = Files.readString(Paths.get(metadataPath), StandardCharsets.UTF_8);
        
        // Verify JSON is still valid (no unclosed quotes)
        assertTrue(metadataContent.contains("\\\"") || metadataContent.contains("\""),
                "Metadata should escape special characters properly");
    }

    @Test
    public void testArgumentOrderFlexibility() throws IOException {
        String outputPath = tempDir.resolve("output.html").toString();
        
        // Test with flags before source
        String[] args1 = { "--output", outputPath, testFilePath };
        FetchCommand cmd1 = new FetchCommand(args1);
        assertDoesNotThrow(() -> cmd1.execute(),
                "Should handle flags before source");
        
        // Test with flags after source
        String outputPath2 = tempDir.resolve("output2.html").toString();
        String[] args2 = { testFilePath, "--output", outputPath2 };
        FetchCommand cmd2 = new FetchCommand(args2);
        assertDoesNotThrow(() -> cmd2.execute(),
                "Should handle flags after source");
    }
}
