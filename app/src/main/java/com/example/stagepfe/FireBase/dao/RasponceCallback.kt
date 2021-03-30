package com.example.fragmentfirebase.firebase.dao

import com.example.stagepfe.FireBase.dao.UserItem

interface ResponseCallback {
    fun success( success:Boolean)
    fun failure( failure: UserItem)
}