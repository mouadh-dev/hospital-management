package com.example.stagepfe.Activity.Doctors

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonance
import com.example.stagepfe.Dao.OrdonanceCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Notification
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddOrdonanceDoctorActivity : AppCompatActivity() {
    var addOne: ImageView? = null
    var removeOne: ImageView? = null
    var nameMedicament: AutoCompleteTextView? = null
    var adapter: ArrayAdapter<String>? = null
    var userdao = UserDao()
    var returnBack: ImageView? = null
    var listViewMedicament: ListView? = null
    var listMedicament = mutableListOf<MedicamentOrdonance>()
    var listMedicamentOrdonance = arrayListOf<MedicamentOrdonance>()
    var addMedicament: Button? = null
    var addOrdonance: Button? = null
    var userDao = UserDao()
    var user = UserItem()
    var ordonance = Ordonance()
    var notification = Notification()
    var namesMedicament: ArrayList<String>? = null
    var quantity: TextView? = null
    var descriptionMedicament: EditText? = null
    var idDoctor: String? = null
    var nameDoctor: String? = null
    var namePatient: String? = null
    var idPAtient: String? = null
    private var adapterMedicament: MyAdapterOrdonance? = null

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()

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
        listViewMedicament = findViewById(R.id.List_Ordonance_add)
        addMedicament = findViewById(R.id.add_medicament_button)
        addOrdonance = findViewById(R.id.Add_Ordonance_Button)
        descriptionMedicament = findViewById(R.id.description_Et)

//        listMedicament = arrayListOf<MedicamentOrdonance>()
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
                var medicamentOrdonance = MedicamentOrdonance()
                medicamentOrdonance.nameMedicament = nameMedicament!!.text.toString()
                medicamentOrdonance.quantity = quantity!!.text.toString()
                medicamentOrdonance.description = descriptionMedicament!!.text.toString()
                listMedicament.add(medicamentOrdonance)
                adapterMedicament!!.notifyDataSetChanged()

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
                userDao.populateSearch(object : UserCallback {
                    @SuppressLint("NewApi")
                    override fun onSuccess(userItem: UserItem) {
                        var patient = intent.getStringExtra("namePatentToOrdonance")
                        var fullname = userItem.nom + " " + userItem.prenom
                        if (patient.equals(fullname)) {
                            namePatient = patient
                            idPAtient = userItem.id.toString()
                            ordonance.idDoctor = idDoctor
                            ordonance.idPatient = idPAtient
                            ordonance.medicament = listMedicamentOrdonance
                            ordonance.taken = "pas encore"
                            ordonance.color = Color.RED.toString()
                            ordonance.typeOrdonnance = "Ordonnance médicament"
                            ordonance.dateOrdonanceSend =
                                currentDateTime.format(DateTimeFormatter.ISO_DATE)
                            ordonance.hourOrdonanceSend =
                                currentDateTime.format(DateTimeFormatter.ISO_TIME)
                            notification.timeNotification =
                                currentDateTime.format(DateTimeFormatter.ISO_TIME)
                            notification.dateNotification =
                                currentDateTime.format(DateTimeFormatter.ISO_DATE)
                            notification.type = "Ordonnance médicament"
                            notification.idDoctor = idDoctor
                            notification.idPatient = idPAtient
                        }

                    }

                    override fun failure() {

                    }
                })
            }

            override fun failure() {
            }
        })
        addOrdonance!!.setOnClickListener {
            if (listViewMedicament!!.isEmpty()) {
                var text = "veuillez ajouter des medicaments"
                dialog(text)

            } else {
                filMedicament()
                userDao.insertordonance(idDoctor!!, idPAtient!!, ordonance, user, notification,
                    object : OrdonanceCallback {
                        override fun successOrdonance(ordonance: Ordonance) {
//                               startActivity(Intent(this@AddOrdonanceDoctorActivity,ShowInfoPatientForDoctorActivity::class.java))
                            finish()
                            Toast.makeText(
                                applicationContext,
                                "add ordo avec success",
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



        MedicamenteSearch()

    }

    private fun MedicamenteSearch() {
        val ref = FirebaseDatabase.getInstance().getReference("medicament")
        //val eventListener: ValueEventListener = object : ValueEventListener {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (ds in snapshot.children) {
                        var names = ds.key.toString()
                        namesMedicament!!.add(names)
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        //ref.addListenerForSingleValueEvent(eventListener)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    private fun filMedicament() {
        for (i in 0 until adapterMedicament!!.count) {
            val item = MedicamentOrdonance() // new one
            adapterMedicament!!.getItem(i)
            var view = adapterMedicament!!.getView(
                i,
                findViewById<TextView>(R.id.name_medicament_list),
                listViewMedicament!!
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
        adapterMedicament = MyAdapterOrdonance(this, R.layout.ord_add_list, listMedicament)
        listViewMedicament!!.adapter = adapterMedicament

        namesMedicament = ArrayList()
        adapter = ArrayAdapter<String>(
            this@AddOrdonanceDoctorActivity,
            android.R.layout.simple_list_item_1,
            namesMedicament!!
        )
        nameMedicament!!.setAdapter(adapter)
    }
}