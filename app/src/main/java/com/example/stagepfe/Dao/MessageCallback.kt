package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Message


interface MessageCallback {
    fun success(message:Message)
    fun failure()
}