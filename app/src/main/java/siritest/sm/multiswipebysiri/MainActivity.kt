package siritest.sm.multiswipebysiri

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import siritest.sm.multiswipebysiri.base.BaseListFragment
import siritest.sm.multiswipebysiri.common.ListBottomFragmentType
import siritest.sm.multiswipebysiri.common.currentBottomListFragmentType
import siritest.sm.multiswipebysiri.fragment.GridFragment
import siritest.sm.multiswipebysiri.util.Logger
import siritest.sm.multiswipebysiri.datasource.VehicleTypeRepository
import siritest.sm.multiswipebysiri.fragment.HorizontalFragment
import siritest.sm.multiswipebysiri.fragment.LogFragment
import siritest.sm.multiswipebysiri.fragment.VerticalFragment

class MainActivity : AppCompatActivity() {

    private var logButtonTextView: TextView? = null
    private var logButtonLayout: FrameLayout? = null
    private var fab: FloatingActionButton? = null
    private var bottomNavigation: BottomNavigationView? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (tryNavigateToListFragment(item.itemId))
            return@OnNavigationItemSelectedListener true

        false
    }

    private val onLogButtonClickedListener = View.OnClickListener {
        navigateToLogFragment()
    }

    private val onLogUpdatedListener = object: Logger.OnLogUpdateListener {
        override fun onLogUpdated() = refreshLogButtonText()
    }

    private val onFabClickedListener = View.OnClickListener {
        // When in the log fragment, the FAB clears the log; when in a list fragment, it adds an item
        if (isLogFragmentOpen())
            Logger.reset()
        else
            VehicleTypeRepository.getInstance().generateNewItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.navigationBarColor = Color.BLACK

        setupLog()
        setupBottomNavigation()
        setupFab()
        refreshLogButtonText()
        navigateToListFragment()
    }

    private fun setupLog() {
        // Find log-related views
        logButtonLayout = findViewById(R.id.see_log_button)
        logButtonTextView = findViewById(R.id.see_log_button_text)

        // Initialise log and subscribe to log changes
        Logger.init(onLogUpdatedListener)

        // If the user clicks on the log button, we open the log fragment
        logButtonLayout?.setOnClickListener(onLogButtonClickedListener)
    }

    private fun setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.navigation)
        bottomNavigation?.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupFab() {
        fab = findViewById(R.id.fab)
        fab?.setOnClickListener(onFabClickedListener)
    }

    private fun refreshLogButtonText() {
        val numItemsOnLog = Logger.instance?.messages?.size ?: 0
        logButtonTextView?.text = getString(R.string.seeLogMessagesTitle, numItemsOnLog)
    }

    private fun tryNavigateToListFragment(itemId: Int): Boolean {
        val listFragmentType: ListBottomFragmentType? = when (itemId) {
            R.id.navigation_vertical_list -> ListBottomFragmentType.VERTICAL
            R.id.navigation_horizontal_list -> ListBottomFragmentType.HORIZONTAL
            R.id.navigation_grid_list -> ListBottomFragmentType.GRID
            else -> null
        }

        if (listFragmentType != null && (listFragmentType != currentBottomListFragmentType || isLogFragmentOpen())) {
            navigateToListFragment(listFragmentType)

            return true
        }

        return false
    }

    private fun navigateToListFragment(listFragmentType: ListBottomFragmentType = currentBottomListFragmentType) {
        currentBottomListFragmentType = listFragmentType

        val fragment: BaseListFragment = when (listFragmentType) {
            ListBottomFragmentType.GRID -> GridFragment.newInstance()
            ListBottomFragmentType.VERTICAL -> VerticalFragment.newInstance()
            ListBottomFragmentType.HORIZONTAL -> HorizontalFragment.newInstance()

        }
        replaceFragment(fragment, listFragmentType.tag)
        onNavigatedToListFragment()
    }

    private fun navigateToLogFragment() {
        replaceFragment(LogFragment.newInstance(), LogFragment.TAG)
        onNavigatedToLogFragment()
    }

    private fun onNavigatedToListFragment() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
        logButtonLayout?.visibility = View.VISIBLE
        fab?.setImageDrawable(AppCompatResources.getDrawable(applicationContext, R.drawable.ic_add_item))
    }

    private fun onNavigatedToLogFragment() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        logButtonLayout?.visibility = View.GONE
        fab?.setImageDrawable(AppCompatResources.getDrawable(applicationContext, R.drawable.ic_delete_item))
    }

    private fun isLogFragmentOpen() = supportFragmentManager.findFragmentByTag(LogFragment.TAG) != null

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, fragment, tag)
        }.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (isLogFragmentOpen()) {
                    navigateToListFragment()

                    return true
                }

                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (isLogFragmentOpen())
            navigateToListFragment()
        else
            super.onBackPressed()
    }
}