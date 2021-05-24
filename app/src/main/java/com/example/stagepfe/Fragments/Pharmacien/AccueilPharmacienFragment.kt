package com.example.stagepfe.Fragments.Pharmacien

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Pharmacien.AccueilPharmacienActivity
import com.example.stagepfe.Adapters.Pharmacien.MyAdapterNewOrdonnancePharmacien
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem


class AccueilPharmacienFragment : Fragment() {
    var listviewNewOrdonnanceParmacien: ListView? = null
    var listNewOrdonnanceParmacien = mutableListOf<ModelNewOrdonnancePharmacien>()
    var adapterPharmacien: MyAdapterNewOrdonnancePharmacien? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_accueil_pharmacien, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewNewOrdonnanceParmacien =
            view.findViewById<ListView>(R.id.listNewOrdonnance_Pharmacien)
        initAdapter()

        val activity: AccueilPharmacienActivity? =
            activity as AccueilPharmacienActivity?
        val myDataFromActivity: String? = activity!!.getMyDataPharmacien()
        println("mouadh :: " + myDataFromActivity)
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (myDataFromActivity.equals(userItem.id.toString())) {

                    for (entry in userItem.ordonance!!.entries) {
                        var medicament = entry.value
                        if (medicament != null) {
                            listNewOrdonnanceParmacien.add(
                                ModelNewOrdonnancePharmacien(
                                    medicament.namepatientOrdo.toString(),
                                    medicament.dateOrdonanceSend.toString(),
                                    medicament.hourOrdonanceSend.toString().substring(1, 5),
//                                R.drawable.logopatient
                                    userItem.profilPhotos!!.toInt()
                                )
                            )
                            adapterPharmacien!!.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun failure() {
            }
        })


//        listNewOrdonnanceParmacien.add(ModelNewOrdonnancePharmacien("Mohamed","12/12/2021","12:12",R.drawable.logopatient))
//        listNewOrdonnanceParmacien.add(ModelNewOrdonnancePharmacien("Mohamed","12/12/2021","12:12",R.drawable.logopatient))

    }

    private fun initAdapter() {
        adapterPharmacien = MyAdapterNewOrdonnancePharmacien(
            requireContext(),
            R.layout.nouvelle_ordonnance_pharmarcien,
            listNewOrdonnanceParmacien
        )
        listviewNewOrdonnanceParmacien!!.adapter = adapterPharmacien
    }


}