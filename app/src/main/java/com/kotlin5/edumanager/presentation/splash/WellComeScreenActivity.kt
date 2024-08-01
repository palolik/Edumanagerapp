package bd.ossl.eps.presentation.splash

import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import bd.ossl.eps.R
import bd.ossl.eps.databinding.ActivityWellComeScreenBinding
import bd.ossl.eps.presentation.auth.LoginActivity
import ossl.eps.common.extension.gone
import ossl.eps.common.extension.navigateTo
import ossl.eps.common.extension.show
import bd.ossl.eps.presentation.splash.adapter.SliderAdapter
import bd.ossl.eps.presentation.splash.model.SliderData
import bd.ossl.eps.presentation.splash.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import ossl.eps.design.core.CoreBaseActivity

@AndroidEntryPoint
class WellComeScreenActivity : CoreBaseActivity<ActivityWellComeScreenBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override fun getViewBinding(): ActivityWellComeScreenBinding {
        return ActivityWellComeScreenBinding.inflate(layoutInflater)
    }

    override fun setupUI() {

        binding.idBtnSkip.setOnClickListener {
            viewModel.setWellComeScreenVisit()
            navigateTo(LoginActivity::class.java, clearTask = true)
        }

        binding.okayBtn.setOnClickListener {
            if (binding.idViewPager.currentItem < 2)
                binding.idViewPager.currentItem += 1
            else {
                viewModel.setWellComeScreenVisit()
                navigateTo(LoginActivity::class.java, clearTask = true)
            }
        }

        val sliderData = arrayListOf(
            SliderData("Instant", "Fund Transfer", R.drawable.splash1),
            SliderData("Hassle free", "Bill and Fees payment", R.drawable.splash2),
            SliderData("Welcome to", "EPS Lifestyle", R.drawable.splash3)
        )

        binding.idViewPager.adapter = SliderAdapter(this, sliderData)
        binding.idViewPager.addOnPageChangeListener(viewListener)

        binding.indicator.setViewPager(binding.idViewPager)
    }


    private var viewListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0, 1 -> {
                        binding.okayBtn.text = "Next"
                        binding.idBtnSkip.show()
                    }

                    else -> {
                        binding.okayBtn.text = "Get Started"
                        binding.idBtnSkip.gone()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        }

}