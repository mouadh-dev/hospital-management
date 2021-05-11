package com.example.stagepfe.Fragments.Doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AddRapportDoctorActivity
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterRapport
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelRapport
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem


class ShowRapportPatientForDoctorFragment : Fragment() {

    var listviewRapport: ListView? = null
    var listRapport = mutableListOf<ModelRapport>()
    var addRapport: ImageView? = null
    var nameDoctorRapport: String? = null
    var spcialityDoctorRapport: String? = null
    var userDao = UserDao()
//    var fullNameDoctor:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =
            inflater.inflate(R.layout.fragment_show_rapport_patient_for_doctor, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        addRapport = view.findViewById(R.id.Add_Rapport)
        listviewRapport = view.findViewById<ListView>(R.id.showRapportPatForDoctorr)




        val activity: ShowInfoPatientForDoctorActivity? =
            activity as ShowInfoPatientForDoctorActivity?
        val myDataFromActivity: String = activity!!.getMyData().toString()

        addRapport!!.setOnClickListener {
            var intent = Intent(activity, AddRapportDoctorActivity::class.java)
            intent.putExtra("namePatentToRapport", myDataFromActivity)
            startActivity(intent)

        }
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                var fullNameDoctor = userItem.prenom + " " + userItem.nom


                userDao.getRapport(object : RapportCallback {
                    override fun success(rapport: Rapports) {

                        nameDoctorRapport = rapport.nameDoctorRapport
                        spcialityDoctorRapport = rapport.specialityDoctor
                        if (nameDoctorRapport!!.equals(fullNameDoctor) && myDataFromActivity.equals(rapport.namPatientRapport)) {

                            listRapport.add(ModelRapport(nameDoctorRapport!!,spcialityDoctorRapport!!))
                            requireActivity().run {
                                listviewRapport!!.adapter =
                                    MyAdapterRapport(this, R.layout.list_rapport, listRapport)
                            }
                        }
                    }

                    override fun failure() {
                    }
                })


            }

            override fun failure() {

            }
        })


//        listRapport.add(ModelRapport("ff", "hh"))


    }

    private fun initAdapter() {
//        requireActivity().run {
//            listviewRapport!!.adapter =
//                MyAdapterRapport(this, R.layout.list_rapport, listRapport)
//        }
    }


}