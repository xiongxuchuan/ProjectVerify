package com.example.myapplication.designModel.mediatorPattern

/**
 * 抽象同事类
 * */
abstract class Colleague(val mediator: Mediator) {
    abstract fun notify(message: String)
}