package com.example.stagepfe.Dao

import com.example.stagepfe.entite.Rapports
import com.example.stagepfe.entite.Reclamation

interface ReclamationCallback {
    fun success(reclamation: Reclamation)
    fun failure()
}