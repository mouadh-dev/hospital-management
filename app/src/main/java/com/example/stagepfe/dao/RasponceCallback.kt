package com.example.stagepfe.dao

import com.example.stagepfe.entite.UserItem

interface ResponseCallback {
    fun success( success:Boolean)
    fun failure( failure: UserItem)
}