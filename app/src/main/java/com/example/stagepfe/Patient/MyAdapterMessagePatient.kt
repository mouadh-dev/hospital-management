package com.example.stagepfe.Patient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class MyAdapterMessagePatientvar (var mCtx: Context, var resources:Int, var items:List<Model>): ArrayAdapter<Model>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

    }
}