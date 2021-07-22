package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadRecipe
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ItemEvent
import java.io.File
import java.io.IOException
import javax.swing.*

class SavedDessertRecipePanel : JPanel() {

    var jcbCat: JComboBox<String>
    var clCat: CardLayout
    var jpCat: JPanel
    var jpContent: JPanel

    var jpCakeCard = JPanel()
    var jpChocolateCard = JPanel()
    var jpConfectionCard = JPanel()
    var jpCookieCard = JPanel()
    var jpCustardCard = JPanel()
    var jpFriedCard = JPanel()
    var jpFrozenCard = JPanel()
    var jpGelatinCard = JPanel()
    var jpPastryCard = JPanel()
    var jpPieCard = JPanel()
    var jpPuddingCard = JPanel()
    var jpBreadCard = JPanel()
    var jpTartCard = JPanel()
    var jspCakeCard = createJSP(jpCakeCard)
    var jspChocolateCard = createJSP(jpChocolateCard)
    var jspConfectionCard = createJSP(jpConfectionCard)
    var jspCookieCard = createJSP(jpCookieCard)
    var jspCustardCard = createJSP(jpCustardCard)
    var jspFriedCard = createJSP(jpFriedCard)
    var jspFrozenCard = createJSP(jpFrozenCard)
    var jspGelatinCard = createJSP(jpGelatinCard)
    var jspPastryCard = createJSP(jpPastryCard)
    var jspPieCard = createJSP(jpPieCard)
    var jspPuddingCard = createJSP(jpPuddingCard)
    var jspBreadCard = createJSP(jpBreadCard)
    var jspTartCard = createJSP(jpTartCard)


    lateinit var selectedCat: String
    lateinit var selectedButton: JButton

    init {
        border = setBorder("Select which dessert category to load buttons for")

        // Initialize
        jcbCat = JComboBox(subCatDesserts.toTypedArray())
        clCat = CardLayout()
        jpCat = JPanel(clCat)
        jpContent = JPanel(MigLayout())


        jcbCat.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                selectedCat = it.item as String

                clCat.show(jpCat, selectedCat)
                updateUI()
            }
        }

        clCat.show(jpCat, subCatDesserts[0])

        add(jpContent)
        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jspCakeCard, subCatDesserts[0])
        jpCat.add(jspChocolateCard, subCatDesserts[1])
        jpCat.add(jspConfectionCard, subCatDesserts[2])
        jpCat.add(jspCookieCard, subCatDesserts[3])
        jpCat.add(jspCustardCard, subCatDesserts[4])
        jpCat.add(jspFriedCard, subCatDesserts[5])
        jpCat.add(jspFrozenCard, subCatDesserts[6])
        jpCat.add(jspGelatinCard, subCatDesserts[7])
        jpCat.add(jspPastryCard, subCatDesserts[8])
        jpCat.add(jspPieCard, subCatDesserts[9])
        jpCat.add(jspPuddingCard, subCatDesserts[10])
        jpCat.add(jspBreadCard, subCatDesserts[11])
        jpCat.add(jspTartCard, subCatDesserts[12])

        jpContent.add(jpCat, "align center")
    }

    private fun createJSP(panel: JPanel) : JScrollPane {
        val jsp = JScrollPane(panel)
        jsp.verticalScrollBar.unitIncrement = 16
        jsp.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        return jsp
    }

    private fun createButton(cat: String, subCat: String, name: String, names: String) : ButtonRecipeCard {
        ReadRecipe(cat, subCat, names)
        val title = recipeTitle
        val category = recipeCategory
        val subCategory = recipeSubCategory
        val instructions = recipeInstructions
        val ingredients = recipeIngredients
        val desc = recipeDescription
        val temperature = recipeTemperature
        val convTemperature = recipeConvTemperature
        val egg = recipeEgg
        val gluten = recipeGluten
        val lactose = recipeLactose
        val vegan = recipeVegan
        val vegetarian = recipeVegetarian

        val jb = ButtonRecipeCard(name, cat, subCat, desc)

        jb.addActionListener {
            val spf = SavedRecipeFrame(title, category, subCategory, instructions, ingredients, desc, temperature, convTemperature, egg, gluten, lactose, vegan, vegetarian)
            spf.setLocationRelativeTo(this)
        }

        return jb
    }

    fun loadRecipes() {
        try {
            jpCakeCard.removeAll()
            jpChocolateCard.removeAll()
            jpConfectionCard.removeAll()
            jpCookieCard.removeAll()
            jpCustardCard.removeAll()
            jpFriedCard.removeAll()
            jpFrozenCard.removeAll()
            jpGelatinCard.removeAll()
            jpPastryCard.removeAll()
            jpPieCard.removeAll()
            jpPuddingCard.removeAll()
            jpBreadCard.removeAll()
            jpTartCard.removeAll()
        } catch (e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }

        for(type in subCatDesserts) {
            val file = File(Files().makeRecipeDir(categories[0], type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)
                println(allFiles)
                dw.add(names)

                when (type) {
                    subCatDesserts[0] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCakeCard.add(selectedButton)
                    }
                    subCatDesserts[1] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpChocolateCard.add(selectedButton)
                    }
                    subCatDesserts[2] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpConfectionCard.add(selectedButton)
                    }
                    subCatDesserts[3] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCookieCard.add(selectedButton)
                    }
                    subCatDesserts[4] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCustardCard.add(selectedButton)
                    }
                    subCatDesserts[5] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpFriedCard.add(selectedButton)
                    }
                    subCatDesserts[6] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpFrozenCard.add(selectedButton)
                    }
                    subCatDesserts[7] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpGelatinCard.add(selectedButton)
                    }
                    subCatDesserts[8] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPastryCard.add(selectedButton)
                    }
                    subCatDesserts[9] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPieCard.add(selectedButton)
                    }
                    subCatDesserts[10] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPuddingCard.add(selectedButton)
                    }
                    subCatDesserts[11] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpBreadCard.add(selectedButton)
                    }
                    subCatDesserts[12] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpTartCard.add(selectedButton)
                    }
                }
            }
        }
    }

}