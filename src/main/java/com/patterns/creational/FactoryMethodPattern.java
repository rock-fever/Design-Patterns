package com.patterns.creational;

// =============================================================================
// FACTORY METHOD PATTERN
// =============================================================================
// Intent:
//   Define an interface for creating an object, but let subclasses decide which
//   class to instantiate.
//
// When to use:
//   - The exact type to create isn't known until runtime
//   - Creation logic needs to be overridable by subclasses
//
// What to implement:
//   1. Interface Notification with send(String message)
//   2. Two implementations: EmailNotification, SmsNotification
//   3. Abstract class NotificationSender with:
//        - abstract Notification createNotification(String target)  ← factory method
//        - concrete notify(String target, String message) that calls createNotification()
//   4. Two concrete creators: EmailSender, SmsSender — each overrides createNotification()
//
// Key concepts to understand:
//   - The factory method is abstract — subclasses own the "what to create" decision
//   - The base class owns the "how to use it" logic (notify)
//   - Difference from Abstract Factory: Factory Method is one product, one method
// =============================================================================

public class FactoryMethodPattern {

    public interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {
        private final String email;

        EmailNotification(String email) {
            this.email = email;
        }

        @Override
        public void send(String message) {
            System.out.println("Sending message via email to " + email + ": " + message);
        }
    }

    static class SmsNotification implements Notification {
        private final String phone;

        SmsNotification(String phone) {
            this.phone = phone;
        }

        @Override
        public void send(String message) {
            System.out.println("Sending message via SMS to " + phone + ": " + message);
        }
    }

    static abstract class NotificationSender {
        abstract Notification createNotification(String target);

        void notify(String target, String message) {
            Notification notification = createNotification(target);
            notification.send(message);
        }
    }

    static class EmailSender extends NotificationSender {
        @Override
        Notification createNotification(String target) {
            return new EmailNotification(target);
        }
    }

    static class SmsSender extends NotificationSender {
        @Override
        Notification createNotification(String target) {
            return new SmsNotification(target);
        }
    }

    public static void main(String[] args) {
        NotificationSender emailSender = new EmailSender();
        emailSender.notify("user@example.com", "Your order has been placed.");

        NotificationSender smsSender = new SmsSender();
        smsSender.notify("+1234567890", "Your OTP is 482910.");
    }
}
