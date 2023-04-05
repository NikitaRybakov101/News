package com.example.news.ui.splashScreenFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.SplashScreenBinding
import com.example.news.ui.baseView.BaseViewBindingFragment
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseViewBindingFragment<SplashScreenBinding>(SplashScreenBinding::inflate) {

    companion object {
        private const val URL_FONT = "null"
        private const val TEXT_SIZE_APP_NAME = 110f
        private const val TIME_ANIMATION_MILLIS = 5500L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_MainNewsFragment)
        }, TIME_ANIMATION_MILLIS)

        uiScope.launch {
            loadingSplash()
        }
    }

    private fun loadingSplash() {
        binding.customTextView.setText(getString(R.string.App_name_splash_screen),URL_FONT,TEXT_SIZE_APP_NAME)
    }
}