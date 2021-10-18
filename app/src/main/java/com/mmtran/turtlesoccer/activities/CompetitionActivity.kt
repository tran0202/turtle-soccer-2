package com.mmtran.turtlesoccer.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
import com.mmtran.turtlesoccer.databinding.ActivityCompetitionBinding
import com.mmtran.turtlesoccer.databinding.ActivityMainBinding
import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil

class CompetitionActivity : AppCompatActivity() {

    private var nationListViewModel: AssociationListViewModel? = null
    private var clubListViewModel: ClubListViewModel? = null
    private var tournamentListViewModel: TournamentListViewModel? = null
    private var nationList: List<Nation?>? = emptyList()
    private var clubList: List<Club?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competition: Competition? = null

    private var binding: ActivityCompetitionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompetitionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        competition = intent.getSerializableExtra(EXTRA_COMPETITION) as Competition

        ActionBarUtil.buildActionBar(layoutInflater, supportActionBar, R.layout.toolbar_competition_activity)
        supportActionBar!!.title = competition!!.name

        val dataLoader = FirestoreLoader()
        nationListViewModel = ViewModelProvider(this).get(
            AssociationListViewModel::class.java
        )
        dataLoader.getActiveNations(nationListViewModel!!)

        clubListViewModel = ViewModelProvider(this).get(
            ClubListViewModel::class.java
        )
        dataLoader.getClubs(clubListViewModel!!)

        tournamentListViewModel = ViewModelProvider(this).get(
            TournamentListViewModel::class.java
        )
        dataLoader.getTournaments(tournamentListViewModel!!)

        nationListViewModel!!.nationList.observe(
            this,
            { nationList_: List<Nation?>? ->
                nationList = nationList_
                joinNation()
            })
        clubListViewModel!!.clubList.observe(
            this,
            { clubList_: List<Club?>? ->
                clubList = clubList_
                joinNation()
            })
        tournamentListViewModel!!.tournamentList.observe(
            this,
            { tournamentList_: List<Tournament?>? ->
                tournamentList = tournamentList_
                joinNation()
            })
    }

    private fun joinNation() {
        if (nationList.isNullOrEmpty() || clubList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return
        tournamentList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
        val x =1
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
}
