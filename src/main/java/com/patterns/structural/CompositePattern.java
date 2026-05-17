package com.patterns.structural;

import java.util.ArrayList;
import java.util.List;

// =============================================================================
// COMPOSITE PATTERN
// =============================================================================
// Intent:
//   Compose objects into tree structures to represent part-whole hierarchies.
//   Lets clients treat individual objects and compositions uniformly.
//
// When to use:
//   - You have a tree structure (file system, UI components, org chart)
//   - Client code should not care whether it's dealing with a leaf or a branch
//
// What to implement:
//   1. Interface FileSystemComponent with:
//        - String getName()
//        - void print(String indent)
//   2. Leaf class File implements FileSystemComponent:
//        - print() just prints indent + name
//   3. Composite class Directory implements FileSystemComponent:
//        - Contains a List<FileSystemComponent> children
//        - add(FileSystemComponent) and remove(FileSystemComponent)
//        - print() prints its own name, then calls print() on each child (with more indent)
//
// Key concepts to understand:
//   - Both File and Directory implement the same interface — client treats them the same
//   - Recursion is natural: Directory.print() calls print() on children, which may be Directories
//   - The tree can be arbitrarily deep
//   - Difference from Decorator: Composite is about tree structure; Decorator is about adding behaviour
// =============================================================================

public class CompositePattern {

    interface FileSystemComponent {
        String getName();
        void print(String indent);
    }

    static class File implements FileSystemComponent {
        private final String name;

        File(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + name);
        }
    }

    static class Directory implements FileSystemComponent {
        private final String name;
        private final List<FileSystemComponent> children = new ArrayList<>();

        Directory(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public void add(FileSystemComponent component) {
            children.add(component);
        }

        public void remove(FileSystemComponent component) {
            children.remove(component);
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + name + "/");
            for (FileSystemComponent child : children) {
                child.print(indent + "  ");
            }
        }
    }

    public static void main(String[] args) {
        Directory src = new Directory("src");
        src.add(new File("Main.java"));
        src.add(new File("Utils.java"));

        Directory root = new Directory("root");
        root.add(new File("file1.txt"));
        root.add(src);
        root.add(new File("README.md"));

        root.print("");
        // Output:
        // root/
        //   file1.txt
        //   src/
        //     Main.java
        //     Utils.java
        //   README.md
    }
}
