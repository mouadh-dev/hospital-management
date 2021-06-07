package com.example.stagepfe.Adapters.Administrateur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.*

class NewUsersAdapterAdmin (
    var mCtx: Context,
    var resources: Int,
    var items: List<UserItem>
): ArrayAdapter<UserItem>(mCtx, resources, items) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
            var view: View = layoutInflater.inflate(resources, null)

            var imageList: de.hdodenhof.circleimageview.CircleImageView =
                view.findViewById(R.id.new_user_image)
            var nameUtilisateur: TextView = view.findViewById(R.id.new_user_name)
            var userRole: TextView = view.findViewById(R.id.new_user_role)
            var acceptButton: Button = view.findViewById(R.id.accept_button)
            var declineButton: Button = view.findViewById(R.id.decline_button)


            var mItem: UserItem = items[position]
            nameUtilisateur.text = mItem.nom + " " + mItem.prenom
            userRole.text = mItem.demande

            var userDao = UserDao()
            userDao.populateSearch(object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    if (userItem.id.equals(mItem.id)){
                        if (mItem.profilPhotos != null){
                            Glide
                                .with(mCtx)
                                .load(mItem.profilPhotos)
                                .into(imageList)
                        }
                    }
                }

                override fun failure() {
                }
            })
            acceptButton.setOnClickListener {
                userDao.populateSearch(object : UserCallback {
                    override fun onSuccess(userItem: UserItem) {
                        if (userItem.id.equals(mItem.id)) {
                            mItem.role!!.add(mItem.demande!!)
                            userDao.addRole(mItem.id!!, mItem.role!!, object : UserCallback {
                                override fun onSuccess(userItem: UserItem) {

                                }

                                override fun failure() {
                                }
                            })
                        }
                    }

                    override fun failure() {
                    }
                })
//                var toast = Toast.makeText(mCtx,"test", Toast.LENGTH_SHORT)
//                toast.show()
            }


            return view
        }
        override fun getCount(): Int {
            return items.size
        }
        override fun getItem(position: Int): UserItem {
            return items[position]
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
