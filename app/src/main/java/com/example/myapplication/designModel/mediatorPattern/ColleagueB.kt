package com.example.myapplication.designModel.mediatorPattern

/**
 * 具体同事类B
 * */
class ColleagueB(mediator: Mediator) : Colleague(mediator) {
    fun send(message: String) {
        mediator.send(message, this)
    }

    override fun notify(message: String) {
        println("ColleagueB received: $message")
    }
}