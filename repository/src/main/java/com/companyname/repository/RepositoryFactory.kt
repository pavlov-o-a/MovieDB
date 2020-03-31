package com.companyname.repository

object RepositoryFactory{

    fun getRepository(): Repository = RepositoryImpl()
}