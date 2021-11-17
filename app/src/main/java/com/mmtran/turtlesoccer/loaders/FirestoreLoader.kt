package com.mmtran.turtlesoccer.loaders

import android.util.Log

import com.google.firebase.firestore.*
import com.mmtran.turtlesoccer.models.*

import java.util.*

class FirestoreLoader {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val confederationList: MutableList<Confederation> = ArrayList()
    private val competitionList: MutableList<Competition> = ArrayList()
    private val tournamentList: MutableList<Tournament> = ArrayList()
    private val nationList: MutableList<Nation> = ArrayList()
    private val teamList: MutableList<Team> = ArrayList()

    fun getNations(nationListViewModel: NationListViewModel) {

        val query = db.collection("nation")
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val nation = document.toObject(Nation::class.java)
                    nation.id = document.id
                    nationList.add(nation)
//                    Log.d(TAG, document.id + " => " + document.data)
                }
                nationList.sortWith { lhs, rhs ->
                    lhs!!.name.compareTo(rhs!!.name).compareTo(0)
                }
                nationListViewModel.setNationList(nationList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getTeams(teamListViewModel: TeamListViewModel) {

        val query = db.collection("team")
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val team = document.toObject(Team::class.java)
                    team.id = document.id
                    teamList.add(team)
//                    Log.d(TAG, document.id + " => " + document.data)
                }
                teamList.sortWith { lhs, rhs ->
                    lhs!!.name!!.compareTo(rhs!!.name!!).compareTo(0)
                }
                teamListViewModel.setTeamList(teamList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getConfederations(confederationListViewModel: ConfederationListViewModel) {

        val query: Query = db.collection("confederation")
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var fifa = Confederation()
                for (document in task.result!!) {
                    val confederation = document.toObject(
                        Confederation::class.java
                    )
                    confederation.id = document.id
                    if (confederation.id == "FIFA") {
                        fifa = confederation
                    } else {
                        confederationList.add(confederation)
                    }
//                    Log.d(TAG, document.id + " => " + document.data)
                }
                confederationList.add(0, fifa)
                confederationListViewModel.setConfederationList(confederationList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getCompetitions(competitionsViewModel: CompetitionListViewModel) {

        val query: Query = db.collection("competition")
            .whereNotEqualTo("order", -1)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val competition = document.toObject(
                        Competition::class.java
                    )
                    competition.id = document.id
                    competitionList.add(competition)
//                    Log.d(TAG, document.id + " => " + document.data)
                }
                competitionsViewModel.setCompetitionList(competitionList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getTournaments(tournamentListViewModel: TournamentListViewModel) {

        val query: Query = db.collection("tournament")
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val tournament = document.toObject(
                        Tournament::class.java
                    )
                    tournament.id = document.id
                    tournamentList.add(tournament)
//                    Log.d(TAG, document.id + " => " + document.data)
                }
                tournamentListViewModel.setTournamentList(tournamentList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

//    fun getNation(competition: Competition) {
//
//        val docRef = db.collection("nation").document("FRA")
//        docRef.get().addOnSuccessListener { documentSnapshot ->
//            val nation = documentSnapshot.toObject(Nation::class.java)
//            nation!!.id = documentSnapshot.id
//            competition.currentChampionNation = nation
//        }
//    }

    companion object {
        private const val TAG = "FirestoreLoader"
    }
}
