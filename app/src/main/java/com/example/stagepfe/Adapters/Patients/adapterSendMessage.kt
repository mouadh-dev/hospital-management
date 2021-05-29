package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Activity.Patients.BottomBarPatientActivity
import com.example.stagepfe.Models.Patient.Model
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message

class adapterSendMessage(var mCtx: Context, var resources:Int, var items:List<Message>): ArrayAdapter<Message>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var messageText: TextView = view.findViewById(R.id.show_message)

        var mItem: Message = items[position]

        messageText.text = mItem.message

        return view
    }

}