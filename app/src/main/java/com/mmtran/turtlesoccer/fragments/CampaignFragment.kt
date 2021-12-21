package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.databinding.FragmentCampaignBinding
import com.mmtran.turtlesoccer.models.Campaign
import com.mmtran.turtlesoccer.models.CampaignViewModel

class CampaignFragment(campaign: Campaign? = Campaign()) : Fragment() {

    private var campaignViewModel: CampaignViewModel? = null
    private var _campaign: Campaign? = campaign

    private var binding: FragmentCampaignBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        campaignViewModel = ViewModelProvider(this).get(modelClass = CampaignViewModel::class.java)
        campaignViewModel!!.setCampaign(_campaign!!)

        binding = FragmentCampaignBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.textHome.text = _campaign!!.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(campaign: Campaign?): Fragment {
            return CampaignFragment(campaign)
        }
    }
}
