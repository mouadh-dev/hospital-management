package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Patient.Model
import com.example.stagepfe.R

class MyAdapter(var mCtx:Context, var resources:Int, var items:List<Model>): ArrayAdapter<Model>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view:View = layoutInflater.inflate(resources, null)

        var imageList:ImageView = view.findViewById(R.id.image_list)
        var nameDoctor:TextView= view.findViewById(R.id.name_doctor_list)
        var specialityDoctor:TextView = view.findViewById(R.id.speciality_)

        var mItem: Model = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.img))
        nameDoctor.text = mItem.name
        specialityDoctor.text = mItem.speciality

        return view
    }

}