package com.patterns.creational;


// =============================================================================
// ABSTRACT FACTORY PATTERN
// =============================================================================
// Intent:
//   Provide an interface for creating families of related objects without
//   specifying their concrete classes.
//
// When to use:
//   - You need to create multiple related objects that must be used together
//   - You want to switch between families of objects (e.g. themes, platforms, providers)
//
// What to implement:
//   1. Two product interfaces: Button (click()) and Checkbox (check())
//   2. Two families of concrete products:
//        - WindowsButton, WindowsCheckbox
//        - MacButton, MacCheckbox
//   3. Abstract factory interface UIFactory with:
//        - createButton()
//        - createCheckbox()
//   4. Two concrete factories: WindowsUIFactory, MacUIFactory
//   5. A class App that takes a UIFactory and uses it — never references concrete classes
//
// Key concepts to understand:
//   - The factory produces a *family* of objects (vs Factory Method: one object)
//   - Client code (App) is fully decoupled from concrete product classes
//   - Switching the factory switches the entire family at once
//   - Difference from Factory Method: Abstract Factory uses composition,
//     Factory Method uses inheritance
// =============================================================================

public class AbstractFactoryPattern {

    public interface Button {
        void click();
    }

    public interface CheckBox {
        void check();
    }

    static class WindowsButton implements Button {

        @Override
        public void click() {
            System.out.println("Windows button has been clicked!");
        }
    }

    static class WindowsCheckbox implements CheckBox {

        @Override
        public void check() {
            System.out.println("Windows checkbox has been checked!");
        }        
    }

    static class MacButton implements Button {
        @Override
        public void click() {
            System.out.println("Mac button has been clicked!");
        }
    }

    static class MacCheckbox implements CheckBox {

        @Override
        public void check() {
            System.out.println("Mac checkbox has been checked!");
        }        
    }

    public interface UIFactory {
        Button createButton();
        CheckBox createCheckBox();
    }

    static class WindowsUIFactory implements UIFactory {

        @Override
        public Button createButton() {
            return new WindowsButton();
        }

        @Override
        public CheckBox createCheckBox() {
            return new WindowsCheckbox();
        }
    }

    static class MacUIFactory implements UIFactory {

        @Override
        public Button createButton() {
            return new MacButton();
        }

        @Override
        public CheckBox createCheckBox() {
            return new MacCheckbox();
        }
    }

    static class App {
        private final Button button;
        private final CheckBox checkbox;

        App(UIFactory factory) {
            this.button = factory.createButton();
            this.checkbox = factory.createCheckBox();
        }

        void render() {
            button.click();
            checkbox.check();
        }
    }

    public static void main(String[] args) {
        App windowsApp = new App(new WindowsUIFactory());
        windowsApp.render();

        App macApp = new App(new MacUIFactory());
        macApp.render();
    }
}
