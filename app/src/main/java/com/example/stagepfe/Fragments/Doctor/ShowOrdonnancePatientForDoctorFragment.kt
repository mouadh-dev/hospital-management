package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AddOrdonanceDoctorActivity
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowOrdonnancePatForDoc
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Dao.getOrdonanceCallback
import com.example.stagepfe.Models.Doctors.ModelShowOrdonnancePatForDoctor
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem


class ShowOrdonnancePatientForDoctorFragment : Fragment() {
    var listviewOrdoPatForDoctor: ListView? = null
    var listOrdoPatForDoctor = mutableListOf<ModelShowOrdonnancePatForDoctor>()
    var addOrdonance:ImageView? = null
    var userDao = UserDao()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     var view =inflater.inflate(
         R.layout.fragment_show_ordonnance_patient_for_doctor,
         container,
         false
     )


        initView(view)
        return view
    }

    private fun initView(view: View) {
        addOrdonance = view.findViewById(R.id.Add_Ordonance)
        listviewOrdoPatForDoctor =view.findViewById<ListView>(R.id.showOrdPatForDoctorr)

////////////////////////////////////////////addOrdonance////////////////////////////////////////////
        val activity: ShowInfoPatientForDoctorActivity? = activity as ShowInfoPatientForDoctorActivity?
        val myDataFromActivity: String = activity!!.getMyData().toString()
userDao.retrieveCurrentDataUser(object : UserCallback {
    override fun onSuccess(userItem: UserItem) {
        var fullNameDoctor = userItem.prenom + " " + userItem.nom
        userDao.getOrdonance(object : getOrdonanceCallback {
            override fun successOrdonance(
                ordonance: Ordonance,
                medicamentOrdonance: MedicamentOrdonance
            ) {
                if (ordonance.namepatientOrdo!!.equals(myDataFromActivity) &&
                    ordonance.nameDoctorOrd!!.equals(fullNameDoctor)) {
                    listOrdoPatForDoctor.add(
                        ModelShowOrdonnancePatForDoctor(
                            ordonance.namepatientOrdo!!,
                            medicamentOrdonance.nameMedicament!!
                        )
                    )
                    listviewOrdoPatForDoctor!!.adapter = MyAdapterShowOrdonnancePatForDoc(
                        requireContext(),
                        R.layout.show_ordonnance_patient_to_doctor,
                        listOrdoPatForDoctor
                    )
                }
            }

            override fun failureOrdonance() {
            }
        })
    }

    override fun failure() {

    }
})


        addOrdonance!!.setOnClickListener {
            var intent = Intent(requireContext(), AddOrdonanceDoctorActivity::class.java)
            intent.putExtra("namePatentToOrdonance",myDataFromActivity)
            startActivity(intent)


        }





//        listOrdoPatForDoctor.add(ModelShowOrdonnancePatForDoctor("12/12/2020","15:50"))
//        listviewOrdoPatForDoctor!!.adapter = MyAdapterShowOrdonnancePatForDoc(requireContext(), R.layout.show_ordonnance_patient_to_doctor, listOrdoPatForDoctor)


    }


}