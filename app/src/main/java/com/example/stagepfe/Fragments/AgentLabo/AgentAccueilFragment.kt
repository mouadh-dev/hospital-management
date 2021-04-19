package com.example.stagepfe.Fragments.AgentLabo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.AgentLabo.MyAdapterAnalyses
import com.example.stagepfe.Adapters.Pharmacien.MyAdapterNewOrdonnancePharmacien
import com.example.stagepfe.Models.AgentLabo.ModelAnalyses
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R


class AgentAccueilFragment : Fragment() {

    var listviewNewAnalyses: ListView? = null
    var listAnalyses= mutableListOf<ModelAnalyses>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_agent_accueil, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewNewAnalyses = view.findViewById<ListView>(R.id.listAnalyses)
        listAnalyses.add(ModelAnalyses("Mohamed","06/07/2021","12:46",R.drawable.logopatient))
        listviewNewAnalyses!!.adapter = MyAdapterAnalyses(requireContext(), R.layout.analyses_list, listAnalyses)


    }


}