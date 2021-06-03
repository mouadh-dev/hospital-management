package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DatabaseError

class MyAdapterShowMessageListDoctor(
    var mCtx: Context,
    var resources: Int,
    var items: List<UserItem>

) : ArrayAdapter<UserItem>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageMsgDoctor: de.hdodenhof.circleimageview.CircleImageView =
            view.findViewById(R.id.Image_Message__Docteur)
        var nameMessagePatient: TextView = view.findViewById(R.id.Name_Message_Docteur)
        var messageRecievedPatient: TextView = view.findViewById(R.id.Message_Recieved_Docteur)
        var timeMessageDoctor: TextView = view.findViewById(R.id.Time_Message_Docteur)

        var mItemDoctor: UserItem = items[position]
        var id: String? = mItemDoctor.id
        nameMessagePatient.text = mItemDoctor.prenom + " " + mItemDoctor.nom
        Glide.with(mCtx).load(mItemDoctor.profilPhotos.toString()).into(imageMsgDoctor)
        var userDao = UserDao()
        userDao.getMessage(object : MessageCallback {
            override fun success(message: Message) {
                if (message.reciever.equals(mItemDoctor.id) || message.sender.equals(mItemDoctor.id)){
                    messageRecievedPatient.text = message.message
                    timeMessageDoctor.text = message.timemsg!!.substring(0,5)
                }

            }

            override fun failure(error: DatabaseError) {
            }
        })

//        messageRecievedPatient.text = mItemDoctor
//


        return view

    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): UserItem {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}