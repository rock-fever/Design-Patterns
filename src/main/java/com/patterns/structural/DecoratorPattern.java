package com.patterns.structural;

// =============================================================================
// DECORATOR PATTERN
// =============================================================================
// Intent:
//   Attach additional responsibilities to an object dynamically.
//   Decorators provide a flexible alternative to subclassing for extending behaviour.
//
// When to use:
//   - You want to add behaviour to individual objects, not the whole class
//   - Subclassing would produce an explosion of combinations
//
// What to implement:
//   1. Interface DataWriter with: void write(String data)
//   2. Base class FileDataWriter implements DataWriter — just prints "Writing: <data>"
//   3. Abstract class DataWriterDecorator implements DataWriter:
//        - Wraps a DataWriter instance
//        - Delegates write() to the wrapped instance
//   4. Concrete decorators (each extends DataWriterDecorator):
//        - CompressionDecorator: prepends "Compressed: " to data before delegating
//        - EncryptionDecorator: prepends "Encrypted: " to data before delegating
//
// Key concepts to understand:
//   - Decorators implement the same interface as the component they wrap
//   - They can be stacked: encrypt(compress(fileWriter))
//   - Each decorator adds one responsibility — Single Responsibility Principle
//   - Difference from inheritance: behaviour added at runtime, not compile time
// =============================================================================

public class DecoratorPattern {

    public interface DataWriter {
        void write(String data);
    }

    static class FileDataWriter implements DataWriter {

        @Override
        public void write(String data) {
            System.out.println("Writing: " + data);
        }
    }

    static abstract class DataWriterDecorator implements DataWriter {
        protected DataWriter wrappee;

        DataWriterDecorator(DataWriter wrappee) {
            this.wrappee = wrappee;
        }

        @Override
        public void write(String data) {
            wrappee.write(data);
        }
    }

    static class CompressionDecorator extends DataWriterDecorator {
        CompressionDecorator(DataWriter wrappee) {
            super(wrappee);
        }

        @Override
        public void write(String data) {
            super.write("Compressed: " + data);
        }
    }

    static class EncryptionDecorator extends DataWriterDecorator {
        EncryptionDecorator(DataWriter wrappee) {
            super(wrappee);
        }

        @Override
        public void write(String data) {
            super.write("Encrypted: " + data);
        }
    }

    public static void main(String[] args) {
        DataWriter writer = new EncryptionDecorator(
            new CompressionDecorator(
                new FileDataWriter()
            )
        );
        writer.write("Hello, World!");
        // Output: Writing: Encrypted: Compressed: Hello, World!
    }
}
