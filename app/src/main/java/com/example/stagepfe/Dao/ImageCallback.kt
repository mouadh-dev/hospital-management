package com.example.stagepfe.Dao

import android.net.Uri
import com.example.stagepfe.entite.Ordonance

interface ImageCallback {
    fun success(uri: Uri)
    fun failure()
}