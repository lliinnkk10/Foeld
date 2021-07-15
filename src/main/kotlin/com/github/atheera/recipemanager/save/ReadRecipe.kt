package com.github.atheera.recipemanager.save

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadRecipe(fileName: String) {

    var parser = JsonParser()
    var recipe = Recipe()

    init {

        try {
            val reader = FileReader(fileName)
            println("File loaded at: $fileName")
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseRecipeObject(obj)
            recipe.toFormat()
        } catch(e: ParseException) {
            e.printStackTrace()
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch(e: IOException) {
            e.printStackTrace()
        }


    }

    private fun parseRecipeObject(file: JsonObject) {

        val title = file.get("title").asString
        recipe.title = title

        val category = file.get("category").asString
        recipe.category = category

        val subCategory = file.get("subCategory").asString
        recipe.subCategory = subCategory

        val instruction = file.get("instruction").asString
        recipe.instruction = instruction

        val ingredients = file.get("ingredients").asString
        recipe.ingredients = ingredients

        val temperature = file.get("temperature").asInt
        recipe.temperature = temperature

        val convTemperature = file.get("convTemperature").asInt
        recipe.convTemperature = convTemperature

        val egg = file.get("egg").asBoolean
        recipe.egg = egg

        val gluten = file.get("gluten").asBoolean
        recipe.gluten = gluten

        val lactose = file.get("lactose").asBoolean
        recipe.lactose = lactose

        val vegan = file.get("vegan").asBoolean
        recipe.vegan = vegan

        val vegetarian = file.get("vegetarian").asBoolean
        recipe.vegetarian = vegetarian

        val favorite = file.get("favorite").asBoolean
        recipe.favorite = favorite

    }

}