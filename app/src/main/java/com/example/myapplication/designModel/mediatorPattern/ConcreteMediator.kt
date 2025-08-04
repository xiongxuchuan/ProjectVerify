package com.example.myapplication.designModel.mediatorPattern

/**
 * 具体中介者
 * */
class ConcreteMediator : Mediator {
    lateinit var colleagueA: ColleagueA
    lateinit var colleagueB: ColleagueB

    override fun send(message: String, colleague: Colleague) {
        if (colleague == colleagueA) {
            colleagueB.notify(message)
        } else {
            colleagueA.notify(message)
        }
    }
}