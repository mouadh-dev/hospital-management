package com.example.stagepfe.Fragments.AgentLabo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Activity.AgentLabo.AccueilAgentLaboActivity
import com.example.stagepfe.Activity.Pharmacien.AccueilPharmacienActivity
import com.example.stagepfe.Adapters.AgentLabo.MyAdapterAnalyses
import com.example.stagepfe.Adapters.Pharmacien.MyAdapterNewOrdonnancePharmacien
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.AgentLabo.ModelAnalyses
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem


class AgentAccueilFragment : Fragment() {

    var listviewNewAnalyses: ListView? = null
    var listAnalyses= mutableListOf<ModelAnalyses>()
    var adapterLaboratoire: MyAdapterAnalyses? = null
    var date = ""

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
        initAdapter()

        val activity: AccueilAgentLaboActivity? =
            requireActivity() as AccueilAgentLaboActivity?
        val myDataFromActivity: String? = activity!!.getMyDataAgentLabo()

        var userDao = UserDao()

        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.ordonance != null) {
                    for (ordo in userItem.ordonance!!.entries) {
                        var ordonance = ordo.value
                        if (ordonance.idPatient.equals(userItem.id.toString())) {
                            if (ordonance.taken.equals("termineé") && ordonance.typeOrdonnance.equals(
                                    "Ordonnance analyse"
                                ) && ordonance.dateOrdonanceSend + ordonance.hourOrdonanceSend != date
                            ) {
                                date = ordonance.dateOrdonanceSend + ordonance.hourOrdonanceSend
                                var namepatientOrdo =""
                                userDao.populateSearch(object : UserCallback {
                                    override fun onSuccess(userPatient: UserItem) {
                                        if (ordonance.idPatient.equals(userPatient.id)){
                                            namepatientOrdo = userPatient.nom + " " + userPatient.prenom
                                            listAnalyses.add(
                                                ModelAnalyses(
                                                    namepatientOrdo,
                                                    ordonance.dateOrdonanceSend.toString(),
                                                    ordonance.hourOrdonanceSend.toString()
                                                        .substring(0, 5),
                                                    R.drawable.logopatient
                                                )
                                            )
                                            adapterLaboratoire!!.notifyDataSetChanged()
                                            date =
                                                ordonance.dateOrdonanceSend + ordonance.hourOrdonanceSend
                                        }
                                    }

                                    override fun failure() {
                                    }
                                })

                            }
                        }
                    }
                }

            }

            override fun failure() {
            }
        })


    }

    private fun initAdapter() {
        adapterLaboratoire = MyAdapterAnalyses(requireContext(),R.layout.analyses_list,listAnalyses)
        listviewNewAnalyses!!.adapter =adapterLaboratoire
    }


}