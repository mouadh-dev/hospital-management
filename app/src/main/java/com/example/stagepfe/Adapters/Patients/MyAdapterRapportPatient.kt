package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Patient.ModelRDVPatient
import com.example.stagepfe.Models.Patient.ModelRapportPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.UserItem

class MyAdapterRapportPatient(var mCtx: Context, var resources:Int, var items:List<Rapports>) :ArrayAdapter<Rapports>(mCtx, resources,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var textNameDoctor: TextView = view.findViewById((R.id.Name_Doctor_Rapport))
        var textDateRapport: TextView = view.findViewById(R.id.Date_Rapport)
        var textHourRapport: TextView = view.findViewById(R.id.Hour_Rapport)

        var items: Rapports = items[position]
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (items.idDoctorRapport.equals(userItem.id)){
                    textNameDoctor.text = userItem.nom + " " + userItem.prenom
                }
            }

            override fun failure() {
            }
        })

//
        textDateRapport.text = items.dateRapport
        textHourRapport.text = items.hourRapport
        return view
    }
    fun getItemAt(position: Int): Rapports? {
        return items[position]
    }
}