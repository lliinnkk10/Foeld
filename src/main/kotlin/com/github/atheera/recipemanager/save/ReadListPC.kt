package com.github.atheera.recipemanager.save

import com.github.atheera.recipemanager.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.lang.StringBuilder
import java.text.ParseException

class ReadListPC(fileName: String) {

    private var parser = JsonParser()
    private var list = ListPC()
    private val file = listPath.plus("${listCategories[0]}/$fileName")

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

        listPCNeg = mutableListOf()
        listPCPos = mutableListOf()

        val title = file.get("title").asString
        list.title = title
        listPCTitle = title

        val posList = file.get("posList").asJsonArray
        for(i in 0 until posList.size()) {
            list.posList.add(removeFirstAndLast(posList[i].toString()))
            listPCPos.add(removeFirstAndLast(posList[i].toString()))
        }

        val negList = file.get("negList").asJsonArray
        for(i in 0 until negList.size()) {
            list.negList.add(removeFirstAndLast(negList[i].toString()))
            listPCNeg.add(removeFirstAndLast(negList[i].toString()))
        }
    }



}