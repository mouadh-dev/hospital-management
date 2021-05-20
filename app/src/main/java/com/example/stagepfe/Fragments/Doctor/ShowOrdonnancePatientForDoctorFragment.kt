package com.example.stagepfe.Fragments.Doctor

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AddAnalyseOrdonnanceActivity
import com.example.stagepfe.Activity.Doctors.AddOrdonanceDoctorActivity
import com.example.stagepfe.Activity.Doctors.ShowInfoPatientForDoctorActivity
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonance
import com.example.stagepfe.Adapters.Doctor.MyAdapterShowOrdonnancePatForDoc
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem


class ShowOrdonnancePatientForDoctorFragment : Fragment() {
    var listviewOrdoPatForDoctor: ListView? = null
    var listOrdoPatForDoctor = mutableListOf<Ordonance>()
    private var adapterOrdonance: MyAdapterShowOrdonnancePatForDoc? = null

    var addOrdonance: ImageView? = null
    var userDao = UserDao()
    var fullNameDoctor: String? = null

    var listViewOrdReading: ListView? = null
    val listMedicament = mutableListOf<MedicamentOrdonance>()
    var adapterMedicament: MyAdapterOrdonance? = null

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


        initAdapter()
////////////////////////////////////////////addOrdonance////////////////////////////////////////////
        val activity: ShowInfoPatientForDoctorActivity? =
            activity as ShowInfoPatientForDoctorActivity?
        val myDataFromActivity: String = activity!!.getMyData().toString()

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNameDoctor = userItem.prenom + " " + userItem.nom
                if (userItem.ordonance != null){
                for (entry in userItem.ordonance!!.entries) {

                    var ordonance = entry.value
                    if (myDataFromActivity == ordonance.namepatientOrdo!! &&
                        ordonance.nameDoctorOrd!! == fullNameDoctor) {
                        val ordonanceList = Ordonance()
                        fillOrdonanceList(ordonanceList, ordonance)
                        listOrdoPatForDoctor.add(ordonanceList)
                        adapterOrdonance!!.notifyDataSetChanged()

                    }
                    }
                }
            }
            override fun failure() {
            }
        })
///////////////////////////////////////////////////////////////////////////////////////////////////

        listviewOrdoPatForDoctor!!.setOnItemClickListener { parent, view, position, id ->
            val ordonanceList = Ordonance()
            val ordonanceAdapter: Ordonance? = adapterOrdonance!!.getItem(position)
            fillOrdonanceList(ordonanceList, ordonanceAdapter!!)

            val v = View.inflate(requireContext(), R.layout.dialog_ordonance, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(v)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<TextView>(R.id.nameDoctor)
                .setText("DR" + " " + ordonanceList.nameDoctorOrd)
            dialog.findViewById<ListView>(R.id.List_Medicament_to_show).visibility = VISIBLE
            dialog.findViewById<TextView>(R.id.namePatient).setText(ordonanceList.namepatientOrdo)
            dialog.findViewById<Button>(R.id.btn_remove).setText("Supprimer")

            listViewOrdReading = dialog.findViewById<ListView>(R.id.List_Medicament_to_show)
            for (medicament in ordonanceList.medicament) {
                listMedicament.add(medicament)
            }
            adapterMedicament = MyAdapterOrdonance(
                requireContext(),
                R.layout.ord_add_list,
                listMedicament
            )
            listViewOrdReading!!.adapter = adapterMedicament

            adapterMedicament!!.notifyDataSetChanged()
            dialog.setOnDismissListener {
                listMedicament.clear()
            }

            dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {
                dialog.dismiss()
                userDao.removeOrdonance(ordonanceAdapter.idDoctor!!,
                    ordonanceAdapter.idPatient!!,
                    ordonanceAdapter.id!!,
                    ordonanceAdapter,
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
///////////////////////////////////////////////////////////////////////////////////////////////////
        addOrdonance!!.setOnClickListener {
            val v = View.inflate(requireContext(), R.layout.dialog_choix_ordonnance, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(v)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<ImageView>(R.id.addMedicamentOrd).setOnClickListener {
                var intent = Intent(requireContext(), AddOrdonanceDoctorActivity::class.java)
                intent.putExtra("namePatentToOrdonance", myDataFromActivity)
                startActivity(intent)
            }
            dialog.findViewById<ImageView>(R.id.addAnalyseOrd).setOnClickListener {
            var intent = Intent(requireContext(), AddAnalyseOrdonnanceActivity::class.java)
            intent.putExtra("namePatentToOrdonance", myDataFromActivity)
            startActivity(intent)
            }
        }
    }

    private fun fillOrdonanceList(ordonanceList: Ordonance, ordonance: Ordonance) {
        ordonanceList.dateOrdonanceSend = ordonance.dateOrdonanceSend
        ordonanceList.hourOrdonanceSend = ordonance.hourOrdonanceSend!!.substring(0, 5)
        ordonanceList.nameDoctorOrd = ordonance.nameDoctorOrd
        ordonanceList.idPatient = ordonance.idPatient
        ordonanceList.namepatientOrdo = ordonance.namepatientOrdo
        ordonanceList.medicament = ordonance.medicament
        ordonanceList.idDoctor = ordonance.idDoctor
        ordonanceList.id = ordonance.id
    }

    private fun initAdapter() {
        adapterOrdonance = MyAdapterShowOrdonnancePatForDoc(
            requireContext(),
            R.layout.show_ordonnance_patient_to_doctor,
            listOrdoPatForDoctor
        )
        listviewOrdoPatForDoctor!!.adapter = adapterOrdonance
    }

}