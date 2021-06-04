package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelOrdonancePatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem

class MyAdapterOrdonancePatient(var mCtx: Context,var resources:Int, var items:List<Ordonance>): ArrayAdapter<Ordonance>(mCtx,resources,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var textTitleOrd: TextView = view.findViewById(R.id.Name_Ordonance)
        var textDateOrd: TextView = view.findViewById(R.id.Date_Ordonance)
        var textHourOrd: TextView = view.findViewById(R.id.Hour_Ordonance)
        var textCheckOrd: TextView = view.findViewById(R.id.Check_Ordonance)
        var textTypekOrd: TextView = view.findViewById(R.id.type_Ordonance_patient)
        var colorCheckOrd: LinearLayout = view.findViewById(R.id.Color_Check_Ordoance)
        var imageScannedOrd: ImageView = view.findViewById(R.id.Qr_Code_Ordonance)

        var item: Ordonance = items[position]
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (item.idDoctor.equals(userItem.id)){
                    textTitleOrd.text = userItem.nom + " " + userItem.prenom
                }
            }

            override fun failure() {
            }
        })

        textDateOrd.text = item.dateOrdonanceSend
        textHourOrd.text = item.hourOrdonanceSend
        textTypekOrd.text = item.typeOrdonnance
        textCheckOrd.text = item.taken
        colorCheckOrd.setBackgroundColor(item.color!!.toInt())
        return view
    }
}