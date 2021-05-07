package com.example.stagepfe.Adapters.Pharmacien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelUpdateDoctor
import com.example.stagepfe.Models.Pharmacien.ModelUpdatePharmacien
import com.example.stagepfe.R

class MyAdapterUpdatePharmacien(var mCtx: Context, var resources:Int, var items:List<ModelUpdatePharmacien>): BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): Any {
        return items[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var titleUpdatePharmacien: TextView = view.findViewById(R.id.items_update_pharmacien)



        var items: ModelUpdatePharmacien = items[position]
        titleUpdatePharmacien.text = items.title





        return view

    }
}