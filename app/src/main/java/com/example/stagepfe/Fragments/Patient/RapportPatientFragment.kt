package com.example.stagepfe.Fragments.Patient

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.stagepfe.Adapters.Doctor.MyAdapterRapport
import com.example.stagepfe.Adapters.Patients.MyAdapterRapportPatient
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem


class RapportPatientFragment : Fragment() {
var listRapportPatient: ListView? = null
    var list = mutableListOf<Rapports>()
    var namePatientRapport: String? = null
    private var adapterRapportPatient: MyAdapterRapportPatient? = null
    var fullNamepatient: String? = null
    var userDao = UserDao()
    var fullDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_rapport_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listRapportPatient = view.findViewById(R.id.List_Rapport)
        initAdapter()

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNamepatient = userItem.nom + " " + userItem.prenom

                userDao.getRapport(object : RapportCallback {
                    override fun success(rapport: Rapports) {
                        namePatientRapport = rapport.namPatientRapport

                        if (rapport.namPatientRapport.equals(fullNamepatient) &&
                            (rapport.hourRapport + " " + rapport.hourRapport) != fullDate){
                            fullDate = rapport.hourRapport + " " + rapport.hourRapport
                            var rapportList = Rapports()
                            rapportList.hourRapport = rapport.hourRapport!!.substring(0,5)
                            rapportList.dateRapport = rapport.dateRapport
                            rapportList.textRapport = rapport.textRapport
                            rapportList.id = rapport.id
                            rapportList.idPatientRapport = rapport.idPatientRapport
                            rapportList.idDoctorRapport = rapport.idDoctorRapport
                            rapportList.nameDoctorRapport = rapport.nameDoctorRapport
                            rapportList.namPatientRapport = rapport.namPatientRapport
                            rapportList.specialityDoctor = rapport.specialityDoctor
                            list.add(rapportList)
                            adapterRapportPatient!!.notifyDataSetChanged()

                            listRapportPatient!!.setOnItemClickListener { parent, view, position, id ->
                                dialog(rapport.textRapport)
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

        listRapportPatient!!.setOnItemClickListener { parent, view, position, id ->
            var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
            var builder = AlertDialog.Builder(requireContext())
            builder.setView(v)

            var dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<TextView>(R.id.TitleDialog).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
            dialog.findViewById<EditText>(R.id.TextRapport).visibility = View.VISIBLE
            dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE

            var patientrapportadapter: Rapports? = adapterRapportPatient!!.getItemAt(position)

            dialog.findViewById<EditText>(R.id.TextRapport).setText(patientrapportadapter!!.textRapport)
            dialog.findViewById<Button>(R.id.btn_confirm).setText("Modifier")
            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                patientrapportadapter.textRapport = dialog.findViewById<EditText>(R.id.TextRapport).text.toString()

                //userDao.updateRapport(patientrapportadapter.idDoctorRapport!!,
                  //  patientrapportadapter.idPatientRapport!!,
                    //patientrapportadapter.id!!,
                    //patientrapportadapter,
                    //object : ResponseCallback {
                      //  override fun success(medicament: String) {

                        //}

                        //override fun success() {

                        //}

                        //override fun failure() {

                        //}
                    //})
                dialog.dismiss()
            }

        }





//        listRapportPatient!!.adapter = MyAdapterRapportPatient(requireContext(),R.layout.rapport_list_patient,list)

    }

    private fun dialog(textRapport: String?) {
        var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
        var builder = AlertDialog.Builder(requireContext())
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<ImageView>(R.id.CheckDialog).visibility = View.GONE

        dialog.findViewById<TextView>(R.id.TitleDialog).text = textRapport.toString()
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
        dialog.findViewById<Button>(R.id.btn_confirm).visibility = View.GONE
    }

    private fun initAdapter() {
        adapterRapportPatient = MyAdapterRapportPatient(requireContext(),R.layout.rapport_list_patient,list)
        listRapportPatient!!.adapter = adapterRapportPatient

    }


}