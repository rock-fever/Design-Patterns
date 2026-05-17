# Java Design Patterns

A hands-on reference implementation of the classic Gang of Four design patterns in Java.

## Structure

```
src/main/java/com/patterns/
├── creational/    # How objects are created
├── structural/    # How objects are composed
└── behavioral/    # How objects communicate
```

## Patterns

### Structural
| Pattern | Status | Description |
|---------|--------|-------------|
| Adapter | ✅ | Convert incompatible interfaces |
| Composite | ✅ | Tree structures, treat leaf and branch uniformly |
| Decorator | ✅ | Add behaviour to objects at runtime |
| Facade | ✅ | Simplified interface to a subsystem |
| Proxy | ✅ | Control access to an object (caching, lazy init) |

### Creational
| Pattern | Status | Description |
|---------|--------|-------------|
| AbstractFactory | ✅ | Create families of related objects |
| Builder | ✅ | Construct complex objects step by step |
| FactoryMethod | ✅ | Delegate instantiation to subclasses |
| Prototype | ✅ | Clone existing objects |
| Singleton | ✅ | Ensure a single instance |

### Behavioral
| Pattern | Status | Description |
|---------|--------|-------------|
| ChainOfResponsibility | ✅ | Pass request along a chain of handlers |
| Command | ✅ | Encapsulate a request as an object |
| Observer | ✅ | Notify dependents on state change |
| State | ✅ | Alter behaviour when internal state changes |
| Strategy | ✅ | Swap algorithms at runtime |
| TemplateMethod | ✅ | Define skeleton of algorithm, defer steps to subclasses |

## Running a Pattern

```bash
javac -d out src/main/java/com/patterns/structural/CompositePattern.java
java -cp out com.patterns.structural.CompositePattern
```
