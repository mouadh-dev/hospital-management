package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelPatientListForDoctorProfil
import com.example.stagepfe.R

class MyAdapterPatientListForDoctorProfil(var mCtx: Context, var resources:Int, var items:List<ModelPatientListForDoctorProfil>): ArrayAdapter<ModelPatientListForDoctorProfil>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: ImageView = view.findViewById(R.id.IVpatientImageListForProfilDoctor)
        var nomDuPatient: TextView = view.findViewById(R.id.TVnamePatientListForProfilDoctor)




        var mItem: ModelPatientListForDoctorProfil = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.pic_patient_for_profil_doctor))
        nomDuPatient.text = mItem.nom_patient_for_profil_doctor

        return view
    }
}