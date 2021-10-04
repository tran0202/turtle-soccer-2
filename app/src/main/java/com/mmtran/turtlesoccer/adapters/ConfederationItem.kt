package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mmtran.turtlesoccer.databinding.RowConfederationBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Confederation

class ConfederationItem(private val confederation: Confederation) : ListItem() {

    private var binding: RowConfederationBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

    override fun getView(context: Context?, convertView: View?, parent: ViewGroup?): View {

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowConfederationBinding.inflate(inflater, parent, false)
        val root: View = binding!!.root

        firebaseStorageLoader = FirebaseStorageLoader()
        firebaseStorageLoader!!.init(context)
        firebaseStorageLoader!!.loadImage(
            context,
            binding!!.confederationFlag,
            "logos/" + confederation.logoFilename
        )

        binding!!.confederationName.text = confederation.name
        binding!!.description.text = confederation.description

        return root
    }
}