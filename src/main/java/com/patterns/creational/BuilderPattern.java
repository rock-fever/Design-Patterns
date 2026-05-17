package com.patterns.creational;


// =============================================================================
// BUILDER PATTERN
// =============================================================================
// Intent:
//   Construct a complex object step by step, separating construction from representation.
//
// When to use:
//   - Object has many optional fields
//   - Telescoping constructors become unreadable
//   - You want immutable objects without a huge constructor
//
// What to implement:
//   1. A class HttpRequest with final fields: method, url, body (optional),
//      timeoutMs (default 5000), followRedirects (default true)
//   2. Private constructor that takes a Builder
//   3. Nested static Builder class with the same fields
//   4. Builder methods: body(), timeoutMs(), followRedirects() — each returns `this`
//   5. Builder.build() returns a new HttpRequest
//
// Key concepts to understand:
//   - Why the outer class constructor is private
//   - How method chaining (fluent API) works via returning `this`
//   - How defaults are set (in Builder fields, not in HttpRequest)
//   - Why this produces immutable objects
// =============================================================================
public class BuilderPattern {

    public static class HttpRequest {
        private final String method;
        private final String url;
        private final String body; // optional
        private final int timeoutMs;
        private final boolean followRedirects;

        @Override
        public String toString() {
            return "HttpRequest{method='" + method + "', url='" + url + "', body='" + body
                    + "', timeoutMs=" + timeoutMs + ", followRedirects=" + followRedirects + "}";
        }

        public static Builder builder() {
            return new Builder();
        }

        private HttpRequest(Builder builder) {
            this.method = builder.method;
            this.url = builder.url;
            this.body = builder.body;
            this.followRedirects = builder.followRedirects;
            this.timeoutMs = builder.timeoutMs;
        }

        static class Builder {
            private String method;
            private String url;
            private String body; // optional
            private int timeoutMs = 5000;
            private boolean followRedirects = true;

            public Builder body(String val) {
                this.body = val;
                return this;
            }

            public Builder url(String val) {
                this.url = val;
                return this;
            }

            public Builder method(String val) {
                this.method = val;
                return this;
            }

            public Builder timeoutMs(int val) {
                this.timeoutMs = val;
                return this;
            }

            public Builder followRedirects(boolean val) {
                this.followRedirects = val;
                return this;
            }

            public HttpRequest build() {
                if (method == null || method.isBlank()) throw new IllegalStateException("method is required");
                if (url == null || url.isBlank()) throw new IllegalStateException("url is required");
                return new HttpRequest(this);
            }
        }

    }

    public static void main(String[] args) {
        // Build a GET request with only timeoutMs set
        HttpRequest httpGetRequestWithTimeOut 
        = HttpRequest.builder()
        .method("GET")
        .url("http://localhost:8080")
        .timeoutMs(1000)
        .build();
        
        // Build a POST request with body and followRedirects=false
        HttpRequest httpPostRequestWithFollowRedirects 
        = HttpRequest.builder()
                .method("POST")
                .url("http://localhost:8080")
                .followRedirects(true)
                .build();
            
        // Print both and verify defaults are applied where not specified
        System.out.println(httpGetRequestWithTimeOut);
        System.out.println(httpPostRequestWithFollowRedirects);
    }
}
