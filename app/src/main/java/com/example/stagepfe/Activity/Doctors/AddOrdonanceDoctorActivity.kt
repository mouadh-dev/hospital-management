package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddOrdonance
import com.example.stagepfe.Adapters.Doctor.MyAdapterRdvDoctor
import com.example.stagepfe.Dao.OrdonanceCallback
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelAddOrdonance
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.R
import com.example.stagepfe.entite.Medicament
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem

class AddOrdonanceDoctorActivity : AppCompatActivity() {
    var addOne:ImageView? = null
    var removeOne:ImageView? = null
    var quantity:TextView? = null
    var descriptionMedicament:EditText? = null
    var nameMedicalment:AutoCompleteTextView? = null
    var medicaments: ArrayList<String?>?=null
    var adapter: ArrayAdapter<String>?=null
    var userdao = UserDao()
    var returnBack: ImageView? = null
    var listViewOrd: ListView? = null
    var listOrd = mutableListOf<ModelAddOrdonance>()
    var addMedicament:Button? = null
    var addOrdonance:Button? = null
    var userDao = UserDao()
    var userItem = UserItem()
    var ordonance = Ordonance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ordonance_doctor)
        initView()
    }

    private fun initView() {
        addOne = findViewById(R.id.add_one)
        removeOne = findViewById(R.id.remove_one)
        quantity = findViewById(R.id.quantity_medicament)
        nameMedicalment = findViewById(R.id.medicament_EditText)
        returnBack = findViewById(R.id.return_back_Ord)
        listViewOrd = findViewById(R.id.List_Ordonance_add)
        addMedicament = findViewById(R.id.add_medicament_button)
        descriptionMedicament = findViewById(R.id.description_Et)
        addOrdonance= findViewById(R.id.Add_Ordonance_Button)

        initAdapter()


        returnBack!!.setOnClickListener {
            var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
            startActivity(intent)
            finish()
        }
////////////////////////////////////////////listView Medicament////////////////////////////////////
        addOrdonance!!.setOnClickListener {
            var fullName = userItem.prenom + " " + userItem.nom
            var idDoc = userItem.id
            userDao.populateSearch(userItem, object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    ordonance.description = descriptionMedicament!!.text.toString()
                    ordonance.nameDoctorOrd = fullName
                    ordonance.idDoctor = idDoc
                    ordonance.idPatient = userItem.id
                    ordonance.quantity = quantity!!.text.toString()
                    userDao.insertordonance(ordonance, userItem, userItem.id.toString(),
                        object : OrdonanceCallback {
                            override fun successOrdonance(ordonance: Ordonance) {
                                var toast = Toast.makeText(
                                    applicationContext,
                                    "Ordonance ajoute avec succès",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                            override fun failureOrdonance() {

                            }
                        })
                }

                override fun failure() {

                }
            })
        }
 ////////////////////////////////////////////listView Medicament////////////////////////////////////
        addMedicament!!.setOnClickListener {


        listOrd.add(ModelAddOrdonance(
            nameMedicalment!!.text.toString(),
            quantity!!.text.toString(),
            descriptionMedicament!!.text.toString()))

        listViewOrd!!.adapter = MyAdapterAddOrdonance(
            this,
            R.layout.ord_add_list,
            listOrd
        )
            quantity!!.setText("0")
            descriptionMedicament!!.text.clear()
            nameMedicalment!!.text.clear()
        }

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

            medicaments = ArrayList()
            adapter  = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                medicaments as ArrayList<String>
            )
            nameMedicalment!!.setAdapter(adapter)
/////////////////////////////////////////Suggestion Search//////////////////////////////////////////

            userdao.MedicamenteSearch( object : ResponseCallback {
                override fun success(medicament: String) {
                    medicaments!!.add(medicament)
                }

                override fun success() {

                }
                override fun failure() {

                }
            })
            adapter!!.notifyDataSetChanged()

        }

}