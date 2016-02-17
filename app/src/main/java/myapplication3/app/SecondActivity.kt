package myapplication3.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Nurlan on 03-Feb-16.
 */
class SecondActivity : AppCompatActivity() , NavigationDrawerFragment.NavigationDrawerCallbacks, SlideHandler {
    override fun onLoadMoreBegin() {
    }
    override fun onRefreshBegin() {
        slideLayout!!.postDelayed(Runnable { runOnUiThread { slideLayout?.updated() } }, 1000*5)
    }

    override fun checkCanDoRefresh(): Boolean {
        return (my_recycler_view.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0
    }
    override fun checkCanLoadMore(): Boolean {
        return (my_recycler_view.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == adapter.itemCount-1
    }

    val LOGTAG : String = this.javaClass.simpleName
    var mLayoutManager = LinearLayoutManager(this);
    var adapter : MyAdapter = MyAdapter(Array(20, { i -> (i).toString() }))


    override fun onNavigationDrawerItemSelected(position: Int) {
//        Toast.makeText(this, "Button 1", Toast.LENGTH_LONG).show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        my_recycler_view.setHasFixedSize(true);
        Log.d(LOGTAG, "onCreate")
        // use a linear layout manager
        my_recycler_view.setLayoutManager(mLayoutManager);
        Log.d(LOGTAG, "setLayoutManager")
        my_recycler_view.setAdapter(adapter)

        slideLayout!!.setSlideHandler(this)
    }
}
