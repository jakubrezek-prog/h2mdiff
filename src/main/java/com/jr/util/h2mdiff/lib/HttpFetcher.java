package com.jr.util.h2mdiff.lib;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for fetching HTML content from URLs or local files.
 * 
 * Handles HTTP requests with proper error handling, timeouts, and metadata capture.
 */
public class HttpFetcher {
    
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;
    private static final String USER_AGENT = "H2MDiff/0.1.0 (HTML to Markdown Diff)";
    
    private final HttpClient httpClient;
    
    /**
     * Constructs an HttpFetcher with default configuration.
     */
    public HttpFetcher() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
    
    /**
     * Fetches HTML content from a URL or local file.
     *
     * @param source URL or local file path
     * @return FetchResult containing HTML content and metadata
     * @throws IOException if fetch fails
     */
    public FetchResult fetch(String source) throws IOException {
        if (isLocalFile(source)) {
            return fetchLocalFile(source);
        } else {
            return fetchUrl(source);
        }
    }
    
    /**
     * Fetches HTML content from a URL.
     *
     * @param urlString the URL to fetch from
     * @return FetchResult containing HTML content and HTTP metadata
     * @throws IOException if the request fails
     */
    private FetchResult fetchUrl(String urlString) throws IOException {
        try {
            // Validate and create URI
            URI uri = validateAndCreateUri(urlString);
            
            // Create HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                    .header("User-Agent", USER_AGENT)
                    .GET()
                    .build();
            
            // Send request and get response
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            
            // Check status code
            if (response.statusCode() != 200) {
                throw new IOException("HTTP " + response.statusCode() + " " +
                        getStatusMessage(response.statusCode()) +
                        " from " + urlString);
            }
            
            // Capture metadata
            Map<String, String> metadata = captureHttpMetadata(response, urlString);
            
            return new FetchResult(response.body(), metadata, urlString);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Fetch interrupted: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid URL format: " + e.getMessage(), e);
        }
    }
    
    /**
     * Fetches HTML content from a local file.
     *
     * @param filePath the local file path
     * @return FetchResult containing file content
     * @throws IOException if the file cannot be read
     */
    private FetchResult fetchLocalFile(String filePath) throws IOException {
        try {
            String content = java.nio.file.Files.readString(
                    java.nio.file.Paths.get(filePath),
                    StandardCharsets.UTF_8);
            
            Map<String, String> metadata = new HashMap<>();
            metadata.put("source", "local_file");
            metadata.put("file_path", filePath);
            metadata.put("fetch_time", java.time.Instant.now().toString());
            
            return new FetchResult(content, metadata, filePath);
        } catch (java.nio.file.NoSuchFileException e) {
            throw new IOException("File not found: " + filePath, e);
        }
    }
    
    /**
     * Validates and creates a URI from a URL string.
     *
     * @param urlString the URL string to validate
     * @return validated URI
     * @throws IOException if URL is invalid
     */
    private URI validateAndCreateUri(String urlString) throws IOException {
        try {
            // Add scheme if missing
            String url = urlString;
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            
            return URI.create(url);
        } catch (Exception e) {
            throw new IOException("Invalid URL: " + urlString, e);
        }
    }
    
    /**
     * Checks if the source is a local file path.
     *
     * @param source the source string to check
     * @return true if source appears to be a local file path
     */
    private boolean isLocalFile(String source) {
        return source.startsWith("/") || source.startsWith("./") ||
               source.startsWith("../") || source.startsWith("~") ||
               (source.length() > 2 && source.charAt(1) == ':'); // Windows path
    }
    
    /**
     * Captures HTTP metadata from response.
     *
     * @param response the HTTP response
     * @param urlString the original URL
     * @return map of metadata
     */
    private Map<String, String> captureHttpMetadata(HttpResponse<?> response, String urlString) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("source", "http");
        metadata.put("url", urlString);
        metadata.put("status_code", String.valueOf(response.statusCode()));
        metadata.put("fetch_time", java.time.Instant.now().toString());
        
        response.headers().firstValue("content-type")
                .ifPresent(ct -> metadata.put("content_type", ct));
        response.headers().firstValue("content-length")
                .ifPresent(cl -> metadata.put("content_length", cl));
        response.headers().firstValue("last-modified")
                .ifPresent(lm -> metadata.put("last_modified", lm));
        
        return metadata;
    }
    
    /**
     * Returns HTTP status message for a given status code.
     *
     * @param statusCode the HTTP status code
     * @return status message
     */
    private String getStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            case 502 -> "Bad Gateway";
            case 503 -> "Service Unavailable";
            default -> "Error";
        };
    }
    
    /**
     * Result of an HTTP fetch operation.
     *
     * @param htmlContent the fetched HTML content
     * @param metadata HTTP metadata captured during fetch
     * @param source the source URL or file path
     */
    public record FetchResult(String htmlContent, Map<String, String> metadata, String source) {}
}
