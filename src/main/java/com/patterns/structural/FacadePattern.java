package com.patterns.structural;

// =============================================================================
// FACADE PATTERN
// =============================================================================
// Intent:
//   Provide a simplified interface to a complex subsystem.
//
// When to use:
//   - A subsystem has many classes and complex interactions
//   - You want a single entry point that hides the complexity
//   - You want to decouple client code from subsystem internals
//
// What to implement:
//   Subsystem classes (complex, many steps):
//   1. VideoDecoder  — decode(String file): prints "Decoding <file>"
//   2. AudioMixer    — mix(String file): prints "Mixing audio for <file>"
//   3. VideoEncoder  — encode(String file, String format): prints "Encoding to <format>"
//   4. FileSaver     — save(String output): prints "Saving <output>"
//
//   Facade:
//   5. VideoConversionFacade with one method:
//        String convert(String filename, String format)
//        — internally coordinates all 4 subsystem classes in the right order
//        — client only calls this one method
//
// Key concepts to understand:
//   - Facade does not prevent direct access to subsystem classes (not a lock)
//   - It simplifies the common case; power users can still go deeper
//   - Reduces coupling between client and subsystem
//   - Difference from Adapter: Facade simplifies; Adapter translates interfaces
// =============================================================================

public class FacadePattern {

    static class VideoDecoder {
        void decode(String file) {
            System.out.println("Decoding " + file);
        }
    }

    static class AudioMixer {
        void mix(String file) {
            System.out.println("Mixing audio for " + file);
        }
    }

    static class VideoEncoder {
        void encode(String file, String format) {
            System.out.println("Encoding to " + format);
        }
    }

    static class FileSaver {
        void save(String output) {
            System.out.println("Saving " + output);
        }
    }

    static class VideoConversionFacade {
        private final VideoDecoder decoder = new VideoDecoder();
        private final AudioMixer mixer = new AudioMixer();
        private final VideoEncoder encoder = new VideoEncoder();
        private final FileSaver saver = new FileSaver();

        String convert(String filename, String format) {
            String output = filename.replaceAll("\\.[^.]+$", "") + "." + format;
            decoder.decode(filename);
            mixer.mix(filename);
            encoder.encode(filename, format);
            saver.save(output);
            return output;
        }
    }

    public static void main(String[] args) {
        String result = new VideoConversionFacade().convert("movie.avi", "mp4");
        System.out.println("Done: " + result);
    }
}
