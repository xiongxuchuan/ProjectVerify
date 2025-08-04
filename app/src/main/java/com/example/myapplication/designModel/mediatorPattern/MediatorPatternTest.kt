package com.example.myapplication.designModel.mediatorPattern

fun main() {
    val mediator = ConcreteMediator()
    val a = ColleagueA(mediator)
    val b = ColleagueB(mediator)

    mediator.colleagueA = a
    mediator.colleagueB = b

    a.send("Hello, B!")
    b.send("Hi, A!")
}