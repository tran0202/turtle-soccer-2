package com.mmtran.turtlesoccer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.mmtran.turtlesoccer.databinding.ActivityMainBinding
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.models.*

class MainActivity : AppCompatActivity() {

    var nationListViewModel: NationListViewModel? = null
    var teamListViewModel: TeamListViewModel? = null
    var confederationListViewModel: ConfederationListViewModel? = null
    var competitionListViewModel: CompetitionListViewModel? = null
    var tournamentListViewModel: TournamentListViewModel? = null

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataLoader = FirestoreLoader()

        nationListViewModel = ViewModelProvider(this).get(modelClass = NationListViewModel::class.java)
        dataLoader.getNations(nationListViewModel!!)

        teamListViewModel = ViewModelProvider(this).get(modelClass = TeamListViewModel::class.java)
        dataLoader.getTeams(teamListViewModel!!)

        confederationListViewModel = ViewModelProvider(this).get(modelClass = ConfederationListViewModel::class.java)
        dataLoader.getConfederations(confederationListViewModel!!)

        competitionListViewModel = ViewModelProvider(this).get(modelClass = CompetitionListViewModel::class.java)
        dataLoader.getCompetitions(competitionListViewModel!!)

        tournamentListViewModel = ViewModelProvider(this).get(modelClass = TournamentListViewModel::class.java)
        dataLoader.getTournaments(tournamentListViewModel!!)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_tournaments, R.id.navigation_competitions, R.id.navigation_confederations, R.id.navigation_nations
        )
            .build()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding!!.mainNavView, navController)
    }
}
