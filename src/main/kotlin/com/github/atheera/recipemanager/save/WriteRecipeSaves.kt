package com.github.atheera.recipemanager.save

import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteRecipeSaves(cat: String, subCat: String, title: String, instr: String, ingr: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean, fav: Boolean) {
    private fun createRecipeObject(cat: String, subCat: String, title: String, instr: String, ingr: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean, fav: Boolean): Recipe {
        val r = Recipe()
        r.title = title
        r.category = cat
        r.subCategory = subCat
        r.instruction = instr
        r.ingredients = ingr
        r.temperature = temp
        r.convTemperature = cTemp
        r.egg = egg
        r.gluten = gluten
        r.lactose = lactose
        r.vegan = vegan
        r.vegetarian = veget
        r.favorite = fav
        r.toFormat()
        return r
    }

    init {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = Files().getRecipeFile(cat, subCat, title)
        val rec = createRecipeObject(cat, subCat, title, instr, ingr, temp, cTemp, egg, gluten, lactose, vegan, veget, fav)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(rec, writer) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}