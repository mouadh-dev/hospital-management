package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Patient.ModelMessagePatient
import com.example.stagepfe.R

class MyAdapterMessagePatient(var mCtx:Context, var resources:Int, var items:List<ModelMessagePatient>): ArrayAdapter<ModelMessagePatient>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view:View = layoutInflater.inflate(resources, null)

        var imageMsgPatient: ImageView = view.findViewById(R.id.Image_Message__Patient)
        var nameMessagePatient: TextView = view.findViewById(R.id.Name_Message_Patient)
        var messageRecievedPatient: TextView = view.findViewById(R.id.Message_Recieved_Patient)
        var timeMessagePatient: TextView = view.findViewById(R.id.Time_Message_Patient)
        var mItemPatient: ModelMessagePatient = items[position]
        imageMsgPatient.setImageDrawable(mCtx.resources.getDrawable(mItemPatient.imgmsgPatient))
        nameMessagePatient.text = mItemPatient.namePatient
        messageRecievedPatient.text = mItemPatient.messagePatient
        timeMessagePatient.text = mItemPatient.timemsgPatient


        return view

    }
}