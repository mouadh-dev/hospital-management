package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AddRapportDoctorActivity
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterRapport
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.ResponseCallback
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
var fullDate:String? = null

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
                val fullNameDoctor = userItem.prenom + " " + userItem.nom
                userDao.getRapport(object : RapportCallback {
                    override fun success(rapport: Rapports) {

                        nameDoctorRapport = rapport.nameDoctorRapport
                        spcialityDoctorRapport = rapport.specialityDoctor
                        if (nameDoctorRapport!!.equals(fullNameDoctor) && myDataFromActivity.equals(
                                rapport.namPatientRapport) &&
                            (rapport.hourRapport + " " + rapport.hourRapport)!= fullDate
                        ) {
                            fullDate = rapport.hourRapport + " " + rapport.hourRapport
                            listRapport.add(
                                ModelRapport(
                                    rapport.dateRapport!!,
                                    rapport.hourRapport!!.substring(0, 5)
                                )
                            )
                            initAdapter()
                        }

                        listviewRapport!!.setOnItemClickListener { parent, view, position, id ->
                            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                            var builder = AlertDialog.Builder(requireContext())
                            builder.setView(v)

                            var dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                            dialog.findViewById<TextView>(R.id.TitleDialog).visibility = View.GONE
                            dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
                            dialog.findViewById<EditText>(R.id.TextRapport).visibility =
                                View.VISIBLE
                            dialog.findViewById<EditText>(R.id.TextRapport).setText(rapport.textRapport)
                            dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE
                            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                                dialog.dismiss()
                                var  rapporttoSend = Rapports()
                                 rapporttoSend.dateRapport = rapport.dateRapport
                                 rapporttoSend.hourRapport = rapport.hourRapport
                                rapporttoSend.id = rapport.id
                                rapporttoSend.idDoctorRapport = rapport.idDoctorRapport
                                rapporttoSend.idPatientRapport = rapport.idPatientRapport
                                rapporttoSend.namPatientRapport = rapport.namPatientRapport
                                rapporttoSend.nameDoctorRapport = rapport.nameDoctorRapport
                                rapporttoSend.specialityDoctor = rapport.specialityDoctor
                                rapporttoSend.textRapport = dialog.findViewById<EditText>(R.id.TextRapport).text.toString()

                                userDao.updateRapport(rapporttoSend.idDoctorRapport!!,
                                    rapporttoSend.idPatientRapport!!,
                                    rapporttoSend.id!!,
                                    rapporttoSend,
                                    object : ResponseCallback {
                                        override fun success(medicament: String) {

                                        }

                                        override fun success() {

                                        }

                                        override fun failure() {

                                        }
                                    })
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
        requireActivity().run {
            listviewRapport!!.adapter =
                MyAdapterRapport(this, R.layout.list_rapport, listRapport)
        }
    }


}