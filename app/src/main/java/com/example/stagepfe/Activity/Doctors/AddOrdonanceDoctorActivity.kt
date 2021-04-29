package com.example.stagepfe.Activity.Doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Medicament
import com.example.stagepfe.entite.UserItem

class AddOrdonanceDoctorActivity : AppCompatActivity() {
    var addOne:ImageView? = null
    var removeOne:ImageView? = null
    var quantity:TextView? = null
    var nameMedicalment:AutoCompleteTextView? = null
    var medicaments: ArrayList<String?>?=null
    var adapter: ArrayAdapter<String>?=null
    var userdao = UserDao()
    var returnBack: ImageView? = null

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

        initAdapter()


        returnBack!!.setOnClickListener {
            var intent = Intent(this, ShowInfoPatientForDoctorActivity::class.java)
            startActivity(intent)
            finish()
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