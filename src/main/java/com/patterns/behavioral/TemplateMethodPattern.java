package com.patterns.behavioral;

// =============================================================================
// TEMPLATE METHOD PATTERN
// =============================================================================
// Intent:
//   Define the skeleton of an algorithm in a base class, deferring some steps
//   to subclasses. Subclasses redefine certain steps without changing the algorithm's structure.
//
// When to use:
//   - Multiple classes share the same algorithm structure but differ in specific steps
//   - You want to enforce a fixed sequence of steps while allowing customisation of each step
//
// What to implement:
//   1. Abstract class ReportGenerator with a final method generate():
//        generate() calls these steps in order:
//          - fetchData()     ← abstract
//          - processData()   ← abstract
//          - formatOutput()  ← abstract
//          - saveReport()    ← concrete (shared: prints "Saving report...")
//   2. Concrete subclasses:
//        - CsvReportGenerator: fetches CSV data, processes it, formats as CSV
//        - PdfReportGenerator: fetches DB data, processes it, formats as PDF
//
// Key concepts to understand:
//   - generate() is final — subclasses cannot change the order of steps
//   - Only the variable steps are abstract — fixed steps stay in the base class
//   - Used in Nexus: AbstractTenantRateLimiter — checkRateLimit() is the template,
//     getLimit() is the hook subclasses override
//   - Difference from Strategy: Template Method uses inheritance; Strategy uses composition
// =============================================================================

public class TemplateMethodPattern {

    abstract static class ReportGenerator {
        final void generate() {
            fetchData();
            processData();
            formatOutput();
            saveReport();
        }

        abstract void fetchData();
        abstract void processData();
        abstract void formatOutput();

        void saveReport() {
            System.out.println("Saving report...");
        }
    }

    static class CsvReportGenerator extends ReportGenerator {
        @Override
        void fetchData() {
            System.out.println("CsvReport: fetching data from CSV file");
        }

        @Override
        void processData() {
            System.out.println("CsvReport: parsing CSV rows");
        }

        @Override
        void formatOutput() {
            System.out.println("CsvReport: formatting output as CSV");
        }
    }

    static class PdfReportGenerator extends ReportGenerator {
        @Override
        void fetchData() {
            System.out.println("PdfReport: fetching data from database");
        }

        @Override
        void processData() {
            System.out.println("PdfReport: aggregating and transforming rows");
        }

        @Override
        void formatOutput() {
            System.out.println("PdfReport: rendering output as PDF");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- CSV Report ---");
        new CsvReportGenerator().generate();

        System.out.println("\n--- PDF Report ---");
        new PdfReportGenerator().generate();
    }
}
