package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.stagepfe.Adapters.Administrateur.MyAdapterReclamationAdministrateur
import com.example.stagepfe.Dao.ReclamationCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Reclamation


class ReclamationAdministrateurFragment : Fragment() {

    var listviewReclamation: ListView? = null
    var listReclamation = ArrayList<Reclamation>()
    var reclamationAdapter: MyAdapterReclamationAdministrateur? = null
    var userDao = UserDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_reclamation_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewReclamation =view.findViewById<ListView>(R.id.listR├ęclamationAdministrateur)
    initAdpater()
        userDao.getReclamation(object : ReclamationCallback {
            override fun success(reclamation: Reclamation) {
                listReclamation.add(reclamation)
                listReclamation.sortWith(Comparator { o1, o2 -> o1.timeReclamation!!.compareTo(o2.timeReclamation!!) })
                System.currentTimeMillis()
                reclamationAdapter!!.notifyDataSetChanged()

            }

            override fun failure() {
            }
        })

//        listReclamation.sortWith(Comparator { o1, o2 -> o1.timeReclamation!!.compareTo(o2.timeReclamation!!) })
//        System.currentTimeMillis()

    }

    private fun initAdpater() {
        reclamationAdapter = MyAdapterReclamationAdministrateur(requireContext(), R.layout.list_reclamation_administrateur, listReclamation)
        listviewReclamation!!.adapter =reclamationAdapter
    }


}