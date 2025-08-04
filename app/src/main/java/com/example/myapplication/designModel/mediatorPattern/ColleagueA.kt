package com.example.myapplication.designModel.mediatorPattern

/**
 * 具体同事类A
 *
 * */
class ColleagueA(mediator: Mediator) : Colleague(mediator) {
    fun send(message: String) {
        mediator.send(message, this)
    }

    override fun notify(message: String) {
        println("ColleagueA received: $message")
    }
}