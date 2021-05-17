package com.example.stagepfe.Dao

import com.example.stagepfe.entite.ProfilPhoto


interface ProfilPhotoCallback {
    fun success(profilPhoto: ProfilPhoto)
    fun failure()
}