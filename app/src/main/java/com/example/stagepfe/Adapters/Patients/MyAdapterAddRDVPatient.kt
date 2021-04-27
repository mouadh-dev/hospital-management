package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Activity.Patients.CheckRDVPatientActivity
import com.example.stagepfe.Models.Patient.ModelAddRDVPatient
import com.example.stagepfe.R

class MyAdapterAddRDVPatient(private val dataList: ArrayList<ModelAddRDVPatient>, private val mCtx: Context,
                             var clickListner:OnItemClickListner
)  :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(mCtx).inflate(R.layout.list_add_rdv_patient, parent, false)


        return viewHolder(itemView)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder: MyAdapterAddRDVPatient.viewHolder = holder as viewHolder

        return holder.bindItem(dataList[position],clickListner)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    inner class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bindItem(item: ModelAddRDVPatient, action: OnItemClickListner){
            var reserv: TextView = itemView.findViewById(R.id.tvTextTakenPatient)
            var hour: TextView = itemView.findViewById(R.id.tvTimePatient)
            var dispo: ImageView = itemView.findViewById<View>(R.id.ivDividerPatient) as ImageView
            var s = dataList[position]
            hour.text = item.timePat
            reserv.text = item.reservPat
            dispo.setImageDrawable(mCtx.resources.getDrawable(s.dispoPat))
            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }
    }

    interface OnItemClickListner{
        fun onItemClick(item: ModelAddRDVPatient, position: Int)
    }
}