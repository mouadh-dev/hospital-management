package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Patients.MyAdapterOrdonancePatient
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem


class OrdonancePatientFragment : Fragment() {
    var listOrdonancePatient: ListView? = null
    var list = mutableListOf<Ordonance>()
    var adapterOrdonance: MyAdapterOrdonancePatient? = null
    var userDao = UserDao()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ordonance_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listOrdonancePatient = view.findViewById(R.id.list_Ordonance_Patient)
        initAdapter()

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.ordonance!= null) {

                    for (entry in userItem.ordonance!!.entries) {
                        var ordonance = entry.value
                        if (ordonance.namepatientOrdo.equals(userItem.nom + " " + userItem.prenom)) {

                            list.add(ordonance)
                            adapterOrdonance!!.notifyDataSetChanged()

                        }
                    }
                }
            }

            override fun failure() {
            }
        })
    }

    private fun initAdapter() {
        adapterOrdonance =
            MyAdapterOrdonancePatient(requireContext(), R.layout.ordonance_list_patient, list)
        listOrdonancePatient!!.adapter = adapterOrdonance
    }


}