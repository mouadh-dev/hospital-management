package com.example.stagepfe.Adapters.Patients

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

class MyAdapterPostPatient(var mCtx: Context, var resources:Int, var items:List<Publication>): ArrayAdapter<Publication>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imagePostpatient: ImageView = view.findViewById(R.id.Image_publication_Patient)
        var namePostpatient: TextView = view.findViewById(R.id.Name_publication_Patient)
        var hourPostpatient: TextView = view.findViewById(R.id.Time_publication_Patient)
        var datePostpatient: TextView = view.findViewById(R.id.Date_publication_Patient)
        var textPostpatient: TextView = view.findViewById(R.id.text_publication_Patient)
        var imageToPostpatient: ImageView = view.findViewById(R.id.image_post_patient)
        var likeNumberpatient: TextView = view.findViewById(R.id.like_text_patient)
        var likeImagepatient: ImageView = view.findViewById(R.id.like_Image_patient)
        var commentpatient: ImageView = view.findViewById(R.id.comment_text_patient)




        var mItem: Publication = items[position]
        var userDao = UserDao()
//        userDao.populateSearch(object : UserCallback {
//            override fun onSuccess(userItem: UserItem) {
//                if (userItem.id.equals(mItem.idsenderPublication)){
//                    namePostpatient.text = userItem.nom + " " + userItem.prenom
//                    Glide.with(mCtx)
//                        .load(userItem.profilPhotos)
//                        .into(imagePostpatient)
//                }
//            }
//
//            override fun failure() {
//            }
//        })
        datePostpatient.text = mItem.datePublication
        hourPostpatient.text = mItem.heurePublication!!.substring(0,5)
        textPostpatient.text = mItem.textPublication

//        userDao.getPost(object : PostCallback {
//            override fun successPost(publication: Publication) {
//                if (mItem.id.equals(publication.id)) {
//                    if (publication.imagePublication != "") {
//                        imageToPostpatient.visibility = View.VISIBLE
//                        Glide.with(mCtx)
//                            .load(publication.imagePublication)
//                            .into(imageToPostpatient)
//                    } else {
//                        imageToPostpatient.visibility = View.GONE
//                    }
//
//                }
//            }
//
//            override fun failurePost() {
//            }
//        })
        likeImagepatient.tag = R.drawable.like_ic
        likeImagepatient.setOnClickListener {
            if (likeImagepatient.tag == R.drawable.like_ic) {
                likeImagepatient.tag = R.drawable.red_like_ic
                likeImagepatient.setImageResource(R.drawable.red_like_ic)
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
            } else {
                likeImagepatient.tag = R.drawable.like_ic
                likeImagepatient.setImageResource(R.drawable.like_ic)
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



            }

        }

//        userDao.getLike(object : LikeCallback {
//            override fun successLike(likePost: LikePost) {
//                if (mItem.id.equals(likePost.idPost)){
//                    likeImagepatient.tag = R.drawable.red_like_ic
//                    likeImagepatient.setImageResource(R.drawable.red_like_ic)
//                    var number = 0
//                    number+=1
//                    likeNumberpatient.text = "$number"
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