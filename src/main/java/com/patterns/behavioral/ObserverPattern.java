package com.patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

// =============================================================================
// OBSERVER PATTERN
// =============================================================================
// Intent:
//   Define a one-to-many dependency so that when one object changes state,
//   all its dependents are notified and updated automatically.
//
// When to use:
//   - An event in one object should trigger reactions in others
//   - You don't want the subject tightly coupled to its observers
//   - Number of observers is unknown or changes at runtime
//
// What to implement:
//   1. Interface Observer with: void update(String event, Object data)
//   2. Interface Observable (Subject) with:
//        - subscribe(Observer o)
//        - unsubscribe(Observer o)
//        - notifyObservers(String event, Object data)
//   3. Class EventBus implements Observable — maintains a List<Observer>
//   4. Three concrete observers:
//        - EmailAlertObserver: prints "Email alert: <event> - <data>"
//        - AuditLogObserver: prints "Audit log: <event> - <data>"
//        - MetricsObserver: prints "Metrics recorded: <event>"
//
// Key concepts to understand:
//   - Subject does not know what observers do — it just notifies
//   - Observers can be added/removed at runtime
//   - Used in Nexus: Kafka consumers are observers reacting to route/tenant events
//   - Java has built-in support via PropertyChangeListener — understand why custom is still useful
// =============================================================================

public class ObserverPattern {

    public interface Observer {
        void update(String event, Object data);
    }

    public interface Observable {
        void subscribe(Observer o);
        void unsubscribe(Observer o);
        void notifyObservers(String event, Object data);
    }

    static class EventBus implements Observable {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void subscribe(Observer o) {
            observers.add(o);
        }

        @Override
        public void unsubscribe(Observer o) {
            observers.remove(o);
        }

        @Override
        public void notifyObservers(String event, Object data) {
            for (Observer o : observers) {
                o.update(event, data);
            }
        }
    }

    static class EmailAlertObserver implements Observer {
        @Override
        public void update(String event, Object data) {
            System.out.println("Email alert: " + event + " - " + data);
        }
    }

    static class AuditLogObserver implements Observer {
        @Override
        public void update(String event, Object data) {
            System.out.println("Audit log: " + event + " - " + data);
        }
    }

    static class MetricsObserver implements Observer {
        @Override
        public void update(String event, Object data) {
            System.out.println("Metrics recorded: " + event);
        }
    }

    public static void main(String[] args) {
        EventBus bus = new EventBus();
        AuditLogObserver audit = new AuditLogObserver();

        bus.subscribe(new EmailAlertObserver());
        bus.subscribe(audit);
        bus.subscribe(new MetricsObserver());

        System.out.println("-- Publishing USER_CREATED (3 observers) --");
        bus.notifyObservers("USER_CREATED", "user@example.com");

        bus.unsubscribe(audit);

        System.out.println("\n-- Publishing USER_DELETED (2 observers) --");
        bus.notifyObservers("USER_DELETED", "user@example.com");
    }
}
