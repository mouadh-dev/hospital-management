package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.R

class MyAdapterAddRDV(var mCtx: Context, var resources:Int, var items:List<ModelAddRDV>): ArrayAdapter<ModelAddRDV>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var duratioRDVDoctorTV: TextView = view.findViewById(R.id.Duration_Appointment)
        var checkAddRDV: TextView = view.findViewById(R.id.check_RDV_taken)

        var mItem: ModelAddRDV = items[position]

        duratioRDVDoctorTV.text = mItem.durationRDV
        checkAddRDV.text = mItem.checkRDV


        return view
    }
}