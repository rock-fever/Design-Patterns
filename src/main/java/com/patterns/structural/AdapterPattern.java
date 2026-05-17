package com.patterns.structural;

import java.util.HashMap;
import java.util.Map;

// =============================================================================
// ADAPTER PATTERN
// =============================================================================
// Intent:
//   Convert the interface of a class into another interface that clients expect.
//   Lets incompatible interfaces work together.
//
// When to use:
//   - Integrating a third-party library with an incompatible interface
//   - Reusing existing code that doesn't match the required interface
//
// What to implement:
//   1. Interface JsonParser with: Map<String, Object> parse(String input)
//   2. A legacy class XmlParser (existing, cannot be modified) with:
//        - Document parseXml(String xml)  ← incompatible method signature
//        - (simulate Document as a simple wrapper class with a Map field)
//   3. Adapter class XmlToJsonAdapter implements JsonParser:
//        - Wraps an XmlParser instance
//        - parse() calls xmlParser.parseXml(), then converts Document → Map
//
// Key concepts to understand:
//   - Adapter wraps the adaptee — it does not extend it
//   - Client only knows about the target interface (JsonParser)
//   - Object adapter (composition) vs class adapter (inheritance) — prefer composition
// =============================================================================

public class AdapterPattern {

    // Target interface — what the client depends on
    public interface JsonParser {
        Map<String, Object> parse(String input);
    }

    // Simulated Document (returned by XmlParser)
    static class Document {
        Map<String, Object> fields;

        Document(Map<String, Object> fields) {
            this.fields = fields;
        }
    }

    // Adaptee — legacy class with incompatible interface, cannot be modified
    static class XmlParser {
        public Document parseXml(String xml) {
            // Simulate parsing XML into a Document
            Map<String, Object> fields = new HashMap<>();
            fields.put("source", "xml");
            fields.put("content", xml);
            return new Document(fields);
        }
    }

    // Adapter — wraps XmlParser, exposes JsonParser interface
    static class XmlToJsonAdapter implements JsonParser {
        private final XmlParser xmlParser;

        XmlToJsonAdapter(XmlParser xmlParser) {
            this.xmlParser = xmlParser;
        }

        @Override
        public Map<String, Object> parse(String input) {
            Document doc = xmlParser.parseXml(input);
            return doc.fields;
        }
    }

    public static void main(String[] args) {
        JsonParser parser = new XmlToJsonAdapter(new XmlParser());
        Map<String, Object> result = parser.parse("<tag>hello</tag>");
        System.out.println(result);
        // Output: {source=xml, content=<tag>hello</tag>}
    }
}
