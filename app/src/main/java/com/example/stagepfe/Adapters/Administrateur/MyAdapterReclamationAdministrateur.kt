package com.example.stagepfe.Adapters.Administrateur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Administrateur.ModelReclamationAdministrateur
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.R

class MyAdapterReclamationAdministrateur(var mCtx: Context, var resources:Int, var items:List<ModelReclamationAdministrateur>): ArrayAdapter<ModelReclamationAdministrateur>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageListRec: ImageView = view.findViewById(R.id.IVpatientImageList_reclamation)
        var namePatientRec: TextView = view.findViewById(R.id.TVnamePatientList_recalamtion)
        var tempsMessagerRec: TextView = view.findViewById(R.id.TvtimeMessageReclamation)
        var messagePatientRec: TextView = view.findViewById(R.id.TVmessageReclamation)

        var mItem: ModelReclamationAdministrateur= items[position]
        imageListRec.setImageDrawable(mCtx.resources.getDrawable(mItem.picRec))
        namePatientRec.text = mItem.nomRec
        tempsMessagerRec.text = mItem.messageRec
        messagePatientRec.text = mItem.tempsRec

        return view
    }
}