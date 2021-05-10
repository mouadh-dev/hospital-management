package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonance
import com.example.stagepfe.Dao.OrdonanceCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelOrdonance
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem


class AddOrdonanceDoctorActivity : AppCompatActivity() {
    var addOne: ImageView? = null
    var removeOne: ImageView? = null
    var nameMedicament: AutoCompleteTextView? = null
    var adapter: ArrayAdapter<String>? = null
    var userdao = UserDao()
    var returnBack: ImageView? = null
    var listViewOrd: ListView? = null
    var listOrd = mutableListOf<ModelOrdonance>()
    var listMedicamentOrdonance = arrayListOf<MedicamentOrdonance>()
    var addMedicament: Button? = null
    var addOrdonance: Button? = null
    var userDao = UserDao()
    var user = UserItem()
    var ordonance = Ordonance()
    var medicamentOrdonance = MedicamentOrdonance()
    var quantity: TextView? = null
    var descriptionMedicament: EditText? = null
    var idDoctor: String? = null
    var nameDoctor: String? = null
    var namePatient: String? = null
    var idPAtient: String? = null
    private var test: MyAdapterOrdonance? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ordonance_doctor)
        initView()
    }

    private fun initView() {
        addOne = findViewById(R.id.add_one)
        removeOne = findViewById(R.id.remove_one)
        quantity = findViewById(R.id.quantity_medicament)
        nameMedicament = findViewById<AutoCompleteTextView>(R.id.medicament_EditText)
        returnBack = findViewById(R.id.return_back_Ord)
        listViewOrd = findViewById(R.id.List_Ordonance_add)
        addMedicament = findViewById(R.id.add_medicament_button)
        addOrdonance = findViewById(R.id.Add_Ordonance_Button)
        descriptionMedicament = findViewById(R.id.description_Et)

        listOrd = arrayListOf<ModelOrdonance>()
        initAdapter()


        returnBack!!.setOnClickListener {

//            var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
//            startActivity(intent)
            finish()
        }

        ////////////////////////////////////////////listView Medicament////////////////////////////////////
        addMedicament!!.setOnClickListener {
            if (nameMedicament!!.text.isEmpty() || quantity!!.text.equals("0")) {
                var text = "veuillez ajouter des medicaments"
                dialog(text)
            } else {
                listOrd.add(
                    ModelOrdonance(
                        nameMedicament!!.text.toString(),
                        quantity!!.text.toString(),
                        descriptionMedicament!!.text.toString()
                    )
                )
                test!!.notifyDataSetChanged()


                quantity!!.setText("0")
                descriptionMedicament!!.text.clear()
                nameMedicament!!.text.clear()
            }
        }


////////////////////////////////////////////add ordonance tofirebase////////////////////////////////

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                idDoctor = userItem.id.toString()
                nameDoctor = userItem.prenom + " " + userItem.nom
            }

            override fun failure() {
            }
        })

        addOrdonance!!.setOnClickListener {


            if (listViewOrd!!.isEmpty()) {
                var text = "veuillez ajouter des medicaments"
                dialog(text)

            } else {
                filMedicament()

                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        var patient = intent.getStringExtra("namePatentToOrdonance")
                        var fullname = userItem.nom + " " + userItem.prenom
                        if (patient.equals(fullname)) {
                            namePatient = patient
                            idPAtient = userItem.id.toString()
                            println("mm :: " + namePatient + " !! " + idPAtient)


                        }
                    }

                    override fun failure() {

                    }
                })


                ordonance.idDoctor = idDoctor
                ordonance.nameDoctorOrd = nameDoctor
                ordonance.namepatientOrdo = namePatient
                ordonance.idPatient = idPAtient
                ordonance.medicament = listMedicamentOrdonance

                userDao.insertordonance(idDoctor!!, idPAtient!!, ordonance, user,
                    object : OrdonanceCallback {
                        override fun successOrdonance(ordonance: Ordonance) {
//                               startActivity(Intent(this@AddOrdonanceDoctorActivity,ShowInfoPatientForDoctorActivity::class.java))
                            finish()
                            Toast.makeText(
                                applicationContext,
                                "add ordo avec succe",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun failureOrdonance() {

                        }
                    })


            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        addOne!!.setOnClickListener {
            var count = quantity!!.text.toString().toInt()
            count += 1
            quantity!!.text = count.toString()
        }
        removeOne!!.setOnClickListener {
            var count = quantity!!.text.toString().toInt()
            if (count > 0) {
                count -= 1
                quantity!!.text = count.toString()
            }
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////
    private fun filMedicament() {
        for (i in 0 until test!!.count) {
            val item = MedicamentOrdonance() // new one
            test!!.getItem(i)
            var view = test!!.getView(
                i,
                findViewById<TextView>(R.id.name_medicament_list),
                listViewOrd!!
            )


            item.nameMedicament =
                view.findViewById<TextView>(R.id.name_medicament_list).text.toString()
            item.quantity =
                view.findViewById<TextView>(R.id.quantity_medicament_list).text.toString()
            item.description =
                view.findViewById<TextView>(R.id.description_ord_list).text.toString()

            listMedicamentOrdonance.add(item)
        }
    }

    private fun dialog(text: String) {
        var v = View.inflate(this, R.layout.fragment_dialog, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.TitleDialog).text = text
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun initAdapter() {
        test = MyAdapterOrdonance(this, R.layout.ord_add_list, listOrd)
        listViewOrd!!.adapter = test


    }
}