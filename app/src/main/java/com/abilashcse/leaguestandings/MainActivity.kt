package com.abilashcse.leaguestandings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import com.abilashcse.leaguestandings.databinding.MainActivityBinding
import com.abilashcse.leaguestandings.ui.recentwins.RecentWinsFragment
import com.abilashcse.leaguestandings.ui.standings.StandingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainView : MainActivityBinding
    var fragmentManager: FragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainView.root)


        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.container, StandingsFragment.newInstance())
                .commitNow()
        }
        mainView.bottomNavigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.page_1 -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.container, StandingsFragment.newInstance())
                    .commit()
            }
            R.id.page_2 -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.container, RecentWinsFragment.newInstance())
                    .commit()
            }
        }
        true
    }

    companion object {
        const val COMPETITION_ID= 2013

    }
}
