package com.example.stagepfe.Adapters.Patients

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
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication
import com.example.stagepfe.entite.UserItem

class MyAdapterPostPatient(var mCtx: Context, var resources:Int, var items:List<Publication>): ArrayAdapter<Publication>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imagePost: ImageView = view.findViewById(R.id.Image_publication__Patient)
        var namePost: TextView = view.findViewById(R.id.Name_publication_Patient)
        var hourPost: TextView = view.findViewById(R.id.Time_publication_Patient)
        var datePost: TextView = view.findViewById(R.id.Date_publication_Patient)
        var textPost: TextView = view.findViewById(R.id.text_publication_Patient)




        var mItem: Publication = items[position]
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(mItem.idsenderPublication)){
                    namePost.text = userItem.nom + " " + userItem.prenom
                    Glide.with(mCtx)
                        .load(userItem.profilPhotos)
                        .into(imagePost)
                }
            }

            override fun failure() {
            }
        })
        datePost.text = mItem.datePublication
        hourPost.text = mItem.heurePublication!!.substring(0,5)
        textPost.text = mItem.textPublication

        return view
    }
}