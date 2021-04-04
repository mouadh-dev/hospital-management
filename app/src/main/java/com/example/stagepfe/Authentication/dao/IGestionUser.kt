package com.example.stagepfe.Authentication.dao

import com.example.stagepfe.Authentication.entite.UserItem

interface IGestionUser{


    fun insertUser(userItem: UserItem)

}