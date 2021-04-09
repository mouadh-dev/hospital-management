package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.stagepfe.Models.Patient.ModelOrdonancePatient
import com.example.stagepfe.R

class MyAdapterOrdonancePatient(var mCtx: Context,var resources:Int, var items:List<ModelOrdonancePatient>): ArrayAdapter<ModelOrdonancePatient>(mCtx,resources,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var textTitleOrd: TextView = view.findViewById(R.id.Name_Ordonance)
        var textDateOrd: TextView = view.findViewById(R.id.Date_Ordonance)
        var textHourOrd: TextView = view.findViewById(R.id.Hour_Ordonance)
        var textCheckOrd: TextView = view.findViewById(R.id.Check_Ordonance)
        var colorCheckOrd: LinearLayout = view.findViewById(R.id.Color_Check_Ordoance)
        var imageScannedOrd: ImageView = view.findViewById(R.id.Qr_Code_Ordonance)

        var item: ModelOrdonancePatient = items[position]

        textTitleOrd.text = item.titlOrdonance
        textDateOrd.text = item.dateOrdonance
        textHourOrd.text = item.hourOrdonance
        textCheckOrd.text = item.checkOrdonance
        colorCheckOrd.setBackgroundColor(mCtx.resources.getColor(item.colorCheckOrdonance))
        imageScannedOrd.setImageDrawable(mCtx.resources.getDrawable(item.imageScaned))
        return view
    }
}