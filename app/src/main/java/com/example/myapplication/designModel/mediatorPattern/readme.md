### 一、什么是中介者模式（Mediator Pattern）

中介者模式是一种行为型设计模式。它通过引入一个中介对象，来封装一组对象之间的交互，使对象之间不再显式地相互引用，从而降低它们之间的耦合度。对象之间的通信都通过中介者进行，便于集中管理和维护。

**适用场景：**
- 系统中对象之间存在复杂的引用关系，导致依赖混乱。
- 希望通过一个中间类来统一管理对象之间的交互。

---

### 二、结构图

```
Colleague1      Colleague2
     \             /
      \           /
       \         /
        Mediator
```

---

### 三、简单 Java Demo

#### 1. 抽象中介者

```java
public interface Mediator {
    void send(String message, Colleague colleague);
}
```

#### 2. 抽象同事类

```java
public abstract class Colleague {
    protected Mediator mediator;
    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
```

#### 3. 具体同事类

```java
public class ColleagueA extends Colleague {
    public ColleagueA(Mediator mediator) {
        super(mediator);
    }
    public void send(String message) {
        mediator.send(message, this);
    }
    public void notify(String message) {
        System.out.println("ColleagueA received: " + message);
    }
}

public class ColleagueB extends Colleague {
    public ColleagueB(Mediator mediator) {
        super(mediator);
    }
    public void send(String message) {
        mediator.send(message, this);
    }
    public void notify(String message) {
        System.out.println("ColleagueB received: " + message);
    }
}
```

#### 4. 具体中介者

```java
public class ConcreteMediator implements Mediator {
    private ColleagueA colleagueA;
    private ColleagueB colleagueB;

    public void setColleagueA(ColleagueA colleagueA) {
        this.colleagueA = colleagueA;
    }
    public void setColleagueB(ColleagueB colleagueB) {
        this.colleagueB = colleagueB;
    }

    @Override
    public void send(String message, Colleague colleague) {
        if (colleague == colleagueA) {
            colleagueB.notify(message);
        } else {
            colleagueA.notify(message);
        }
    }
}
```

#### 5. 测试代码

```java
public class MediatorDemo {
    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();
        ColleagueA a = new ColleagueA(mediator);
        ColleagueB b = new ColleagueB(mediator);

        mediator.setColleagueA(a);
        mediator.setColleagueB(b);

        a.send("Hello, B!");
        b.send("Hi, A!");
    }
}
```

#### 6. 输出结果

```
ColleagueB received: Hello, B!
ColleagueA received: Hi, A!
```

---

### 四、总结

- 中介者模式将对象之间的复杂关系转移到中介者对象中，简化了对象之间的依赖。
- 适合用于对象之间交互复杂、需要解耦的场景。

如需更复杂的例子或有其他设计模式问题，欢迎继续提问！