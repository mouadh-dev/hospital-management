package com.example.stagepfe.dao

import com.example.stagepfe.entite.UserItem

interface ResponseCallback {
    fun success()
    fun failure()
}