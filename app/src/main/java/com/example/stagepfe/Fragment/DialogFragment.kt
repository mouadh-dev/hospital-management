package com.example.stagepfe.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stagepfe.R


class DialogFragment : Fragment() {

    fun onCreateDialog(savedInstanceState: Bundle): AlertDialog.Builder? {
        return activity?.let {

            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }
            builder?.setMessage("hello")!!.setTitle("test")
            builder.apply {
                setPositiveButton("ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                    })
            val dialog: AlertDialog? = builder?.create()
        }
    }
}}

