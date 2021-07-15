package com.github.atheera.recipemanager.save

import com.github.atheera.recipemanager.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadListTD(fileName: String) {

    private var parser = JsonParser()
    private var list = ListTD()
    private val file = listPath.plus("${listCategories[1]}/$fileName")

    init {

        try {
            val reader = FileReader(file)
            println("File loaded at: $file")
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseListObject(obj)
            list.toFormat()
        } catch(e: ParseException) {
            e.printStackTrace()
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch(e: IOException) {
            e.printStackTrace()
        }

    }

    private fun parseListObject(file: JsonObject) {

        listTD = mutableListOf()

        val title = file.get("title").asString
        list.title = title
        listTDTitle = title

        val listToDo = file.get("list").asJsonArray
        for(i in 0 until listToDo.size()) {
            listTD.add(removeFirstAndLast(listToDo[i].toString()))
            list.list.add(removeFirstAndLast(listToDo[i].toString()))
        }

    }

}