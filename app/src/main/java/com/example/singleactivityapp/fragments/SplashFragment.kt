package com.example.singleactivityapp.fragments

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.singleactivityapp.R
import com.example.singleactivityapp.hasReadExternalStoragePermission
import com.example.singleactivityapp.navigateTo

class SplashFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            navigateToNextFragment()
        } else {
            handler.postDelayed({
                navigateToNextFragment()
            }, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            inflater.inflate(R.layout.fragment_splash, container, false)
        } else {
            null
        }
    }

    private fun navigateToNextFragment() {
        if (hasStoragePermissions()) {
            navigateTo(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        } else {
            navigateTo(SplashFragmentDirections.actionSplashFragmentToPermissionsFragment())
        }
    }

    private fun hasStoragePermissions(): Boolean {
        return requireContext().hasReadExternalStoragePermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}

