package com.patterns.behavioral;

// =============================================================================
// CHAIN OF RESPONSIBILITY PATTERN
// =============================================================================
// Intent:
//   Pass a request along a chain of handlers. Each handler decides to process
//   the request or pass it to the next handler in the chain.
//
// When to use:
//   - More than one object may handle a request and the handler isn't known a priori
//   - You want to issue a request to one of several handlers without coupling sender to receiver
//   - The set of handlers should be specifiable dynamically
//
// What to implement:
//   1. Abstract class SupportHandler with:
//        - SupportHandler next (linked list style)
//        - setNext(SupportHandler) returning SupportHandler (for chaining)
//        - abstract void handle(int level, String issue)
//   2. Three concrete handlers:
//        - FrontlineSupport: handles level <= 1, else passes to next
//        - TechnicalSupport: handles level <= 2, else passes to next
//        - ManagementSupport: handles everything that reaches it
//
// Key concepts to understand:
//   - Each handler only knows about the next — not the whole chain
//   - Request may be handled at any point or not at all (if chain ends)
//   - Used in Nexus: FilterChainExecutor — each filter decides to process or proceed
//   - Difference: Nexus chain always proceeds; classic CoR stops at the handler
// =============================================================================

public class ChainOfResponsibilityPattern {

    static abstract class SupportHandler {
        private SupportHandler next;

        SupportHandler setNext(SupportHandler next) {
            this.next = next;
            return next;
        }

        abstract void handle(int level, String issue);

        protected void passToNext(int level, String issue) {
            if (next != null) {
                next.handle(level, issue);
            } else {
                System.out.println("No handler found for level " + level + ": " + issue);
            }
        }
    }

    static class FrontlineSupport extends SupportHandler {
        @Override
        void handle(int level, String issue) {
            if (level <= 1) {
                System.out.println("FrontlineSupport handled level " + level + ": " + issue);
            } else {
                passToNext(level, issue);
            }
        }
    }

    static class TechnicalSupport extends SupportHandler {
        @Override
        void handle(int level, String issue) {
            if (level <= 2) {
                System.out.println("TechnicalSupport handled level " + level + ": " + issue);
            } else {
                passToNext(level, issue);
            }
        }
    }

    static class ManagementSupport extends SupportHandler {
        @Override
        void handle(int level, String issue) {
            System.out.println("ManagementSupport handled level " + level + ": " + issue);
        }
    }

    public static void main(String[] args) {
        SupportHandler frontline   = new FrontlineSupport();
        SupportHandler technical   = new TechnicalSupport();
        SupportHandler management  = new ManagementSupport();

        frontline.setNext(technical).setNext(management);  // builds the chain

        frontline.handle(1, "Password reset");
        frontline.handle(2, "Application crash");
        frontline.handle(3, "Data breach escalation");
    }
}
