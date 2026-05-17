package com.patterns.behavioral;

// =============================================================================
// STATE PATTERN
// =============================================================================
// Intent:
//   Allow an object to alter its behaviour when its internal state changes.
//   The object will appear to change its class.
//
// When to use:
//   - An object's behaviour depends on its state and must change at runtime
//   - You have large conditionals that switch behaviour based on state (if/switch)
//
// What to implement:
//   1. Interface TrafficLightState with: void handle(TrafficLight context)
//   2. Three concrete states:
//        - RedState:    prints "STOP", then sets context state to GreenState
//        - GreenState:  prints "GO",   then sets context state to YellowState
//        - YellowState: prints "SLOW", then sets context state to RedState
//   3. Class TrafficLight (context):
//        - Holds current TrafficLightState (starts at RedState)
//        - setState(TrafficLightState)
//        - change(): delegates to current state's handle()
//
// Key concepts to understand:
//   - Each state knows what the NEXT state is — transitions live in the state, not the context
//   - Context delegates all behaviour to its current state
//   - Adding a new state means adding a new class — no modification to existing states
//   - Used in Nexus: CircuitBreakerState (CLOSED/OPEN/HALF_OPEN) — same concept
//   - Difference from Strategy: State transitions automatically; Strategy is switched by client
// =============================================================================

public class StatePattern {

    public interface TrafficLightState {
        void handle(TrafficLight context);
    }

    static class RedState implements TrafficLightState {
        @Override
        public void handle(TrafficLight context) {
            System.out.println("STOP");
            context.setState(new GreenState());
        }
    }

    static class GreenState implements TrafficLightState {
        @Override
        public void handle(TrafficLight context) {
            System.out.println("GO");
            context.setState(new YellowState());
        }
    }

    static class YellowState implements TrafficLightState {
        @Override
        public void handle(TrafficLight context) {
            System.out.println("SLOW");
            context.setState(new RedState());
        }
    }

    static class TrafficLight {
        private TrafficLightState state;

        TrafficLight() {
            this.state = new RedState();
        }

        void setState(TrafficLightState state) {
            this.state = state;
        }

        void change() {
            state.handle(this);
        }
    }

    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();
        for (int i = 0; i < 6; i++) {
            light.change();
        }
    }
}
