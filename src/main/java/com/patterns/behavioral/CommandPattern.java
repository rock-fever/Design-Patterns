package com.patterns.behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

// =============================================================================
// COMMAND PATTERN
// =============================================================================
// Intent:
//   Encapsulate a request as an object, allowing you to parameterise clients
//   with different requests, queue them, log them, or support undo.
//
// When to use:
//   - You want to parameterise operations (pass actions as arguments)
//   - You need undo/redo functionality
//   - You want to queue, schedule, or log operations
//
// What to implement:
//   1. Interface Command with: execute() and undo()
//   2. Receiver class TextEditor with:
//        - String content (starts empty)
//        - insertText(String text), deleteText(int chars)
//   3. Concrete commands:
//        - InsertCommand(TextEditor, String text): execute appends text, undo removes it
//        - DeleteCommand(TextEditor, int chars): execute removes last N chars, undo restores them
//   4. Class CommandHistory:
//        - Deque<Command> history
//        - execute(Command): runs command and pushes to history
//        - undo(): pops last command and calls undo()
//
// Key concepts to understand:
//   - Command decouples the sender (who triggers) from the receiver (who executes)
//   - Undo is possible because the command remembers what it did
//   - Used in Nexus: Kafka event records are immutable commands (no undo, but same encapsulation idea)
// =============================================================================

public class CommandPattern {

    public interface Command {
        void execute();
        void undo();
    }

    static class TextEditor {
        String content = "";

        void insertText(String text) {
            content += text;
        }

        void deleteText(int chars) {
            content = content.substring(0, content.length() - chars);
        }
    }

    static class InsertCommand implements Command {
        private final TextEditor editor;
        private final String text;

        InsertCommand(TextEditor editor, String text) {
            this.editor = editor;
            this.text = text;
        }

        @Override
        public void execute() {
            editor.insertText(text);
        }

        @Override
        public void undo() {
            editor.deleteText(text.length());
        }
    }

    static class DeleteCommand implements Command {
        private final TextEditor editor;
        private final int chars;
        private String deleted;  // remembered for undo

        DeleteCommand(TextEditor editor, int chars) {
            this.editor = editor;
            this.chars = chars;
        }

        @Override
        public void execute() {
            deleted = editor.content.substring(editor.content.length() - chars);
            editor.deleteText(chars);
        }

        @Override
        public void undo() {
            editor.insertText(deleted);
        }
    }

    static class CommandHistory {
        private final Deque<Command> history = new ArrayDeque<>();

        void execute(Command command) {
            command.execute();
            history.push(command);
        }

        void undo() {
            if (!history.isEmpty()) {
                history.pop().undo();
            }
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        history.execute(new InsertCommand(editor, "Hello"));
        System.out.println(editor.content);   // Hello

        history.execute(new InsertCommand(editor, " World"));
        System.out.println(editor.content);   // Hello World

        history.execute(new DeleteCommand(editor, 5));
        System.out.println(editor.content);   // Hello (deleted " World" — 6 chars, but 5 here removes "World")

        history.undo();
        System.out.println(editor.content);   // Hello World (undo delete)

        history.undo();
        System.out.println(editor.content);   // Hello (undo insert " World")
    }
}
