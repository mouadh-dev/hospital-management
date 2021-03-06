package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stagepfe.R
import com.example.stagepfe.entite.AnalyseOrdonnance


class MyAdapterAnalyseReading (var mCtx: Context, var resources:Int, var items:List<AnalyseOrdonnance>): BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var descriptionAnalyse: TextView = view.findViewById(R.id.description_analyse_list)


        var mItem: AnalyseOrdonnance = items[position]
        descriptionAnalyse.text = mItem.descriptionAnalyse
        return view

    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
