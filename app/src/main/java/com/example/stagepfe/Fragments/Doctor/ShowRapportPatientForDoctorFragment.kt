package com.example.stagepfe.Fragments.Doctor

import android.annotation.SuppressLint
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
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem


class ShowRapportPatientForDoctorFragment : Fragment() {

    var listviewRapport: ListView? = null
    var listRapport = mutableListOf<Rapports>()
    var addRapport: ImageView? = null
    var nameDoctorRapport: String? = null
    var spcialityDoctorRapport: String? = null
    private var adapterRapport: MyAdapterRapport? = null
    var userDao = UserDao()
    var fullDate: String? = null
    var fullNameDoctor: String? = null
    var fullNamePatient:String? = null

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

//    override fun onCreateContextMenu(
//        menu: ContextMenu,
//        v: View,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        val inflater = menuInflater
//    }
//    fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_main, menu)
//    }
    @SuppressLint("CutPasteId")
    private fun initView(view: View) {
        addRapport = view.findViewById(R.id.Add_Rapport)
        listviewRapport = view.findViewById<ListView>(R.id.showRapportPatForDoctorr)

        initAdapter()


        val activity: ShowInfoPatientForDoctorActivity? =
            requireActivity() as ShowInfoPatientForDoctorActivity?
        val myDataFromActivity: String = activity!!.getMyData().toString()



        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(user: UserItem) {
                        if (user.rapports != null){
                            for (entry in user.rapports!!.entries){
                                var rapport = entry.value
                                if (myDataFromActivity == user.nom + " " + user.prenom
                                    &&
                                    rapport.idDoctorRapport == userItem.id
                                ){
                                    var rapportList = Rapports()
                                    rapportList.hourRapport = rapport.hourRapport!!.substring(0, 5)
                                    rapportList.dateRapport = rapport.dateRapport
                                    rapportList.textRapport = rapport.textRapport
                                    rapportList.id = rapport.id
                                    rapportList.idPatientRapport = rapport.idPatientRapport
                                    rapportList.idDoctorRapport = rapport.idDoctorRapport
                                    rapportList.specialityDoctor = rapport.specialityDoctor
                                    listRapport.add(rapportList)

                                    adapterRapport!!.notifyDataSetChanged()
                                }
                            }
                        }
                    }

                    override fun failure() {
                    }
                })
//                userDao.getRapport(object : RapportCallback {
//                    override fun success(rapport: Rapports) {
//                        if (rapport.idDoctorRapport.equals(userItem.id) &&
//                            (rapport.dateRapport + " " + rapport.hourRapport) != fullDate
//                        ) {
//                            fullDate = rapport.dateRapport + " " + rapport.hourRapport
//                        }
//
//                    }
//
//                    override fun failure() {
//                    }
//                })

            }

            override fun failure() {
            }
        })


//    listviewRapport!!.setOnItemLongClickListener { parent, view, position, id ->
//        var rapportadapter: Rapports? = adapterRapport!!.getItemAt(position)
//
//        true
//    }





        listviewRapport!!.setOnItemClickListener { parent, view, position, id ->
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
            dialog.findViewById<Button>(R.id.btn_Delete_rapport).visibility = View.VISIBLE
            dialog.findViewById<Button>(R.id.btn_Delete_rapport).visibility = View.VISIBLE

            var rapportadapter: Rapports? = adapterRapport!!.getItemAt(position)

            dialog.findViewById<EditText>(R.id.TextRapport).setText(rapportadapter!!.textRapport)
            dialog.findViewById<Button>(R.id.btn_confirm).setText("Modifier")
            dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                rapportadapter.textRapport = dialog.findViewById<EditText>(R.id.TextRapport).text.toString()

                userDao.updateRapport(rapportadapter.idDoctorRapport!!,
                    rapportadapter.idPatientRapport!!,
                    rapportadapter.id!!,
                    rapportadapter,
                    object : ResponseCallback {
                        override fun success(medicament: String) {

                        }

                        override fun success() {

                        }

                        override fun failure() {

                        }
                    })
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btn_Delete_rapport).setOnClickListener{
                userDao.removeRapport(rapportadapter.idDoctorRapport!!,rapportadapter.idPatientRapport!!,rapportadapter.id!!
                    ,object : ResponseCallback{
                        override fun success(medicament: String) {

                        }

                        override fun success() {

                        }

                        override fun failure() {

                        }

                    })
                dialog.dismiss()
            }
        }

        addRapport!!.setOnClickListener {
            var intent = Intent(activity, AddRapportDoctorActivity::class.java)
            intent.putExtra("namePatentToRapport", myDataFromActivity)
            startActivity(intent)
    }

    }
    private fun initAdapter() {
            adapterRapport = MyAdapterRapport(requireContext(), R.layout.list_rapport, listRapport)
            listviewRapport!!.adapter = adapterRapport
    }


}
