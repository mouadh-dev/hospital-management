package com.example.stagepfe.Fragments.Administrateur

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.stagepfe.Adapters.Administrateur.MyAdapterReclamationAdministrateur
import com.example.stagepfe.Adapters.Administrateur.MyAdapterUtlisateurAdministrateur
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.Models.Administrateur.ModelReclamationAdministrateur
import com.example.stagepfe.Models.Administrateur.ModelUtilisateursAdministrateur
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem


class UtlisitaeursAdministrateurFragment : Fragment() {

    var listviewUtilisateur: ListView? = null
    var listUtilisateur = mutableListOf<UserItem>()
    var adapterUsers: MyAdapterUtlisateurAdministrateur? = null
    var userDao = UserDao()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_utlisitaeurs_administrateur, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listviewUtilisateur =view.findViewById<ListView>(R.id.ListUtilisateursAdministrateur)
        initAdapter()
        userDao.populateSearch(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                if (!(userItem.role!!.contains("Admin"))){
                    listUtilisateur.add(userItem)
                    adapterUsers!!.notifyDataSetChanged()
                }

            }

            override fun failure() {
            }
        })
//
//        for (pos in 0 until adapterUsers!!.count) {
//            adapterUsers!!.getItem(pos)
//            var view = adapterUsers!!.getView(pos,view.findViewById(R.id.SupprimerButtom),listviewUtilisateur!!)
//            view.findViewById<Button>(R.id.SupprimerButtom).setOnClickListener {
//                var toast= Toast.makeText(requireContext(),"test",Toast.LENGTH_SHORT)
//                toast.show()
//            }
//        }



    }

    private fun initAdapter() {
        adapterUsers = MyAdapterUtlisateurAdministrateur(requireContext(), R.layout.utilisateur_list_administrateur, listUtilisateur)
        listviewUtilisateur!!.adapter = adapterUsers
    }


}