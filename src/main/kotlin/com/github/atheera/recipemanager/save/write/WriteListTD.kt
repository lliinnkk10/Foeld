package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.objects.ListTD
import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteListTD(type: String, title: String, list: MutableList<String>) {

    private fun createTDObject(title: String, list: MutableList<String>) : ListTD {
        val l = ListTD()

        l.title = title
        l.list = list

        return l
    }

    init {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = Files().getListFile(type, title)
        val listTD = createTDObject(title, list)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(listTD, writer) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}