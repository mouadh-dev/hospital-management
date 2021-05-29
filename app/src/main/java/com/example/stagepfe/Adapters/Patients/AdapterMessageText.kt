package com.example.stagepfe.Adapters.Patients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagepfe.R
import com.example.stagepfe.entite.Message
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdapterMessageText(private val mCtx: Context, private val mMsg: List<Message>) :
    RecyclerView.Adapter<AdapterMessageText.ViewHolder>() {
    var fuser: FirebaseUser? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MSG_TYPE_RIGHT) {
            val view: View =
                LayoutInflater.from(mCtx).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(mCtx).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg: Message = mMsg[position]
        holder.show_message.text = msg.message
    }

    override fun getItemCount(): Int {
        return mMsg.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var show_message: TextView = itemView.findViewById(R.id.show_message)

    }

    override fun getItemViewType(position: Int): Int {
        fuser = FirebaseAuth.getInstance().currentUser
        return if (mMsg[position].sender.equals(fuser!!.uid)) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }

    companion object {
        const val MSG_TYPE_LEFT = 0
        const val MSG_TYPE_RIGHT = 1
    }


}

