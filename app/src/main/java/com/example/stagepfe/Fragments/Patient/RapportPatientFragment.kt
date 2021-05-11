package com.example.stagepfe.Fragments.Patient

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.stagepfe.Adapters.Patients.MyAdapterRapportPatient
import com.example.stagepfe.Dao.RapportCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem


class RapportPatientFragment : Fragment() {
var listRapportPatient: ListView? = null
    var list = mutableListOf<ModelRapportPatient>()
    var userDao = UserDao()

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


        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.getRapport(object : RapportCallback {
                    override fun success(rapport: Rapports) {
                        if (rapport.namPatientRapport.equals(userItem.nom + " " + userItem.prenom)){
                            list.add(ModelRapportPatient(rapport.nameDoctorRapport.toString(), "11/04/2020","11:00"))
                            initAdapter()
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


//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))
//        list.add(ModelRapportPatient("DR Foulen", "11/04/2020","11:00"))

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
        listRapportPatient!!.adapter = MyAdapterRapportPatient(requireContext(),R.layout.rapport_list_patient,list)

    }


}