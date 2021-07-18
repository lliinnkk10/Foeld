package com.github.atheera.recipemanager

import com.github.atheera.recipemanager.extras.LoadImage
import com.github.atheera.recipemanager.gui.WindowDisplay
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteSettingsFile
import java.io.File
import java.lang.StringBuilder

// Names of all the list types available
val categories = listOf("Desserts", "Extras", "Meats")
val subCatDesserts = listOf("Cake", "Chocolate", "Confection", "Cookie", "Custard", "Deep Fried", "Frozen", "Gelatin", "Pastry", "Pie", "Pudding", "Sweet Bread", "Tart")
val subCatExtras = listOf("Bread", "Greenthing", "Sauce", "Savory Pie", "Soup")
val subCatMeats = listOf("Beef", "Fish", "Other", "Plant Meat", "Poultry", "Pork")
val listCategories = listOf("Pros and Cons", "To Do")
val measures = listOf("L", "DL", "CL", "ML", "KG", "HG", "G", "MG", "TBSP", "TSP", "SPM", "PCS", "PINCH", "CUP")

// List types items
    // Pros/Cons
lateinit var listPCTitle: String
lateinit var listPCPos: MutableList<String>
lateinit var listPCNeg: MutableList<String>
    // To Do
lateinit var listTDTitle: String
lateinit var listTD: MutableList<String>
    // Recipes
lateinit var recipeTitle: String
lateinit var recipeCategory: String
lateinit var recipeSubCategory: String
lateinit var recipeInstructions: String
lateinit var recipeIngredients: MutableList<String>
var recipeTemperature: Int = 0
var recipeConvTemperature: Int = 0
var recipeEgg: Boolean = false
var recipeGluten: Boolean = false
var recipeLactose: Boolean = false
var recipeVegan: Boolean = false
var recipeVegetarian: Boolean = false

// Paths for saving location
const val setPath: String = "C://FOE/"
const val settingsPath: String = "${setPath}Settings.json"
lateinit var path: String
lateinit var recipePath: String
lateinit var listPath: String
lateinit var recipeFavPath: String

// Loading all images for use across the code
val backgroundImage = LoadImage().loadImage("notepadBG.png")!!
val imageIcon = LoadImage().loadImage(("icon.png"))!!
val logo = LoadImage().loadImage("logo.png")!!
val toolTip = LoadImage().loadImage("hoverTooltip.png")!!

fun main() {
    // This should always run first!
    onStartUp()

    // Opens the screen
    WindowDisplay()
}

fun onStartUp() {
    // Checks if Settings.json has been made, if not make it else gets information from it
    if(!File(settingsPath).exists()) {
        WriteSettingsFile(settingsPath)
        path = setPath
    } else {
        ReadSettings(settingsPath)
    }
    // Sets the save paths according to the Settings.json
    recipePath = "$path/Recipes/"
    listPath = "$path/Files/"
    recipeFavPath = recipePath + "Favorites/"

    createDirs()
}

// Creating all the categories and sub categories for less chance of error/crashes
fun createDirs() {
    val dir = Files()
    for(cats in categories) {
        when (cats) {
            categories[0] -> for(subCat in subCatDesserts) { dir.makeRecipeDir(cats, subCat) }
            categories[1] -> for(subCat in subCatExtras) { dir.makeRecipeDir(cats, subCat) }
            categories[2] -> for(subCat in subCatMeats) { dir.makeRecipeDir(cats, subCat) }
        }
    }
    for(lists in listCategories) {
        when (lists) {
            listCategories[0] -> dir.makeListDir(lists)
            listCategories[1] -> dir.makeListDir(lists)
        }
    }
    dir.makeDir(recipeFavPath)
}

fun removeFirstAndLast(string: String) : String {
    val sb = StringBuilder(string)
    sb.deleteCharAt(string.length - 1)
    sb.deleteCharAt(0)
    return sb.toString()
}