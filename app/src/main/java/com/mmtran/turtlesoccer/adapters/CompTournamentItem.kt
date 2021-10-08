package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mmtran.turtlesoccer.databinding.RowCompTournamentBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Tournament

class CompTournamentItem(private val tournament: Tournament) : ListItem() {

    private var binding: RowCompTournamentBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

    override fun getView(context: Context?, convertView: View?, parent: ViewGroup?): View {

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowCompTournamentBinding.inflate(inflater, parent, false)
        val root: View = binding!!.root

        firebaseStorageLoader = FirebaseStorageLoader()
        firebaseStorageLoader!!.init(context)
        firebaseStorageLoader!!.loadImage(
            context,
            binding!!.tournamentLogos,
            "wc_logos/" + tournament.details!!.logoFilename
        )

        return root
    }
}
