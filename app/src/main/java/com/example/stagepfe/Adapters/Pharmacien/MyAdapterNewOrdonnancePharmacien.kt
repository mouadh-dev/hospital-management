package com.example.stagepfe.Adapters.Pharmacien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.Models.Pharmacien.ModelNewOrdonnancePharmacien
import com.example.stagepfe.R

class MyAdapterNewOrdonnancePharmacien(var mCtx: Context, var resources:Int, var items:List<ModelNewOrdonnancePharmacien>): ArrayAdapter<ModelNewOrdonnancePharmacien>(mCtx, resources, items)   {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: ImageView = view.findViewById(R.id.IVpatientImageList_Pharmacie)
        var namePatient: TextView = view.findViewById(R.id.TVnamePatientList_Pharmacie)
        var dateNewOrdo : TextView = view.findViewById(R.id.TVDateOrdonnanceList_Pharmacie)
        var tempsNewOrd : TextView = view.findViewById(R.id.TVTimeOrdonnanceList_Pharmacie)

        var mItem: ModelNewOrdonnancePharmacien = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.picPat))
        namePatient.text = mItem.nomPat
        dateNewOrdo.text = mItem.dateNewOrd
        tempsNewOrd.text = mItem.tempsNewOrd

        return view
    }

}