package com.example.stagepfe.Adapters.Administrateur

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
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

class MyAdapterPostAdmin(var mCtx: Context, var resources:Int, var items:List<Publication>): ArrayAdapter<Publication>(mCtx, resources, items)  {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imagePostadmin: ImageView = view.findViewById(R.id.Image_publication__admin)
        var namePostadmin: TextView = view.findViewById(R.id.Name_publication_admin)
        var hourPostadmin: TextView = view.findViewById(R.id.Time_publication_admin)
        var datePostadmin: TextView = view.findViewById(R.id.Date_publication_admin)
        var textPostadmin: TextView = view.findViewById(R.id.text_publication_admin)
        var imageToPostadmin: ImageView = view.findViewById(R.id.image_post_admin)
        var likeNumberadmin: TextView = view.findViewById(R.id.like_text_admin)
        var likeImageadmin: ImageView = view.findViewById(R.id.like_Image_admin)
        var commentadmin: ImageView = view.findViewById(R.id.comment_image_admin)




        var mItem: Publication = items[position]
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(mItem.idUser)){
                    namePostadmin.text = userItem.nom + " " + userItem.prenom
                    Glide.with(mCtx)
                        .load(userItem.profilPhotos)
                        .into(imagePostadmin)
                }
            }

            override fun failure() {
            }
        })
        datePostadmin.text = mItem.datePublication
        hourPostadmin.text = mItem.heurePublication!!.substring(0,5)
        textPostadmin.text = mItem.textPublication


//        userDao.getPost(object : PostCallback {
//            override fun successPost(publication: Publication) {
//                if (mItem.id.equals(publication.id)) {
//                    if (publication.imagePublication != "") {
//                        imageToPostadmin.visibility = View.VISIBLE
//                        Glide.with(mCtx)
//                            .load(publication.imagePublication)
//                            .into(imageToPostadmin)
//                    } else {
//                        imageToPostadmin.visibility = View.GONE
//                    }
//
//                }
//            }
//
//            override fun failurePost() {
//            }
//        })
        likeImageadmin.tag = R.drawable.like_ic
//        likeImageadmin.setOnClickListener {
//            if (likeImageadmin.tag == R.drawable.like_ic) {
//                likeImageadmin.tag = R.drawable.red_like_ic
//                likeImageadmin.setImageResource(R.drawable.red_like_ic)
//                userDao.retrieveCurrentDataUser(object : UserCallback {
//                    override fun onSuccess(userItem: UserItem) {
//                        var likee = LikePost()
//                        likee.idLiker = userItem.id
//                        likee.idtaker = mItem.idsenderPublication
//                        likee.idPost = mItem.id
//                        userDao.sendLike(likee)
//                    }
//
//                    override fun failure() {
//                    }
//                })
//            }
//            else {
//                likeImageadmin.tag = R.drawable.like_ic
//                likeImageadmin.setImageResource(R.drawable.like_ic)
//                userDao.getLike(object : LikeCallback {
//                    override fun successLike(likePost: LikePost) {
//                        if (mItem.id.equals(likePost.idPost)){
//                            removeLike(likePost.id!!)
//
//                        }
//                    }
//
//                    override fun failureLike() {
//                    }
//                })
//
//
//
//            }
//
//        }

//        userDao.getLike(object : LikeCallback {
//            override fun successLike(likePost: LikePost) {
//                if (mItem.id.equals(likePost.idPost)){
//                    likeImageadmin.tag = R.drawable.red_like_ic
//                    likeImageadmin.setImageResource(R.drawable.red_like_ic)
//                    var number = 0
//                    number+=1
//                    likeNumberadmin.text = "$number"
//                }
//
//
//            }
//
//            override fun failureLike() {
//            }
//        })

        return view
    }

    private fun removeLike(id: String) {
        var userDao = UserDao()
//        userDao.removeLike(object : LikeCallback {
//            override fun successLike(likePost: LikePost) {
//            }
//
//            override fun failureLike() {
//            }
//        })
    }
}