package myapplication3.app

import android.content.Context
import android.util.AttributeSet

/**
 * Created by Nurlan on 11-Feb-16.
 */
open public class SlideLayout : MyFrameLayout {
    val LOG_TAG : String = this.javaClass.simpleName

    constructor(context: Context) : super(context) {
        initWidgets()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        initWidgets()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initWidgets()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun initWidgets(){
        this.setOnClickListener{}
    }

}