package com.example.stagepfe.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.R

class MyAdapterListPatient(var mCtx: Context, var resources:Int, var items:List<ModelPatientList>): ArrayAdapter<ModelPatientList>(mCtx, resources, items)  {
   override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
       var view: View = layoutInflater.inflate(resources, null)

       var imageList: ImageView = view.findViewById(R.id.IVpatientImageList)
       var nomDuPatient: TextView = view.findViewById(R.id.TVnamePatientList)




       var mItem: ModelPatientList = items[position]
       imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.pic_patient))
       nomDuPatient.text = mItem.nom_patient

       return view
   }
}