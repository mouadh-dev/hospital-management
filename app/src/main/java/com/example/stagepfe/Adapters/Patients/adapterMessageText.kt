package com.example.stagepfe.Adapters.Patients


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.Models.Patient.ModelAddRDVPatient
import com.example.stagepfe.Models.Patient.ModelMessagePatient
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.example.stagepfe.Adapters.Patients.MyAdapterAddRDVPatient.viewHolder as viewHolder1

class adapterMessageText ( private val mCtx: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    var resources:Int = 0
    private val dataList: ArrayList<Message>? = null
    var msgTypeLeft:Int? = 0
    var msgTypeRight:Int? = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == msgTypeRight) {
            val itemView = LayoutInflater.from(mCtx).inflate(
                R.layout.chat_item_right,
                parent, false
            )
            return viewHolder(itemView)
        }else{
            val itemView = LayoutInflater.from(mCtx).inflate(
                R.layout.chat_item_left,
                parent, false
            )
            return viewHolder(itemView)
        }
    }

   inner class viewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

   }



    override fun getItemViewType(position: Int): Int {
        var fuser: FirebaseUser = FirebaseAuth.getInstance().currentUser
        if (dataList!![position].sender.equals(fuser.uid)){
            return msgTypeRight!!
        }else{
            return msgTypeLeft!!
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }
}

