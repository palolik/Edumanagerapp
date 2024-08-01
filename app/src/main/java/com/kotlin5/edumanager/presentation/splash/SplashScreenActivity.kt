package bd.ossl.eps.presentation.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import bd.ossl.eps.presentation.splash.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import ossl.eps.common.extension.DataObserver
import ossl.eps.common.extension.DataResource
import ossl.eps.common.extension.Status
import ossl.eps.common.extension.navigateTo
import ossl.eps.common.extension.showToast
import ossl.eps.design.core.CoreBaseActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : CoreBaseActivity<ActivitySplashScreenBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    //observers
    private val shouldNavigateObserver = DataObserver { onShouldNavigateResponse(it) }

    override fun getViewBinding(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        viewModel.shouldNavigate()
    }

    override fun setupObserver() {
        viewModel.shouldNavigateLiveData.observe(this, shouldNavigateObserver)
    }

    private fun onShouldNavigateResponse(response: DataResource<*>) {
        when (response.status) {
            Status.SUCCESS -> {
                val data = response.data as? Boolean ?: return
                if (data) {
                    navigateTo(LoginActivity::class.java, clearTask = true)
                }
                else {
                    navigateTo(WellComeScreenActivity::class.java, clearTask = true)
                }
            }

            Status.ERROR -> {
                response.message?.showToast(this)
            }

            Status.LOADING -> {
            }
        }
    }
}