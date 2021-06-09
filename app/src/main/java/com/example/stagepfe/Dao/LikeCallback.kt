package com.example.stagepfe.Dao

import com.example.stagepfe.entite.LikePost

interface LikeCallback {
    fun successLike(likePost: LikePost)
    fun failureLike()
}