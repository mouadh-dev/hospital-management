package com.example.stagepfe.Activity.Doctors

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV.OnItemClickListner
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.R
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline


class CheckRDVActivity : AppCompatActivity(), OnItemClickListner {

    var pickerDate: TextView? = null
    var secondTimeLine: DatePickerTimeline? = null

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_r_d_v)

        initView()
    }

    private fun initView() {


        pickerDate = findViewById(R.id.pick_date_TV)
        secondTimeLine = findViewById(R.id.second_time_line)
        pickerDate!!.text = intent.getStringExtra("key")
//        sendParamDialog = findViewById<TextView>(R.id.Duration_Appointment)
//
//        var time:String = sendParamDialog!!.text.toString()
        year = intent.getStringExtra("keyyear")!!.toInt()
        day = intent.getStringExtra("keyday")!!.toInt()
        month = intent.getStringExtra("keymonth")!!.toInt()

        secondTimeLine!!.setOnDateSelectedListener { year, month, day, index ->
            this.day = day
            this.year = year
            this.month = month
            pickerDate!!.text =
                "Veuillez choisir l'heure du rendez-vous pour ${this.day}/${this.month}/${this.year}"
        }


//            listDoctorAddRdv.add(ModelAddRDV("08:00", reserve()))

        //Recycler View
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view) as RecyclerView

        //Data

        //Data
        val dataList: MutableList<ModelAddRDV> = ArrayList()
        dataList.add(ModelAddRDV(R.drawable.itemone, "08:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "08:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "09:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "09:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "10:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "10:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "11:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "11:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "12:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "12:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "13:00", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "13:30", "vide"))
        dataList.add(ModelAddRDV(R.drawable.itemone, "14:00", "vide"))

        //Recycler View Adapter
        val mAdapter = MyAdapterAddRDV(dataList as ArrayList<ModelAddRDV>, this, this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(mAdapter)


    }
//    private fun reserve(): String {
//    }


    override fun onItemClick(item: ModelAddRDV, position: Int) {


        var time = item.time
        dialog(time)
    }

    private fun dialog(time: String) {


        var v = View.inflate(
            this,
            R.layout.dialog_add_rdv_doctor,
            null
        )
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        ////////////
        var userdao = UserDao()
        userdao.retrieveDataUser(this,
            UserItem(),
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    dialog.findViewById<TextView>(R.id.name_Doctor_Time)!!.text =
                        userItem.nom + " " + userItem.prenom
                    dialog.findViewById<TextView>(R.id.speciality_Doctor)!!.text =
                        userItem.speciality


                    dialog.findViewById<TextView>(R.id.daterdv)!!.text =
                        "${this@CheckRDVActivity.day}/${this@CheckRDVActivity.month}/${this@CheckRDVActivity.year}"
                    dialog.findViewById<TextView>(R.id.timerdv)!!.text = time

                }

                override fun failure() {
                }

            })


        dialog.findViewById<Button>(R.id.btn_confirm_rdv)!!.setOnClickListener {
            dialog.dismiss()
            var appointment = Appointment()
            var userItem = UserItem()
            appointment.nameDoctor =
                dialog.findViewById<TextView>(R.id.name_Doctor_Time)!!.text.toString()
            appointment.date = "$day/$month/$year"
            appointment.namePatient =
                dialog.findViewById<EditText>(R.id.Name_Patient_Dialog)!!.text.toString().trim()
            appointment.dispo = "reserveé"
            appointment.FinishOrNot = "Pas encore"

            appointment.hour = time
            userdao.insertappointment(appointment,userItem)
            var takenAppointment: TextView = findViewById(R.id.tvTextTaken)
            takenAppointment.text =
                dialog.findViewById<EditText>(R.id.Name_Patient_Dialog)!!.text.toString().trim()
            var toast = Toast.makeText(
                applicationContext,
                "Rendez-vous ajoute avec succès",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}
