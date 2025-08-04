### 一、策略模式（Strategy Pattern）简介

策略模式是一种行为型设计模式。它定义了一系列算法（策略），并将每个算法封装起来，使它们可以互换。策略模式让算法的变化独立于使用算法的客户端。

**适用场景：**
- 需要在运行时动态切换算法或行为。
- 多个类只在行为上略有不同，可以通过封装算法消除重复代码。

**优点：**
- 算法可以自由切换。
- 避免多重条件语句（如 if-else 或 switch）。
- 扩展性好，新增策略很方便。

---

### 二、Kotlin 策略模式 Demo

#### 1. 定义策略接口

```kotlin
interface PaymentStrategy {
    fun pay(amount: Double)
}
```

#### 2. 实现具体策略

```kotlin
class CreditCardPayment : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paid $$amount using Credit Card.")
    }
}

class PaypalPayment : PaymentStrategy {
    override fun pay(amount: Double) {
        println("Paid $$amount using PayPal.")
    }
}
```

#### 3. 上下文类（使用策略）

```kotlin
class ShoppingCart(private var paymentStrategy: PaymentStrategy) {
    fun setPaymentStrategy(strategy: PaymentStrategy) {
        paymentStrategy = strategy
    }

    fun checkout(amount: Double) {
        paymentStrategy.pay(amount)
    }
}
```

#### 4. 测试代码

```kotlin
fun main() {
    val cart = ShoppingCart(CreditCardPayment())
    cart.checkout(100.0) // Paid $100.0 using Credit Card.

    cart.setPaymentStrategy(PaypalPayment())
    cart.checkout(200.0) // Paid $200.0 using PayPal.
}
```

---

### 三、输出结果

```
Paid $100.0 using Credit Card.
Paid $200.0 using PayPal.
```

---

### 四、总结

- 策略模式将算法的定义和使用分离，便于扩展和维护。
- 在 Kotlin 中，策略接口可以用接口、抽象类，甚至函数类型（高阶函数）实现，灵活性更高。

如需更复杂的策略模式用法或其他设计模式示例，欢迎继续提问！