package myapplication3.app

/**
 * Created by Nurlan on 09-Feb-16.
 */
public interface PullToRefreshListener {
    fun begin()
    fun run(y : Int)
    fun finishme()
    fun cancel()
}
