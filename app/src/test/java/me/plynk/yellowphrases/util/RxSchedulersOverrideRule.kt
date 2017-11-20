package me.plynk.yellowphrases.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

class RxSchedulersOverrideRule : TestRule {

    private val SCHEDULER_INSTANCE = Schedulers.trampoline()
    private val mSchedulerFunction = Function<Scheduler, Scheduler> { SCHEDULER_INSTANCE }
    private val mSchedulerFunctionLazy = Function<Callable<Scheduler>, Scheduler> { SCHEDULER_INSTANCE }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mSchedulerFunctionLazy)

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(mSchedulerFunction)
                RxJavaPlugins.setNewThreadSchedulerHandler(mSchedulerFunction)
                RxJavaPlugins.setComputationSchedulerHandler(mSchedulerFunction)

                base.evaluate()

                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()

            }
        }
    }
}