package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Publication


interface PostCallback {
    fun successPost(publication: Publication)
    fun failurePost()
}