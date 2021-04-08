package com.example.stagepfe.Patient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.R

class MyAdapterNotificationPatient(var mCtx: Context, var resources:Int, var items:List<ModelNotificationPatient>): ArrayAdapter<ModelNotificationPatient>(mCtx, resources, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
            var view: View = layoutInflater.inflate(resources, null)

            var imageNotifPatient: ImageView = view.findViewById(R.id.Notificatio_Image_Patient)
            var textNotificationPatient: TextView = view.findViewById(R.id.Notification_Patient_Text)

            var items: ModelNotificationPatient = items[position]
            imageNotifPatient.setImageDrawable(mCtx.resources.getDrawable(items.imgNotificationPatient))
            textNotificationPatient.text = items.notificationPatient



            return view

        }
    }
