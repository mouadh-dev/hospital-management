package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Message
import com.google.firebase.database.DatabaseError


interface MessageCallback {
    fun success(message:Message)
    fun failure(error: DatabaseError)
}