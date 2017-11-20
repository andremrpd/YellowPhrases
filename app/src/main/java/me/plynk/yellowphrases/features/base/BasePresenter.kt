package me.plynk.yellowphrases.features.base

interface BasePresenter<in V: BaseView>{
    fun attachView(view: V)
    fun detachView()
}
