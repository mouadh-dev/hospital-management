package com.example.stagepfe.Fragments.Patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Patients.MyAdapterOrdonancePatient
import com.example.stagepfe.Adapters.Patients.MyAdapterRapportPatient
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Dao.getOrdonanceCallback
import com.example.stagepfe.Models.Patient.ModelOrdonancePatient
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem


class OrdonancePatientFragment : Fragment() {
    var listOrdonancePatient: ListView? = null
    var list = mutableListOf<ModelOrdonancePatient>()
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

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getOrdonance(object : getOrdonanceCallback {
                    override fun successOrdonance(
                        ordonance: Ordonance,
                        medicamentOrdonance: MedicamentOrdonance
                    ) {
                       if (ordonance.namepatientOrdo.equals(userItem.nom + " " + userItem.prenom)){
                           list.add(ModelOrdonancePatient("Ordonance","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
                           initAdapter()
                       }
                    }

                    override fun failureOrdonance() {

                    }

                })

            }

            override fun failure() {
            }
        })

//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
////        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","termineé",R.color.green,0))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar,))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
//        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.red,R.drawable.codebar))
////        list.add(ModelOrdonancePatient("Ordonance 1","11/05/2020","11:00","pas encore",R.color.green,0))



    }

    private fun initAdapter() {
        listOrdonancePatient!!.adapter = MyAdapterOrdonancePatient(requireContext(),R.layout.ordonance_list_patient,list)
    }


}