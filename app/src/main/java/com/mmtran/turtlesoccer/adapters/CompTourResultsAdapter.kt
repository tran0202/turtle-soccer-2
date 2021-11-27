package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagCodeBinding
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagCodeCellBinding
import com.mmtran.turtlesoccer.databinding.RowCompTourResultBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.SectionHeader
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
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
            holder.championsColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.runnersUpColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.thirdPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.fourthPlaceColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
            holder.semiFinalistsColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_comp_result_even_row_other_cell, null)
        } else {
            holder.tournamentColumnLinearLayout.background = ResourcesCompat.getDrawable(_context!!.resources, R.drawable.border_other_row_first_cell, null)
            holder.championsColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
            holder.runnersUpColumnLinearLayout.background = ResourcesCompat.getDrawable(_context.resources, R.drawable.border_other_row_other_cell, null)
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

            CommonUtil.renderTeamFlagCodeCell(_context, tournament.finalStandings!!.championTeam, holder.championsFlagCodeCellBinding)

            CommonUtil.renderTeamFlagCodeCell(_context, tournament.finalStandings!!.runnersUpTeam, holder.runnersUpFlagCodeCellBinding)

            val recyclerView: RecyclerView = holder.thirdPlaceListRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(_context)
            CommonUtil.addDivider(recyclerView, _context, R.drawable.no_divider)
            compTourThirdPlaceAdapter = CompTourThirdPlaceAdapter(_context, tournament.finalStandings!!.thirdPlaceTeam!!)
            recyclerView.adapter = compTourThirdPlaceAdapter

            CommonUtil.renderTeamFlagCodeCell(_context, tournament.finalStandings!!.fourthPlaceTeam, holder.fourthPlaceFlagCodeCellBinding)

            if (tournament.finalStandings!!.semiFinalist1Team!!.isValid() && tournament.finalStandings!!.semiFinalist2Team!!.isValid()) {
                holder.semiFinalistsColumnLinearLayout.visibility = View.VISIBLE
                holder.thirdPlaceColumnLinearLayout.visibility = View.GONE
                holder.fourthPlaceColumnLinearLayout.visibility = View.GONE
                TeamUtil.renderFlagCode(_context, tournament.finalStandings!!.semiFinalist1Team, holder.semiFinalist1FlagCodeBinding)
                TeamUtil.renderFlagCode(_context, tournament.finalStandings!!.semiFinalist2Team, holder.semiFinalist2FlagCodeBinding)
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
        var championsColumnLinearLayout: LinearLayout = binding.championsColumn
        var championsFlagCodeCellBinding: FragmentTeamFlagCodeCellBinding = binding.champions
        var runnersUpColumnLinearLayout: LinearLayout = binding.runnersUpColumn
        var runnersUpFlagCodeCellBinding: FragmentTeamFlagCodeCellBinding = binding.runnersUp
        var thirdPlaceColumnLinearLayout: LinearLayout = binding.thirdPlaceColumn
        var thirdPlaceListRecyclerView: RecyclerView = binding.thirdPlaceList
        var fourthPlaceColumnLinearLayout: LinearLayout = binding.fourthPlaceColumn
        var fourthPlaceFlagCodeCellBinding: FragmentTeamFlagCodeCellBinding = binding.fourthPlace
        var semiFinalistsColumnLinearLayout: LinearLayout = binding.semiFinalistsColumn
        var semiFinalist1FlagCodeBinding: FragmentTeamFlagCodeBinding = binding.semiFinalist1FlagName
        var semiFinalist2FlagCodeBinding: FragmentTeamFlagCodeBinding = binding.semiFinalist2FlagName

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
