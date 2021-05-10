package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Rapports

interface RapportCallback {
    fun success(rapport:Rapports)
    fun failure()
}