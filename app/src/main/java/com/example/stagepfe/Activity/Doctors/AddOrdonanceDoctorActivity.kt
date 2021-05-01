package com.example.stagepfe.Activity.Doctors

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
    var addOne:ImageView? = null
    var removeOne:ImageView? = null
    var nameMedicament:AutoCompleteTextView? = null
    var adapter: ArrayAdapter<String>?=null
    var userdao = UserDao()
    var returnBack: ImageView? = null
    var listViewOrd: ListView? = null
    var listOrd = mutableListOf<ModelOrdonance>()
    var listMedicamentOrdonance = ArrayList<MedicamentOrdonance>()
    var addMedicament:Button? = null
    var addOrdonance:Button? = null
    var userDao = UserDao()
    var userItem = UserItem()
    var ordonance = Ordonance()
    var medicamentOrdonance = MedicamentOrdonance()
    var quantity :TextView? = null
    var descriptionMedicament:EditText? = null

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
        addOrdonance= findViewById(R.id.Add_Ordonance_Button)
        descriptionMedicament = findViewById(R.id.description_Et)


        initAdapter()


        returnBack!!.setOnClickListener {

//            var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
//            startActivity(intent)
//            finish()
        }

 ////////////////////////////////////////////listView Medicament////////////////////////////////////
        addMedicament!!.setOnClickListener {
        listOrd.add(
            ModelOrdonance(
                nameMedicament!!.text.toString(),
                quantity!!.text.toString(),
                descriptionMedicament!!.text.toString()
            )
        )
        listViewOrd!!.adapter = MyAdapterOrdonance(this, R.layout.ord_add_list, listOrd)






            quantity!!.setText("0")
            descriptionMedicament!!.text.clear()
            nameMedicament!!.text.clear()
        }

////////////////////////////////////////////add ordonance tofirebase////////////////////////////////
        addOrdonance!!.setOnClickListener {

           userDao.retrieveCurrentDataUser( object : UserCallback {
               override fun onSuccess(userItem: UserItem) {
                   ordonance.nameDoctorOrd = userItem.prenom + " " + userItem.nom
                   ordonance.idDoctor = userItem.id
                   ordonance.medicament = listMedicamentOrdonance
                   ordonance.namepatientOrdo = intent.getStringExtra("test")
                   userDao.insertordonance(ordonance, userItem,
                       object : OrdonanceCallback {
                           override fun successOrdonance(ordonance: Ordonance) {
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

               override fun failure() {

               }
           })
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        addOne!!.setOnClickListener {
            var count = quantity!!.text.toString().toInt()
            count+= 1
            quantity!!.text = count.toString()
        }
        removeOne!!.setOnClickListener {
            var count = quantity!!.text.toString().toInt()
            if (count > 0){
            count-= 1
            quantity!!.text = count.toString()
        }
        }

    }

    private fun initAdapter() {

    }
}