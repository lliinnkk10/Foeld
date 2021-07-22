package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.dw
import com.github.atheera.recipemanager.path
import com.github.atheera.recipemanager.save.objects.Settings
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadSettings(saveLocation: String) {

    private var parser = JsonParser()
    private var settings = Settings()

    init {

        try {
            val reader = FileReader(saveLocation)
            println("File loaded at: $saveLocation")
            dw.add("File loaded at: $saveLocation")
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseSettingsObjects(obj)
        } catch (e: ParseException) {
            dw.exc(e)
            e.printStackTrace()
        } catch(e: FileNotFoundException) {
            dw.exc(e)
            e.printStackTrace()
        } catch(e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }

    }

    private fun parseSettingsObjects(file: JsonObject) {

        val saveLocation = file.get("saveLocation").asString
        path = saveLocation
        settings.saveLocation = saveLocation

    }

}