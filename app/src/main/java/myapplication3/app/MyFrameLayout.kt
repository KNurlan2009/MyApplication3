package myapplication3.app

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*

/**
 * Created by Nurlan on 09-Feb-16.
 */
open public class MyFrameLayout : LinearLayout {
    val LOGTAG : String = this.javaClass.simpleName
    var pointY = 0
    private var slideHandler : SlideHandler? = null
    var self : LinearLayout? = null
    var decorItem :TextView? = null
    var progressBar : ProgressBar? = null
    var decorLayout :RelativeLayout? = null
    var decorLayoutParam : LayoutParams? = null
    var updating = false
    var isMoving = false
    var canUpdate = true
    var resizeAnimation : ResizeAnimation? = null
    var closeAnimation : ResizeAnimation? = null
    var maxOpenedHeight = 120;
    var canStretch = true
    var added = false
    var myTimer : Timer = Timer()
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
        self = this
        val inflater :LayoutInflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
        val view : View= inflater.inflate(R.layout.myblind, this);
        this.decorItem = view.findViewById(R.id.decorItem) as TextView?
        this.progressBar = view.findViewById(R.id.progressBar) as ProgressBar?
        this.decorLayout = view.findViewById(R.id.decorLayout) as RelativeLayout?
        decorLayoutParam = this.decorLayout?.layoutParams as LayoutParams
        decorLayoutParam?.height = 0
        this.decorLayout?.layoutParams = decorLayoutParam
        //val loadMoreView : View= inflater.inflate(R.layout.myblind, this);
    }

    public fun updated(){
        Log.d(LOGTAG, "updated")
        decorItem!!.text = "Обновленно !"
        progressBar!!.visibility = View.GONE
        updating = false
//        closeAnimation = ResizeAnimation(decorLayout as View, 0, decorLayout!!.height)
//        closeAnimation?.duration = 1200
//        decorLayout?.startAnimation(closeAnimation)
        this.postDelayed(Runnable {
            canStretch = true
            Log.d(LOGTAG, "postDelayed run")
            decorItem!!.text = "Потяните чтобы обновить"
            if (!isMoving && (decorLayoutParam!!.height > 0)){
                Log.d(LOGTAG, "postDelayed isMoving false")
                decorLayoutParam?.height = 0
                canUpdate = true
                decorLayout?.layoutParams = decorLayoutParam
                decorLayout?.invalidate()
            }

            }, 1200)
        //myTimer.schedule(timerTask{}, 1200)

    }

    public fun isUpdated():Boolean{return updating}

    public fun setSlideHandler(slideHandler : SlideHandler){
        this.slideHandler = slideHandler
    }

    override public fun dispatchTouchEvent (ev: MotionEvent) : Boolean{
            when (ev.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    isMoving = false
                    canUpdate = true
                    Log.d(LOGTAG, "ACTION_DOWN x= " + ev.x + " y: " + ev.y)

                    if (!updating && slideHandler!!.checkCanDoRefresh()) {
                        pointY = ev.y.toInt()
                        decorItem!!.text = "Потяните чтобы обновить"
                        progressBar!!.visibility = View.GONE
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    var distance = ev.y.toInt() - pointY
                    isMoving = true

                    if (ev.y > 0 && distance > 0) {
                        Log.d(LOGTAG, "ACTION_MOVE")
                        if(!slideHandler!!.checkCanDoRefresh() && decorLayoutParam!!.height==0) {
                            pointY =  ev.y.toInt()
                            distance = ev.y.toInt() - pointY
                            Log.d(LOGTAG, "ACTION_MOVE @@@")
                        }

                        if (canStretch   && (slideHandler!!.checkCanDoRefresh() || decorLayoutParam!!.height > 0)) {
                            decorLayoutParam!!.height = distance
                            this.decorLayout?.layoutParams = decorLayoutParam
                            this.decorLayout?.invalidate()
                            Log.d(LOGTAG, "ACTION_MOVE 2222")
                        }
                        if (decorLayoutParam!!.height > maxOpenedHeight && distance>maxOpenedHeight  ) {
                            if (!updating && canUpdate){
                                decorItem!!.text = "Обновляется"
                                progressBar!!.visibility = View.VISIBLE
                                updating = true
                                canUpdate = false
                                slideHandler?.onRefreshBegin()
                                Log.d(LOGTAG, "ACTION_MOVE 3333333")
                            }
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    Log.d(LOGTAG, "ACTION_UP x= " + ev.x + " y: " + ev.y)
                    isMoving = false
                    onMotionInterrupt(ev)
                }
                MotionEvent.ACTION_CANCEL -> {
                    isMoving = false
                    Log.d(LOGTAG, "ACTION_CANCEL")
                    onMotionInterrupt(ev)
                }
            }

        var b = super.dispatchTouchEvent(ev)
        return b
    }

    private fun onMotionInterrupt(ev: MotionEvent) {
        var distance = ev.y.toInt() - pointY
        if (distance > 0) {
            if (!updating) {
                toIdleState()
            } else {
                if (canStretch) animateToOpenedState()
            }
        }
    }

    private fun animateToOpenedState() {
        if (decorLayoutParam!!.height >= maxOpenedHeight) {
            Log.d(LOGTAG, "animateToOpenedState")
            canStretch = false
            if (updating)Log.d(LOGTAG, "updating true") else Log.d(LOGTAG, "updating false")
            resizeAnimation = ResizeAnimation(decorLayout as View, if (updating)maxOpenedHeight else 0 , decorLayout!!.height)
            resizeAnimation?.duration = 1000
            decorLayout?.startAnimation(resizeAnimation)
        }
    }


    private fun toIdleState() {
        Log.d(LOGTAG, "toIdleState")
        decorLayoutParam!!.height = 0
        this.decorLayout?.layoutParams = decorLayoutParam
        decorItem!!.text = "Потяните чтобы обновить"
        progressBar!!.visibility = View.GONE
        this.decorLayout?.invalidate()
    }
}