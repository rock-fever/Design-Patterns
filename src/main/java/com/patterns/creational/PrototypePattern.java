package com.patterns.creational;

import java.util.ArrayList;
import java.util.List;

// =============================================================================
// PROTOTYPE PATTERN
// =============================================================================
// Intent:
//   Create new objects by copying (cloning) an existing object instead of
//   constructing from scratch.
//
// When to use:
//   - Object creation is expensive (e.g. DB fetch, heavy computation)
//   - You need many similar objects with slight variations
//   - You want to avoid subclassing just to create objects
//
// What to implement:
//   1. Interface Prototype with clone() returning Prototype
//   2. Class NetworkConfig with fields: host, port, headers (List<String>)
//      - Implement clone() — perform a DEEP copy (copy the list, not the reference)
//      - Understand why shallow copy of the list would be a bug
//   3. Class DatabaseConfig with fields: url, poolSize
//      - Implement clone()
//
// Key concepts to understand:
//   - Shallow copy vs deep copy — when does it matter?
//   - Java's built-in Cloneable vs implementing your own clone method
//   - Why copying beats constructing when creation is expensive
// =============================================================================

public class PrototypePattern {

    public interface Prototype {
        Prototype clone();
    }

    static class NetworkConfig implements Prototype {
        private String host;
        private int port;
        private List<String> headers;

        NetworkConfig(String host, int port, List<String> headers) {
            this.host = host;
            this.port = port;
            this.headers = headers;
        }

        @Override
        public Prototype clone() {
            return new NetworkConfig(this.host, this.port, new ArrayList<>(this.headers)); // deep copy list
        }

        void setHost(String host) { this.host = host; }
        void addHeader(String header) { this.headers.add(header); }

        @Override
        public String toString() {
            return "NetworkConfig{host='" + host + "', port=" + port + ", headers=" + headers + "}";
        }
    }

    static class DatabaseConfig implements Prototype {
        private String url;
        private int poolSize;

        DatabaseConfig(String url, int poolSize) {
            this.url = url;
            this.poolSize = poolSize;
        }

        @Override
        public Prototype clone() {
            return new DatabaseConfig(this.url, this.poolSize);
        }

        void setUrl(String url) { this.url = url; }

        @Override
        public String toString() {
            return "DatabaseConfig{url='" + url + "', poolSize=" + poolSize + "}";
        }
    }

    public static void main(String[] args) {
        // --- NetworkConfig: proves deep copy of list ---
        NetworkConfig original = new NetworkConfig("prod.example.com", 8080,
                new ArrayList<>(List.of("Authorization", "Content-Type")));
        NetworkConfig copy = (NetworkConfig) original.clone();

        copy.setHost("staging.example.com");
        copy.addHeader("X-Debug");

        System.out.println("Original: " + original);  // host and headers unchanged
        System.out.println("Copy:     " + copy);

        System.out.println();

        // --- DatabaseConfig: simple clone ---
        DatabaseConfig dbOriginal = new DatabaseConfig("jdbc:postgres://prod/mydb", 20);
        DatabaseConfig dbCopy = (DatabaseConfig) dbOriginal.clone();
        dbCopy.setUrl("jdbc:postgres://staging/mydb");

        System.out.println("Original: " + dbOriginal);
        System.out.println("Copy:     " + dbCopy);
    }
}
