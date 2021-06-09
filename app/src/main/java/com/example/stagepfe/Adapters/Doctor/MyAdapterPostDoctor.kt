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
import com.example.stagepfe.Dao.LikeCallback
import com.example.stagepfe.Dao.PostCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.LikePost
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
                if (userItem.id.equals(mItem.idsenderPublication)) {
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
        userDao.getPost(object : PostCallback {
            override fun successPost(publication: Publication) {
                if (mItem.id.equals(publication.id)) {
                    if (publication.imagePublication != "") {
                        imageToPost.visibility = VISIBLE
                        Glide.with(mCtx)
                            .load(publication.imagePublication)
                            .into(imageToPost)
                    } else {
                        imageToPost.visibility = GONE
                    }

                }
            }

            override fun failurePost() {
            }
        })
        likeImage.tag = R.drawable.like_ic
        likeImage.setOnClickListener {
            if (likeImage.tag == R.drawable.like_ic) {
                likeImage.tag = R.drawable.red_like_ic
                likeImage.setImageResource(R.drawable.red_like_ic)
                userDao.retrieveCurrentDataUser(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        var likee = LikePost()
                        likee.idLiker = userItem.id
                        likee.idtaker = mItem.idsenderPublication
                        likee.idPost = mItem.id
                        userDao.sendLike(likee)
                    }

                    override fun failure() {
                    }
                })
            } else {
                likeImage.tag = R.drawable.like_ic
                likeImage.setImageResource(R.drawable.like_ic)
                userDao.getLike(object : LikeCallback {
                    override fun successLike(likePost: LikePost) {
                        if (mItem.id.equals(likePost.idPost)){
                            removeLike(likePost.id!!)

                        }
                    }

                    override fun failureLike() {
                    }
                })



            }

        }

        userDao.getLike(object : LikeCallback {
            override fun successLike(likePost: LikePost) {
                if (mItem.id.equals(likePost.idPost)){
                    likeImage.tag = R.drawable.red_like_ic
                    likeImage.setImageResource(R.drawable.red_like_ic)
                    var number = 0
                    number+=1
                    likeNumber.text = "$number"
                }


            }

            override fun failureLike() {
            }
        })

            return view
    }

    private fun removeLike(id: String) {
        var userDao = UserDao()
        userDao.removeLike(id, object : LikeCallback {
            override fun successLike(likePost: LikePost) {
            }

            override fun failureLike() {
            }
        })
    }
}