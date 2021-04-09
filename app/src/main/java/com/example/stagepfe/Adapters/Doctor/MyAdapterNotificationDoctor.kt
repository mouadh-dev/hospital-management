package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelNotification
import com.example.stagepfe.R

class MyAdapterNotificationDoctor(var mCtx: Context, var resources:Int, var items:List<ModelNotification>): ArrayAdapter<ModelNotification>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageNotificationList: ImageView = view.findViewById(R.id.imageNottificationlist)
        var messageNotification: TextView = view.findViewById(R.id.TVmessageNotification)


        var mItem: ModelNotification = items[position]
        imageNotificationList.setImageDrawable(mCtx.resources.getDrawable(mItem.picture))
        messageNotification.text = mItem.message

        return view
    }
}