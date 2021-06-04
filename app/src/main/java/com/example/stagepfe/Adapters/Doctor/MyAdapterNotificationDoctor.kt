package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.R

class MyAdapterNotificationDoctor(var mCtx: Context, var resources:Int, var items:List<ModelNotification>): ArrayAdapter<ModelNotification>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageNotificationList: ImageView = view.findViewById(R.id.imageNottificationlist)
        var messageNotification: TextView = view.findViewById(R.id.TVmessageNotification)
        var timeNotification: TextView = view.findViewById(R.id.time_notification_doctor)


        var items: ModelNotification = items[position]
        var userDao = UserDao()
        imageNotificationList.setImageDrawable(mCtx.resources.getDrawable(items.picture))
        messageNotification.text = items.message
        timeNotification.text = items.timeNotificationDoctor

        return view
    }
}