package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AddOrdonanceDoctorActivity
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowOrdonnancePatForDoc
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Dao.getOrdonanceCallback
import com.example.stagepfe.Models.Doctors.ModelShowOrdonnancePatForDoctor
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem

import android.widget.AdapterView.OnItemLongClickListener


class ShowOrdonnancePatientForDoctorFragment : Fragment() {
    var listviewOrdoPatForDoctor: ListView? = null
    var listOrdoPatForDoctor = mutableListOf<ModelShowOrdonnancePatForDoctor>()
    var addOrdonance: ImageView? = null
    var userDao = UserDao()
    var fullNameDoctor: String? = null
    var fullDate: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(
            R.layout.fragment_show_ordonnance_patient_for_doctor,
            container,
            false
        )


        initView(view)
        return view
    }

    private fun initView(view: View) {
        addOrdonance = view.findViewById(R.id.Add_Ordonance)
        listviewOrdoPatForDoctor = view.findViewById<ListView>(R.id.showOrdPatForDoctorr)

////////////////////////////////////////////addOrdonance////////////////////////////////////////////
        val activity: ShowInfoPatientForDoctorActivity? =
            activity as ShowInfoPatientForDoctorActivity?
        val myDataFromActivity: String = activity!!.getMyData().toString()
        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNameDoctor = userItem.prenom + " " + userItem.nom

                userDao.getOrdonance(object : getOrdonanceCallback {
                    override fun successOrdonance(
                        ordonance: Ordonance,
                        medicamentOrdonance: MedicamentOrdonance
                    ) {
                        if (myDataFromActivity.equals(ordonance.namepatientOrdo!!) &&
                            ordonance.nameDoctorOrd!!.equals(fullNameDoctor) &&
                            (ordonance.dateOrdonanceSend + " " + ordonance.hourOrdonanceSend) != (fullDate)
                        ) {
                            fullDate =
                                ordonance.dateOrdonanceSend + " " + ordonance.hourOrdonanceSend
                            listOrdoPatForDoctor.add(
                                ModelShowOrdonnancePatForDoctor(
                                    ordonance.dateOrdonanceSend!!,
                                    ordonance.hourOrdonanceSend!!.substring(0, 5)
                                )
                            )
                            listviewOrdoPatForDoctor!!.adapter = MyAdapterShowOrdonnancePatForDoc(
                                requireContext(),
                                R.layout.show_ordonnance_patient_to_doctor,
                                listOrdoPatForDoctor
                            )
                        }
                        listviewOrdoPatForDoctor!!.setOnItemClickListener { parent, view, position, id ->
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
                            dialog.findViewById<EditText>(R.id.TextRapport).visibility = View.GONE
                            dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE
                            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                                dialog.dismiss()
                                userDao.removeOrdonance(ordonance.idDoctor!!,
                                    ordonance.idPatient!!,
                                    ordonance.id!!,
                                    ordonance,
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

                    override fun failureOrdonance() {
                    }
                })
            }

            override fun failure() {

            }
        })
        addOrdonance!!.setOnClickListener {
            var intent = Intent(requireContext(), AddOrdonanceDoctorActivity::class.java)
            intent.putExtra("namePatentToOrdonance", myDataFromActivity)
            startActivity(intent)
        }
    }
}