package com.example.stagepfe.Adapters.Administrateur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Administrateur.ModelReclamationAdministrateur
import com.example.stagepfe.Models.Doctors.ModelDoctorMessage
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Reclamation
import com.example.stagepfe.entite.UserItem

class MyAdapterReclamationAdministrateur(var mCtx: Context, var resources:Int, var items:ArrayList<Reclamation>): ArrayAdapter<Reclamation>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageListRec: de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.IVpatientImageList_reclamation)
        var namePatientRec: TextView = view.findViewById(R.id.TVnamePatientList_recalamtion)
        var tempsMessagerRec: TextView = view.findViewById(R.id.TvtimeMessageReclamation)
        var messagePatientRec: TextView = view.findViewById(R.id.TVmessageReclamation)
        var dateReclamation: TextView = view.findViewById(R.id.TvdateMessageReclamation)

        var mItem: Reclamation= items[position]
//        imageListRec.setImageDrawable(mCtx.resources.getDrawable(mItem.picRec))
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(mItem.idReclameur)){
                    namePatientRec.text = userItem.nom + " " + userItem.prenom
                    if (userItem.profilPhotos != null){
                        Glide.with(mCtx)
                            .load(userItem.profilPhotos)
                            .into(imageListRec)
                    }

                }
            }

            override fun failure() {
            }
        })

        tempsMessagerRec.text = mItem.timeReclamation!!.substring(0,5)
        messagePatientRec.text = mItem.description
        dateReclamation.text = mItem.dateReclamation
//        items.sortWith(Comparator { o1, o2 -> o1.timeReclamation!!.compareTo(o2.timeReclamation!!) })
        System.currentTimeMillis()
        return view
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Reclamation? {
        return items[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}