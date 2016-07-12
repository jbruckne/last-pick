package joebruckner.lastpick.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import joebruckner.lastpick.*
import joebruckner.lastpick.ui.about.AboutActivity
import joebruckner.lastpick.ui.common.BaseActivity
import joebruckner.lastpick.ui.movie.MovieActivity
import jonathanfinerty.once.Once
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

class HomeActivity : BaseActivity() {
    override val layoutId = R.layout.activity_home
    override var menuId = R.menu.menu_home

    val drawerLayout by lazy { find<DrawerLayout>(R.id.drawer_layout) }
    val navigationView by lazy { find<NavigationView>(R.id.navigation_view) }

    var lastSelectedItem = R.id.action_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Once.beenDone(Once.THIS_APP_INSTALL, "onboarding")) {
            startActivity(Intent(this, IntroActivity::class.java))
            Once.markDone("onboarding")
        }

        replaceFrame(R.id.frame, LandingFragment(), false)
        setupNavDrawer()

        if (!Once.beenDone(Once.THIS_APP_VERSION, "showcase")) {
            setupShowcase()
        }
    }

    fun setupShowcase() {
        val sequence = MaterialShowcaseSequence(this, "4815")
        sequence.addSequenceItem(
                MaterialShowcaseView.Builder(this)
                        .setTarget(fab)
                        .setContentText("Click this to shuffle the movie results")
                        .setDismissText("GOT IT")
                        .setShapePadding(20)
                        .setDelay(1000)
                        .build()
        )
        sequence.start()
    }

    fun setupNavDrawer() {
        setHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setCheckedItem(lastSelectedItem)
        navigationView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawers()
            if (item.groupId == R.id.section_main) lastSelectedItem = item.itemId
            when (item.itemId) {
                R.id.action_home -> consume {
                    setupHomePage()
                    replaceFrame(R.id.frame, LandingFragment(), false)
                    title = "Last Pick"
                }
                R.id.action_history -> consume {
                    setupDefaultPage()
                    replaceFrame(R.id.frame, HistoryFragment(), false)
                    title = "History"
                }
                R.id.action_bookmarks -> consume {
                    setupDefaultPage()
                    replaceFrame(R.id.frame, BookmarksFragment(), false)
                    title = "Bookmarks"
                }
                R.id.action_settings, R.id.action_send_feedback -> consume {
                    Snackbar.make(fab!!, "Coming Soon", Snackbar.LENGTH_SHORT).show()
                }
                R.id.action_about -> consume {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                else -> false
            }
        }
    }

    fun setupHomePage() {
        isThemeLocked = false
        menuId = R.menu.menu_home
        supportInvalidateOptionsMenu()
        enableFab()
    }

    fun setupDefaultPage() {
        resetTheme()
        isThemeLocked = true
        menuId = R.menu.menu_empty
        supportInvalidateOptionsMenu()
        appBar?.setExpanded(false, false)
        disableFab()
    }

    override fun onStart() {
        super.onStart()
        fab?.setOnClickListener {
            startActivity(Intent(this, MovieActivity::class.java))
        }
        navigationView.menu.findItem(lastSelectedItem).isChecked = true
    }
}