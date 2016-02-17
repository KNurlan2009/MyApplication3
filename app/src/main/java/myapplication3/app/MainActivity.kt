package myapplication3.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*

class MainActivity : AppCompatActivity() , NavigationDrawerFragment.NavigationDrawerCallbacks, PullToRefreshListener {
    val LOGTAG : String = this.javaClass.simpleName

    override fun begin() {
        Log.d(LOGTAG, "MainActivity begin")
    }

    override fun run(y: Int) {
        Log.d(LOGTAG, "MainActivity run: " + y)
    }
    override fun cancel() {
        Log.d(LOGTAG, "MainActivity cancel")
    }
    override fun finishme() {
        Log.d(LOGTAG, "MainActivity finishme")
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    /**
     * Used to store the last screen title. For use in [.restoreActionBar].
     */
    private var mTitle: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LOGTAG", "pre create")
        setContentView(R.layout.activity_main)

        Log.d("LOGTAG", "create")
            mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment
        mTitle = title

        // Set up the drawer.
        mNavigationDrawerFragment!!.setUp(
                R.id.navigation_drawer,
                findViewById(R.id.drawer_layout) as DrawerLayout)
    }

    override fun onNavigationDrawerItemSelected(position: Int) {
        // update the main content by replacing fragments
        try {
            startActivity(Intent(this, javaClass<SecondActivity>()))
        } catch (e : Exception){
                e.printStackTrace()
        }
//        val fragmentManager = supportFragmentManager
//        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit()

        //
    }

    fun onSectionAttached(number: Int) {
        when (number) {
            1 -> mTitle = getString(R.string.title_section1)
            2 -> mTitle = getString(R.string.title_section2)
            3 -> mTitle = getString(R.string.title_section3)
        }
    }

    fun restoreActionBar() {
        val actionBar = supportActionBar
        actionBar.navigationMode = ActionBar.NAVIGATION_MODE_STANDARD
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.title = mTitle
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!mNavigationDrawerFragment!!.isDrawerOpen) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            menuInflater.inflate(R.menu.main, menu)
            restoreActionBar()
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_main, container, false)
            return rootView
        }

        override fun onAttach(activity: Activity?) {
            super.onAttach(activity)
            (activity as MainActivity).onSectionAttached(
                    arguments.getInt(ARG_SECTION_NUMBER))
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

}
