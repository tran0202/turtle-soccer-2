package com.mmtran.turtlesoccer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CompetitionPagerAdapter
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
import com.mmtran.turtlesoccer.databinding.ActivityCompetitionBinding
import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.mmtran.turtlesoccer.utils.CompetitionUtil

class CompetitionActivity : AppCompatActivity() {

    private var binding: ActivityCompetitionBinding? = null
    private var compViewPager: ViewPager2? = null
    private var competitionPagerAdapter: CompetitionPagerAdapter? = null

    private var nationListViewModel: NationListViewModel? = null
    private var teamListViewModel: TeamListViewModel? = null
    private var tournamentListViewModel: TournamentListViewModel? = null
    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competition: Competition? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompetitionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        competition = intent.getSerializableExtra(EXTRA_COMPETITION) as Competition

        ActionBarUtil.buildActionBar(layoutInflater, supportActionBar, R.layout.toolbar_competition_activity)
        supportActionBar!!.title = competition!!.name

        compViewPager = binding!!.competitionViewPager

        val dataLoader = FirestoreLoader()
        nationListViewModel = ViewModelProvider(this).get(
            NationListViewModel::class.java
        )
        dataLoader.getActiveNations(nationListViewModel!!)

        teamListViewModel = ViewModelProvider(this).get(
            TeamListViewModel::class.java
        )
        dataLoader.getTeams(teamListViewModel!!)

        tournamentListViewModel = ViewModelProvider(this).get(
            TournamentListViewModel::class.java
        )
        dataLoader.getTournaments(tournamentListViewModel!!)

        nationListViewModel!!.nationList.observe(
            this,
            { nationList_: List<Nation?>? ->
                nationList = nationList_
                competitionObserver()
            })
        teamListViewModel!!.teamList.observe(
            this,
            { teamList_: List<Team?>? ->
                teamList = teamList_
                competitionObserver()
            })
        tournamentListViewModel!!.tournamentList.observe(
            this,
            { tournamentList_: List<Tournament?>? ->
                tournamentList = tournamentList_
                competitionObserver()
            })
    }

    private fun competitionObserver() {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return

        CompetitionUtil.getChampion(competition, nationList, teamList)

        tournamentList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
        competition!!.tournamentList = tournamentList

        competitionPagerAdapter = CompetitionPagerAdapter.newInstance(supportFragmentManager, lifecycle)
        competitionPagerAdapter!!.setCompetition(competition!!)
        compViewPager!!.adapter = competitionPagerAdapter

        TabLayoutMediator(
            binding!!.competitionTabLayout, compViewPager!!
        ) { tab: TabLayout.Tab, position: Int ->
            tab.setText(
                TAB_RES[position]
            )
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAB_RES =
            intArrayOf(R.string.competition_about, R.string.competition_results, R.string.competition_all_time_standings)
    }
}
