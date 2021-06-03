package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.MessageCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.example.stagepfe.entite.UserItem
import com.google.firebase.database.DatabaseError

class MyAdapterMessagePatient(var mCtx:Context, var resources:Int, var items:List<UserItem>): ArrayAdapter<UserItem>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view:View = layoutInflater.inflate(resources, null)

        var imageMsgPatient: de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.Image_Message__Patient)
        var nameMessagePatient: TextView = view.findViewById(R.id.Name_Message_Patient)

        var messageRecievedPatient: TextView = view.findViewById(R.id.Message_Recieved_Patient)
        var timeMessagePatient: TextView = view.findViewById(R.id.Time_Message_Patient)
        var mItemPatient:  UserItem = items[position]

        var id: String? = mItemPatient.id
        nameMessagePatient.text = mItemPatient.prenom + " " + mItemPatient.nom
        Glide.with(mCtx).load(mItemPatient.profilPhotos.toString()).into(imageMsgPatient)
        var userDao = UserDao()
        userDao.getMessage(object : MessageCallback {
            override fun success(message: Message) {
                if (message.reciever.equals(mItemPatient.id) || message.sender.equals(mItemPatient.id)){
                    messageRecievedPatient.text = message.message
                    timeMessagePatient.text = message.timemsg!!.substring(0,5)

                }

            }

            override fun failure(error: DatabaseError) {
            }
        })

//        messageRecievedPatient.text = mItemPatient.message
//        timeMessagePatient.text = mItemPatient.timemsg!!.substring(0,5)
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