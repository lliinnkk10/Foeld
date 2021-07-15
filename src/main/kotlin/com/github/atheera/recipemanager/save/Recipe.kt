package com.github.atheera.recipemanager.save

import java.io.Serializable

class Recipe : Serializable {

    // Strings
    lateinit var title: String
    lateinit var category: String
    lateinit var subCategory: String
    lateinit var instruction: String
    lateinit var ingredients: String

    // Ints
    var temperature: Int = 0
    var convTemperature: Int = 0

    // Booleans
    var egg: Boolean = false
    var gluten: Boolean = false
    var lactose: Boolean = false
    var vegan: Boolean = false
    var vegetarian: Boolean = false
    var favorite: Boolean = false

    fun toFormat() {
        println(
            "Title:\n $title \n" +
            "Category:\n $category \n" +
            "Sub Category:\n $subCategory \n" +
            "Instructions:\n $instruction \n" +
            "Ingredients:\n $ingredients \n" +
            "Temperature:\n $temperature \n" +
            "Converted Temperature:\n $convTemperature \n" +
            "Egg Free?:\n $egg \n" +
            "Gluten Free?:\n $gluten \n" +
            "Lactose Free?:\n $lactose \n" +
            "Vegan?:\n $vegan \n" +
            "Vegetarian?:\n $vegetarian \n" +
            "Favorite Recipe?:\n $favorite \n"
        )
    }
}