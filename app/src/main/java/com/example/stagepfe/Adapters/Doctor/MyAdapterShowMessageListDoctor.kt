package com.example.stagepfe.Adapters.Doctor

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
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem

class MyAdapterShowMessageListDoctor (var mCtx: Context, var resources:Int, var items:List<String>): ArrayAdapter<String>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageMsgDoctor: de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.Image_Message__Docteur)
        var nameMessagePatient: TextView = view.findViewById(R.id.Name_Message_Docteur)

//        var messageRecievedPatient: TextView = view.findViewById(R.id.Message_Recieved_Patient)
//        var timeMessageDoctor: TextView = view.findViewById(R.id.Time_Message_Docteur)
        var mItemDoctor: String = items[position]
        var id:String? = mItemDoctor
        var userDao = UserDao()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (userItem.id.equals(id)){
                    nameMessagePatient.text = userItem.nom + " " + userItem.prenom
                    Glide.with(mCtx).load(userItem.profilPhotos.toString()).into(imageMsgDoctor)
                }
            }

            override fun failure() {
            }
        })


//        imageMsgPatient.setImageDrawable(mCtx.resources.getDrawable(mItemPatient.imgmsgPatient!!))
//        Glide.with(mCtx).load(mItemDoctor.imgmsgPatient).into(imageMsgDoctor)
//        nameMessagePatient.text = mItemPatient.nameReciever
//        messageRecievedPatient.text = mItemPatient.message
//        timeMessageDoctor.text = mItemDoctor.timemsg!!.substring(0,5)


        return view

    }
    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): String {
        return items[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}