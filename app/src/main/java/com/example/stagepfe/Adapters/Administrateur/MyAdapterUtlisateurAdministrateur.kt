package com.example.stagepfe.Adapters.Administrateur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Administrateur.ModelUtilisateursAdministrateur
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.R

class MyAdapterUtlisateurAdministrateur (var mCtx: Context, var resources:Int, var items:List<ModelUtilisateursAdministrateur>): ArrayAdapter<ModelUtilisateursAdministrateur>(mCtx, resources, items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: ImageView = view.findViewById(R.id.image_list_utilisateur)
        var nameUtilisateur: TextView = view.findViewById(R.id.name_utilisateur_list)
        var natureUtilisateur: TextView = view.findViewById(R.id.nature_utilisateur)


        var mItem: ModelUtilisateursAdministrateur = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.picUtilisateur))
        nameUtilisateur.text = mItem.nomUtilisateur
        natureUtilisateur.text = mItem.natureUtilisateur
        return view
    }
}