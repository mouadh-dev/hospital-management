package com.example.stagepfe.Adapters.AgentLabo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stagepfe.Models.AgentLabo.ModelUpdateAgent
import com.example.stagepfe.R

class MyAdapterAgentUpdate (var mCtx: Context, var resources:Int, var items:List<ModelUpdateAgent>): BaseAdapter() {
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


        var titleUpdateAgent: TextView = view.findViewById(R.id.items_update_Agent)



        var items: ModelUpdateAgent = items[position]
        titleUpdateAgent.text = items.title





        return view

    }
}