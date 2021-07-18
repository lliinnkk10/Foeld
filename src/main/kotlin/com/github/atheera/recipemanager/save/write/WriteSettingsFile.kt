package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.save.objects.Settings
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.settingsPath
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import java.io.IOException

class WriteSettingsFile(saveLocation: String) {

    private fun createSettingsObject(saveLocation: String) : Settings {
        val s = Settings()

        s.saveLocation = saveLocation

        return s
    }

    init {
        ReadSettings(settingsPath)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(settingsPath)
        val sett = createSettingsObject(saveLocation)
        try{
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(sett, writer) }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}