package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameNarrowBinding
import com.mmtran.turtlesoccer.databinding.RowCompTourResultBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.SectionHeader
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.TeamUtil

class CompTourResultsAdapter(context: Context?, tournamentList: List<Tournament?>) :
    RecyclerView.Adapter<CompTourResultsAdapter.ViewHolder>() {

    private val _context = context
    private val _tournamentList: List<Tournament?> = tournamentList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var compTourThirdPlaceAdapter: CompTourThirdPlaceAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompTourResultBinding = RowCompTourResultBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tournament: Tournament = _tournamentList[position]!!

        if (tournament.era != null) {
            holder.eraTextView.visibility = View.VISIBLE
            holder.eraTextView.text = tournament.era
        } else {
            holder.eraTextView.visibility = View.GONE
        }

        when (tournament.compTourResultSectionHeader) {
            SectionHeader.THIRD_PLACE_HEADER -> {
                holder.sectionThirdFourthPlaceLinearLayout.visibility = View.VISIBLE
                holder.sectionSemiFinalistsLinearLayout.visibility = View.GONE
            }
            SectionHeader.SEMIFINALISTS_HEADER -> {
                holder.sectionThirdFourthPlaceLinearLayout.visibility = View.GONE
                holder.sectionSemiFinalistsLinearLayout.visibility = View.VISIBLE
            }
            else -> {
                holder.sectionThirdFourthPlaceLinearLayout.visibility = View.GONE
                holder.sectionSemiFinalistsLinearLayout.visibility = View.GONE
            }
        }

        if (tournament.compTourResultEvenRow) {
            holder.tournamentColumnLinearLayout.background = ResourcesCompat.getDrawable(_context!!.resources, R.drawable.border_comp_result_even_row_first_cell, null)
            holder.championColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.runnerUpColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.thirdPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.fourthPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.semiFinalistsColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
        } else {
            holder.tournamentColumnLinearLayout.background = ResourcesCompat.getDrawable(_context!!.resources, R.drawable.border_other_row_first_cell, null)
            holder.championColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
            holder.runnerUpColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
            holder.thirdPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
            holder.fourthPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
            holder.semiFinalistsColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
        }

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.tournamentLogoImageView,
            tournament.competition!!.logoPath + "/" + tournament.details!!.logoFilename
        )

        if (tournament.shortYear != null) {
            holder.shortYearTextView.visibility = View.VISIBLE
            holder.shortYearTextView.text = tournament.shortYear
        } else {
            holder.shortYearTextView.visibility = View.GONE
        }

        if (tournament.finalStandings != null) {

            if (tournament.finalStandings!!.championTeam != null) {
                holder.championColumnInnerLayout.visibility = View.VISIBLE
                TeamUtil.renderFlagNameNarrow(_context, holder.championFlagNameBinding, tournament.finalStandings!!.championTeam)
            } else {
                holder.championColumnInnerLayout.visibility = View.GONE
            }
            if (tournament.finalStandings!!.runnersUpTeam != null) {
                holder.runnerUpColumnInnerLinearLayout.visibility = View.VISIBLE
                TeamUtil.renderFlagNameNarrow(_context, holder.runnerUpFlagNameBinding, tournament.finalStandings!!.runnersUpTeam)
            } else {
                holder.runnerUpColumnInnerLinearLayout.visibility = View.GONE
            }

            val recyclerView: RecyclerView = holder.thirdPlaceListRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(_context)
            val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(
                ContextCompat.getDrawable(
                    _context,
                    R.drawable.no_divider
                )!!
            )
            recyclerView.addItemDecoration(divider)
            compTourThirdPlaceAdapter = CompTourThirdPlaceAdapter(_context, tournament.finalStandings!!.thirdPlaceTeam!!)
            recyclerView.adapter = compTourThirdPlaceAdapter

            if (tournament.finalStandings!!.fourthPlaceTeam!!.isValid()) {
                holder.fourthPlaceFlagNameBinding.flag.visibility = View.VISIBLE
                holder.fourthPlaceFlagNameBinding.clubLogo.visibility = View.VISIBLE
                TeamUtil.renderFlagNameNarrow(_context, holder.fourthPlaceFlagNameBinding, tournament.finalStandings!!.fourthPlaceTeam)
            } else {
                holder.fourthPlaceFlagNameBinding.flag.visibility = View.GONE
                holder.fourthPlaceFlagNameBinding.clubLogo.visibility = View.GONE
                holder.fourthPlaceFlagNameBinding.code.text = ""
            }

            if (tournament.finalStandings!!.semiFinalist1Team!!.isValid() && tournament.finalStandings!!.semiFinalist2Team!!.isValid()) {
                holder.semiFinalistsColumnLinearLayout.visibility = View.VISIBLE
                holder.thirdPlaceColumnLinearLayout.visibility = View.GONE
                holder.fourthPlaceColumnLinearLayout.visibility = View.GONE
                TeamUtil.renderFlagNameNarrow(_context, holder.semiFinalist1FlagNameBinding, tournament.finalStandings!!.semiFinalist1Team)
                TeamUtil.renderFlagNameNarrow(_context, holder.semiFinalist2FlagNameBinding, tournament.finalStandings!!.semiFinalist2Team)
            } else {
                holder.semiFinalistsColumnLinearLayout.visibility = View.GONE
                holder.thirdPlaceColumnLinearLayout.visibility = View.VISIBLE
                holder.fourthPlaceColumnLinearLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return _tournamentList.size
    }

    inner class ViewHolder internal constructor(binding: RowCompTourResultBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var eraTextView: TextView = binding.era
        var sectionThirdFourthPlaceLinearLayout: LinearLayout = binding.sectionThirdFourthPlace
        var sectionSemiFinalistsLinearLayout: LinearLayout = binding.sectionSemiFinalists
        var tournamentColumnLinearLayout: LinearLayout = binding.tournamentColumn
        var tournamentLogoImageView: ImageView = binding.tournamentLogo
        var shortYearTextView: TextView = binding.shortYear
        var championColumnLinearLayout: LinearLayout = binding.championColumn
        var championColumnInnerLayout: LinearLayout = binding.championColumnInner
        var championFlagNameBinding: FragmentFlagNameNarrowBinding = binding.championFlagName
        var runnerUpColumnLinearLayout: LinearLayout = binding.runnerUpColumn
        var runnerUpColumnInnerLinearLayout: LinearLayout = binding.runnerUpColumnInner
        var runnerUpFlagNameBinding: FragmentFlagNameNarrowBinding = binding.runnerUpFlagName
        var thirdPlaceColumnLinearLayout: LinearLayout = binding.thirdPlaceColumn
        var thirdPlaceListRecyclerView: RecyclerView = binding.thirdPlaceList
        var fourthPlaceColumnLinearLayout: LinearLayout = binding.fourthPlaceColumn
        var fourthPlaceFlagNameBinding: FragmentFlagNameNarrowBinding = binding.fourthPlaceFlagName
        var semiFinalistsColumnLinearLayout: LinearLayout = binding.semiFinalistsColumn
        var semiFinalist1FlagNameBinding: FragmentFlagNameNarrowBinding = binding.semiFinalist1FlagName
        var semiFinalist2FlagNameBinding: FragmentFlagNameNarrowBinding = binding.semiFinalist2FlagName

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _tournamentList, absoluteAdapterPosition)
        }

        init {
            tournamentColumnLinearLayout.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int)
    }
}
