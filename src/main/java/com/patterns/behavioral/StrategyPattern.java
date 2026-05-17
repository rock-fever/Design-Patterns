package com.patterns.behavioral;

// =============================================================================
// STRATEGY PATTERN
// =============================================================================
// Intent:
//   Define a family of algorithms, encapsulate each one, and make them interchangeable.
//   Strategy lets the algorithm vary independently from clients that use it.
//
// When to use:
//   - Multiple variants of an algorithm exist and you want to switch between them
//   - You want to eliminate conditionals that select behaviour (if/switch on type)
//
// What to implement:
//   1. Interface SortStrategy with: void sort(int[] data)
//   2. Three strategies: BubbleSortStrategy, QuickSortStrategy, MergeSortStrategy
//      (implementations can be simplified/faked — just print which algorithm is running)
//   3. Class Sorter:
//        - Takes a SortStrategy in constructor (or via setter for runtime switching)
//        - sort(int[] data) delegates to the strategy
//
// Key concepts to understand:
//   - Strategy is injected — the client doesn't instantiate it
//   - Switching strategy at runtime changes behaviour without touching Sorter
//   - Eliminates: if (type == BUBBLE) { ... } else if (type == QUICK) { ... }
//   - Used in Nexus: AuthStrategyFactory, RateLimitStrategyFactory
// =============================================================================

public class StrategyPattern {

    interface SortStrategy {
        void sort(int[] data);
    }

    static class BubbleSortStrategy implements SortStrategy {
        @Override
        public void sort(int[] data) {
            System.out.println("BubbleSortStrategy: sorting " + data.length + " elements (O(n²))");
        }
    }

    static class QuickSortStrategy implements SortStrategy {
        @Override
        public void sort(int[] data) {
            System.out.println("QuickSortStrategy: sorting " + data.length + " elements (O(n log n) avg)");
        }
    }

    static class MergeSortStrategy implements SortStrategy {
        @Override
        public void sort(int[] data) {
            System.out.println("MergeSortStrategy: sorting " + data.length + " elements (O(n log n) stable)");
        }
    }

    static class Sorter {
        private SortStrategy strategy;

        Sorter(SortStrategy strategy) {
            this.strategy = strategy;
        }

        void setStrategy(SortStrategy strategy) {
            this.strategy = strategy;
        }

        void sort(int[] data) {
            strategy.sort(data);
        }
    }

    public static void main(String[] args) {
        int[] data = {5, 3, 8, 1, 9, 2};

        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.sort(data);

        sorter.setStrategy(new QuickSortStrategy());
        sorter.sort(data);

        sorter.setStrategy(new MergeSortStrategy());
        sorter.sort(data);
    }
}
