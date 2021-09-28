package com.mmtran.turtlesoccer.loaders

import android.util.Log

import com.google.firebase.firestore.*

import com.mmtran.turtlesoccer.models.AssociationsViewModel
import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.models.ConfederationsViewModel
import com.mmtran.turtlesoccer.models.Nation

import java.util.*
import java.util.stream.Collectors

class FirestoreLoader {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val nationList: MutableList<Nation?> = ArrayList()
    private val confederationList: MutableList<Confederation> = ArrayList()

    fun getActiveNations(associationsViewModel: AssociationsViewModel) {

        val query = db.collection("nation")
            .whereEqualTo("parent_nation_id", "")
            .whereNotEqualTo("confederation_id", "")
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val nation = document.toObject(Nation::class.java)
                    nation.id = document.id
                    nationList.add(nation)
                    Log.d(TAG, document.id + " => " + document.data)
                }
                nationList.sortWith(Comparator { lhs, rhs ->
                    Integer.compare(
                        lhs!!.name.compareTo(rhs!!.name),
                        0
                    )
                })
                associationsViewModel.setNationList(nationList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getConfederations(confederationsViewModel: ConfederationsViewModel) {

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
                    Log.d(TAG, document.id + " => " + document.data)
                }
                confederationList.add(0, fifa)
                confederationsViewModel.setConfederationList(confederationList)
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        }
    }

    fun getDocument(associationsViewModel: AssociationsViewModel) {

        val docRef = db.collection("nation").document("AFG")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val nation = documentSnapshot.toObject(Nation::class.java)
            nationList.add(nation)
            nationList.stream()
                .filter { n: Nation? -> n!!.parentNationId.isEmpty() && n.confederationId != null && n.confederationId.isNotEmpty() }
                .collect(Collectors.toList())
            associationsViewModel.setNationList(nationList)
        }
    }

    companion object {
        private const val TAG = "FirestoreLoader"
    }
}
