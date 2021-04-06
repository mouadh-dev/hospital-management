package com.example.stagepfe.Patient

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stagepfe.Authentication.entite.UserItem
import com.example.stagepfe.R


class AccountPatientActivity : AppCompatActivity() {
    var listview:ListView? = null
    var list = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_patient)

        listview = findViewById<ListView>(R.id.list)
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))

        listview!!.adapter = MyAdapter(this, R.layout.doctors_list, list)







        var home = PatientAccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.ContainerFragmentPatient, home)
            .commit()

    }
//    fun fetchData():ArrayList<Model>{
//        var list:ArrayList<Model>?= ArrayList()
//        list!!.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//        list.add(Model("Dr Foulen Fouleni","Generaliste", R.drawable.doctor_ic))
//
//        return list
//    }
}