package me.plynk.yellowphrases.features.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.LongSparseArray
import android.view.View
import me.plynk.yellowphrases.App
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.di.components.ConfigPersistentComponent
import me.plynk.yellowphrases.di.components.DaggerConfigPersistentComponent
import me.plynk.yellowphrases.di.components.FragmentComponent
import me.plynk.yellowphrases.di.modules.FragmentModule
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>>: Fragment(), BaseView {

    protected var baseActivity: BaseActivity? = null
        private set
    protected var fragmentComponent: FragmentComponent? = null
        private set
    private var fragmentId: Long = 0

    protected abstract var presenter: T
    @Inject protected lateinit var remoteRepository: RemoteRepository

    companion object {
        private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val componentsArray = LongSparseArray<ConfigPersistentComponent>()
        private val NEXT_ID = AtomicLong(0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent

        if (componentsArray.get(fragmentId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(App.appplicationComponent)
                    .build()
            componentsArray.put(fragmentId, configPersistentComponent)
        } else {
            configPersistentComponent = componentsArray.get(fragmentId)
        }

        fragmentComponent = configPersistentComponent.plusFragmentComponent(FragmentModule(this))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun onDetach(){
        dismissLoading()
        baseActivity = null
        super.onDetach()
    }

    override fun onDestroy() {
        if (!activity?.isChangingConfigurations!!) {
            componentsArray.remove(fragmentId)
        }
        presenter.detachView()
        super.onDestroy()
    }

    override fun showErrorMsg(message: String?) {
        baseActivity?.showErrorMsg(message)
    }

    override fun showTryAgainError(message: String?, onClickListener: View.OnClickListener?) {
        baseActivity?.showTryAgainError(message, onClickListener)
    }

    override fun showLoading() {
        baseActivity?.showLoading()
    }

    override fun dismissLoading() {
        baseActivity?.dismissLoading()
    }


}
