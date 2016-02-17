package myapplication3.app

/**
 * Created by Nurlan on 11-Feb-16.
 */
open public interface SlideHandler {


        abstract fun checkCanDoRefresh(): Boolean
        abstract fun checkCanLoadMore(): Boolean
        /**
         * When refresh begin

         * @param frame
         */
        abstract fun onRefreshBegin()
        abstract fun onLoadMoreBegin()
    }