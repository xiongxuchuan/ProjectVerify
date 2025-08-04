package com.example.myapplication.designModel.mediatorPattern

/**
 * 抽象中介者
 * */
interface Mediator {
    fun send(message: String, colleague: Colleague)
}