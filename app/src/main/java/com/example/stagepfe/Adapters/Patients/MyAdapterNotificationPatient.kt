package com.example.stagepfe.Adapters.Patients

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
import com.example.stagepfe.Models.Patient.ModelNotificationPatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem

class MyAdapterNotificationPatient(var mCtx: Context, var resources:Int, var items:List<ModelNotificationPatient>): ArrayAdapter<ModelNotificationPatient>(mCtx, resources, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
            var view: View = layoutInflater.inflate(resources, null)

            var imageNotifPatient: ImageView = view.findViewById(R.id.Notificatio_Image_Patient)
            var textNotificationPatient: TextView = view.findViewById(R.id.Notification_Patient_Text)
            var timeNotification: TextView = view.findViewById(R.id.time_notification)

            var items: ModelNotificationPatient = items[position]
            var userDao = UserDao()
//            userDao.retrieveCurrentDataUser(object : UserCallback {
//                override fun onSuccess(userItem: UserItem) {
//                    Glide.with(mCtx).load(userItem.profilPhotos.toString()).into(imageNotifPatient)
//                }
//
//                override fun failure() {
//                }
//            })

            imageNotifPatient.setImageDrawable(mCtx.resources.getDrawable(items.imgNotificationPatient))
            textNotificationPatient.text = items.notificationPatient
            timeNotification.text = items.timeNotification



            return view

        }
    }
