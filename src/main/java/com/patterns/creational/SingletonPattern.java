package com.patterns.creational;

// =============================================================================
// SINGLETON PATTERN
// =============================================================================
// Intent:
//   Ensure a class has only one instance and provide a global access point to it.
//
// When to use:
//   - Shared config, connection pool, logger, registry
//
// What to implement:
//   1. A class AppConfig with private constructor
//   2. A static volatile field holding the single instance
//   3. A static getInstance() method using double-checked locking
//      (first check outside synchronized, second inside — understand why both are needed)
//   4. A few fields (e.g. env, maxConnections) set in the private constructor
//
// Key concepts to understand:
//   - Why the constructor must be private
//   - Why the instance field must be volatile
//   - What double-checked locking prevents
//   - Thread safety without locking on every call
// =============================================================================

public class SingletonPattern {

    static class AppConfig {

        private static volatile AppConfig instance;

        private final String env;
        private final int maxConnections;

        private AppConfig() {
            this.env = "production";
            this.maxConnections = 10;
        }

        static AppConfig getInstance() {
            if (instance == null) {                          // first check (no lock)
                synchronized (AppConfig.class) {
                    if (instance == null) {                  // second check (with lock)
                        instance = new AppConfig();
                    }
                }
            }
            return instance;
        }

        @Override
        public String toString() {
            return "AppConfig{env='" + env + "', maxConnections=" + maxConnections + "}";
        }
    }

    public static void main(String[] args) {
        AppConfig a = AppConfig.getInstance();
        AppConfig b = AppConfig.getInstance();

        System.out.println(a);
        System.out.println(b);
        System.out.println("Same instance: " + (a == b));
    }
}
