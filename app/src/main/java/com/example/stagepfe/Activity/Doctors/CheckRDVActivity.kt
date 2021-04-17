package com.example.stagepfe.Activity.Doctors

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.stagepfe.Adapters.Doctor.MyAdapterAddRDV
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.R

import com.example.stagepfe.entite.UserItem
import com.github.badoualy.datepicker.DatePickerTimeline


class CheckRDVActivity : AppCompatActivity() {
    var listviewDoctorAddRdv: ListView? = null
    var listDoctorAddRdv = mutableListOf<ModelAddRDV>()
    var pickerDate:TextView? = null
    var secondTimeLine: DatePickerTimeline? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_r_d_v)

        initView()
    }

    private fun initView() {
       listviewDoctorAddRdv = findViewById<ListView>(R.id.list_add_RDV)

        pickerDate = findViewById(R.id.pick_date_TV)
        secondTimeLine = findViewById(R.id.second_time_line)
//        secondTimeLine!!.onDateSelectedListener
//        pickerDate!!.text = intent.getStringExtra("key")
        secondTimeLine!!.setOnDateSelectedListener { year, month, day, index ->
            var v = View.inflate(this, R.layout.progress_dialog, null)
            var builder = AlertDialog.Builder(this)
            builder.setView(v)

            var progressdialog = builder.create()
            progressdialog.show()
            progressdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            progressdialog.setCancelable(false)

            pickerDate!!.text = "Veuillez choisir l'heure du rendez-vous pour $day/$month/$year"
            listDoctorAddRdv.add(ModelAddRDV("08:00-08:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("08:30-09:00", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("09:00-09:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("09:30-10:00", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("10:00-10:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("10:30-11:00", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("11:00-11:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("11:30-12:00", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("12:00-12:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("12:30-13:00", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("13:00-13:30", "Reserveé"))
            listDoctorAddRdv.add(ModelAddRDV("13:30-14:00", "Reserveé"))

            listviewDoctorAddRdv!!.adapter = MyAdapterAddRDV(
                this,
                R.layout.list_add_rdv_doctor,
                listDoctorAddRdv
            )
            listviewDoctorAddRdv!!.setOnItemClickListener { parent, view, position, id ->
                if (position == 0){
//                    var test:String = listviewDoctorAddRdv!!.get(position).toString().trim()

                    dialog(year, month, day)
                }
            }
            progressdialog.dismiss()
        }



    }

    private fun dialog( year: Int, month: Int, day: Int) {



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
                    dialog.findViewById<TextView>(R.id.name_Doctor_Time).text =
                        "DR " + userItem.nom + " " + userItem.prenom
                    dialog.findViewById<TextView>(R.id.speciality_Doctor).text = userItem.speciality


                    dialog.findViewById<TextView>(R.id.daterdv).text = "$day/$month/$year"
//                    dialog.findViewById<TextView>(R.id.timerdv).text = test

                }

                override fun failure() {
                }

            })


        dialog.findViewById<Button>(R.id.btn_confirm_rdv)
            .setOnClickListener {
                dialog.dismiss()
            }
    }

}