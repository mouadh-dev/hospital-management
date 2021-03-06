package com.example.stagepfe.Adapters.Doctor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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

class MyAdapterPostDoctor(var mCtx: Context, var resources: Int, var items: List<Publication>) :
    ArrayAdapter<Publication>(mCtx, resources, items) {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imagePost: ImageView = view.findViewById(R.id.Image_publication__Docteur)
        var namePost: TextView = view.findViewById(R.id.Name_publication_Docteur)
        var hourPost: TextView = view.findViewById(R.id.Time_publication_Docteur)
        var datePost: TextView = view.findViewById(R.id.Date_publication_Docteur)
        var textPost: TextView = view.findViewById(R.id.text_publication_Docteur)
        var imageToPost: ImageView = view.findViewById(R.id.imagepooost)
        var likeNumber: TextView = view.findViewById(R.id.like_text_doctor)
        var likeImage: ImageView = view.findViewById(R.id.like_Image_doctor)
        var comment: ImageView = view.findViewById(R.id.comment_text_doctor)


        var mItem: Publication = items[position]
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(mItem.idUser)) {
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
        hourPost.text = mItem.heurePublication!!.substring(0, 5)
        textPost.text = mItem.textPublication
        if (mItem.imagePublication != "null") {
            imageToPost.visibility = VISIBLE
            Glide.with(mCtx)
                .load(mItem.imagePublication)
                .into(imageToPost)
        } else {
            imageToPost.visibility = GONE
        }
        return view
    }

}