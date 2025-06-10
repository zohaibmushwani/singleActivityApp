package com.example.singleactivityapp

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Context.hasReadExternalStoragePermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}


enum class SlideDirection {
    UP, DOWN, LEFT, RIGHT, NONE
}

fun View.animatedVisible(
    visible: Boolean,
    duration: Long = 300,
    slideDirection: SlideDirection = SlideDirection.NONE,
    slideDistance: Float? = null
) {
    if (visible == isVisible) return

    val distance = slideDistance ?: when (slideDirection) {
        SlideDirection.UP, SlideDirection.DOWN -> height.toFloat()
        SlideDirection.LEFT, SlideDirection.RIGHT -> width.toFloat()
        SlideDirection.NONE -> 0f
    }

    if (visible) {
        isVisible = true
        alpha = 0f
        when (slideDirection) {
            SlideDirection.UP -> translationY = distance
            SlideDirection.DOWN -> translationY = -distance
            SlideDirection.LEFT -> translationX = distance
            SlideDirection.RIGHT -> translationX = -distance
            SlideDirection.NONE -> Unit
        }
        animate()
            .alpha(1f)
            .translationX(if (slideDirection == SlideDirection.LEFT || slideDirection == SlideDirection.RIGHT) 0f else translationX)
            .translationY(if (slideDirection == SlideDirection.UP || slideDirection == SlideDirection.DOWN) 0f else translationY)
            .setDuration(duration)
            .start()
    } else {
        animate()
            .alpha(0f)
            .translationX(when (slideDirection) {
                SlideDirection.LEFT -> -distance
                SlideDirection.RIGHT -> distance
                else -> translationX
            })
            .translationY(when (slideDirection) {
                SlideDirection.UP -> -distance
                SlideDirection.DOWN -> distance
                else -> translationY
            })
            .setDuration(duration)
            .withEndAction { isVisible = false }
            .start()
    }
}

