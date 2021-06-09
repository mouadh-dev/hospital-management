package com.example.stagepfe.Dao

import com.example.stagepfe.entite.LikePost
import com.example.stagepfe.entite.Publication


interface PostCallback {
    fun successPost(likes: LikePost,publication: Publication)
    fun failurePost()
}