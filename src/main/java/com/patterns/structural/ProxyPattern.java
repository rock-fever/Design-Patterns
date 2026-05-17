package com.patterns.structural;

import java.util.HashMap;
import java.util.Map;

// =============================================================================
// PROXY PATTERN
// =============================================================================
// Intent:
//   Provide a surrogate or placeholder for another object to control access to it.
//
// When to use:
//   - Lazy initialisation (virtual proxy) — delay expensive object creation
//   - Access control (protection proxy) — check permissions before delegating
//   - Caching (caching proxy) — return cached result instead of re-executing
//   - Logging/metrics — record calls without modifying the real object
//
// What to implement:
//   1. Interface DatabaseService with: String query(String sql)
//   2. RealDatabaseService implements DatabaseService — simulates slow DB call
//      (print "Executing query..." and return a fake result)
//   3. CachingDatabaseProxy implements DatabaseService:
//        - Wraps a RealDatabaseService (lazy — only create it on first use)
//        - Maintains a Map<String, String> cache
//        - query(): return cached result if present, otherwise delegate and cache
//
// Key concepts to understand:
//   - Proxy implements the same interface as the real subject
//   - Client cannot tell if it's talking to a proxy or the real object
//   - Difference from Decorator: Proxy controls access; Decorator adds behaviour
//   - Lazy init: RealDatabaseService is only created when first query is made
// =============================================================================

public class ProxyPattern {

    public interface DatabaseService {
        String query(String sql);
    }

    static class RealDatabaseService implements DatabaseService {

        @Override
        public String query(String sql) {
            System.out.println("Executing query..." + sql);
            return "Result";
        }
    }

    static class CachingDatabaseService implements DatabaseService {
        private RealDatabaseService dbService;
        private final Map<String, String> cache = new HashMap<>();

        @Override
        public String query(String sql) {
            if (cache.containsKey(sql)) {
                System.out.println("Cache hit for: " + sql);
                return cache.get(sql);
            }
            if (dbService == null) {
                dbService = new RealDatabaseService();
            }
            String result = dbService.query(sql);
            cache.put(sql, result);
            return result;
        }
    }

    public static void main(String[] args) {
        DatabaseService service = new CachingDatabaseService();

        System.out.println(service.query("SELECT * FROM users"));
        System.out.println(service.query("SELECT * FROM users")); // cache hit
        System.out.println(service.query("SELECT * FROM orders")); // new query
        // Output:
        // Executing query...SELECT * FROM users
        // Result
        // Cache hit for: SELECT * FROM users
        // Result
        // Executing query...SELECT * FROM orders
        // Result
    }
}
