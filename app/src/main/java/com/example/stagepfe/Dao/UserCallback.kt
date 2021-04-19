package com.example.stagepfe.Dao

import com.example.stagepfe.entite.UserItem

interface UserCallback {

    fun onSuccess(userItem: UserItem)
    fun failure()



}