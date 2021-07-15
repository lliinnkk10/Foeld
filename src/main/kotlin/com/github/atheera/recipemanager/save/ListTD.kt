package com.github.atheera.recipemanager.save

import java.io.Serializable

class ListTD : Serializable {

    lateinit var title: String
    var list = mutableListOf<String>()

    fun toFormat() {
        println(
            "Title: \n $title \n" +
            "Items to do: \n $list"
        )
    }

}