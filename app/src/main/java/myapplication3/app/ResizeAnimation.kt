package myapplication3.app

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Created by Nurlan on 11-Feb-16.
 */
public class ResizeAnimation(internal var view: View, internal val targetHeight: Int, internal var startHeight: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = ((targetHeight - startHeight) * interpolatedTime + startHeight).toInt()
        view.layoutParams.height = newHeight
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}
