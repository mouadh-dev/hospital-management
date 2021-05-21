package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance


class MyAdapterOrdonancePharmacien(
    var mCtx: Context,
    var resources: Int,
    var items: List<MedicamentOrdonance>
): BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var nameMedicament: TextView = view.findViewById(R.id.name_medicament_Pharmacien)
        var quantity: TextView = view.findViewById(R.id.quantity_medicament_pharmacien)
        var descriptionOrd: TextView = view.findViewById(R.id.description_medicament_pharmacien)
        var checkBox:CheckBox = view.findViewById(R.id.checkBox_Pharmacien)


        var mItem: MedicamentOrdonance = items[position]

        nameMedicament.text = mItem.nameMedicament
        quantity.text = mItem.quantity
        descriptionOrd.text = mItem.description
        checkBox.setFocusable(false)
        checkBox.setFocusableInTouchMode(false);
        checkBox.isChecked = false
        checkBox.setChecked(MedicamentOrdonance .isChecked());


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
