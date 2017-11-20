package me.plynk.yellowphrases.extensions

import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ViewGroup.showProgressBar(): ProgressBar {
    var progressBar = findViewWithTag<ProgressBar>("mainProgressBar")

    if (progressBar == null){
        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(150, 150)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        addView(progressBar, params)
        progressBar.tag = "mainProgressBar"
    }

    progressBar.visibility = View.VISIBLE

    return progressBar
}

fun ViewGroup.dismissProgressBar(){
    val progressBar = findViewWithTag<ProgressBar>("mainProgressBar")
    progressBar?.visibility = View.GONE
}

fun ImageView.setTopCropMatrix(){
    val matrix: Matrix = imageMatrix
    val imageWidth: Float = drawable.intrinsicWidth.toFloat()
    val screenWidth: Float = resources.displayMetrics.widthPixels.toFloat()
    val scaleRatio: Float = screenWidth/imageWidth
    matrix.postScale(scaleRatio, scaleRatio)
    imageMatrix = matrix
}