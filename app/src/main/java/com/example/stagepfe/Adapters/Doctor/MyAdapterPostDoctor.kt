package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.R
import com.example.stagepfe.entite.Publication

class MyAdapterPostDoctorclass(var mCtx: Context, var resources:Int, var items:List<Publication>): ArrayAdapter<Publication>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imagePost: ImageView = view.findViewById(R.id.)
        var namePost: TextView = view.findViewById(R.id.)
        var timePost: TextView = view.findViewById(R.id.)
        var hourPost: TextView = view.findViewById(R.id.)
        var textPost: TextView = view.findViewById(R.id.)




        var mItem: Publication = items[position]


        return view
    }
}