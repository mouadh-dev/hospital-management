package com.example.stagepfe.Adapters.Doctor

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Models.Doctors.ModelAddRDV
import com.example.stagepfe.R

class MyAdapterAddRDV(private val dataList: ArrayList<ModelAddRDV>, private val mCtx: Context,var clickListner: OnItemClickListner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(mCtx).inflate(R.layout.list_add_rdv_doctor, parent, false)


        return viewHolder(itemView)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder: MyAdapterAddRDV.viewHolder = holder as viewHolder

        return holder.bindItem(dataList[position],clickListner)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    inner class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bindItem(item: ModelAddRDV, action:OnItemClickListner){
            var reserv:TextView = itemView.findViewById(R.id.tvTextTaken)
            var hour:TextView = itemView.findViewById(R.id.tvTime)
            var dispo: ImageView = itemView.findViewById<View>(R.id.ivDivider) as ImageView
            var s = dataList[position]
            hour.text = item.time
            reserv.text = item.reserv
            dispo.setImageDrawable(mCtx.resources.getDrawable(s.dispo))
            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }

        }
    }

    interface OnItemClickListner{
        fun onItemClick(item: ModelAddRDV, position: Int)
    }

}
