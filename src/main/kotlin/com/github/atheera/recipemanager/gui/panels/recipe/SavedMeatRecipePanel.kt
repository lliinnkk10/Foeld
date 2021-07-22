package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadRecipe
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ItemEvent
import java.io.File
import java.io.IOException
import javax.swing.*

class SavedMeatRecipePanel : JPanel() {

    var jcbCat: JComboBox<String>
    var clCat: CardLayout
    var jpCat: JPanel
    var jpContent: JPanel
    var jspContent: JScrollPane

    var jpBeefCard = JPanel(MigLayout())
    var jpFishCard = JPanel(MigLayout())
    var jpOtherCard = JPanel(MigLayout())
    var jpPlantCard = JPanel(MigLayout())
    var jpPoultryCard = JPanel(MigLayout())
    var jpPorkCard = JPanel(MigLayout())
    var jspBeefCard = createJSP(jpBeefCard)
    var jspFishCard = createJSP(jpFishCard)
    var jspOtherCard = createJSP(jpOtherCard)
    var jspPlantCard = createJSP(jpPlantCard)
    var jspPoultryCard = createJSP(jpPoultryCard)
    var jspPorkCard = createJSP(jpPorkCard)

    lateinit var selectedCat: String
    lateinit var selectedButton: JButton

    init {
        border = setBorder("Select which meat category to load buttons for")

        jcbCat = JComboBox(subCatMeats.toTypedArray())
        clCat = CardLayout()
        jpCat = JPanel(clCat)
        jpContent = JPanel(MigLayout())
        jspContent = JScrollPane(jpCat)
        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(535, 550)
        jspContent.maximumSize = Dimension(535, 550)

        jcbCat.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                selectedCat = it.item as String
                clCat.show(jpCat, selectedCat)
                updateUI()
            }
        }

        clCat.show(jpCat, subCatMeats[0])

        add(jpContent)
        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jspBeefCard, subCatMeats[0])
        jpCat.add(jspFishCard, subCatMeats[1])
        jpCat.add(jspOtherCard, subCatMeats[2])
        jpCat.add(jspPlantCard, subCatMeats[3])
        jpCat.add(jspPoultryCard, subCatMeats[4])
        jpCat.add(jspPorkCard, subCatMeats[5])

        jpContent.add(jspContent, "align center")
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
            jpBeefCard.removeAll()
            jpFishCard.removeAll()
            jpOtherCard.removeAll()
            jpPlantCard.removeAll()
            jpPorkCard.removeAll()
            jpPoultryCard.removeAll()
        } catch (e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }

        for(type in subCatMeats) {
            val file = File(Files().makeRecipeDir(categories[2], type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)
                println(allFiles)
                dw.add(names)

                when (type) {
                    subCatMeats[0] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpBeefCard.add(selectedButton, "wrap")
                    }
                    subCatMeats[1] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpFishCard.add(selectedButton, "wrap")
                    }
                    subCatMeats[2] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpOtherCard.add(selectedButton, "wrap")
                    }
                    subCatMeats[3] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpPlantCard.add(selectedButton, "wrap")
                    }
                    subCatMeats[4] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpPoultryCard.add(selectedButton, "wrap")
                    }
                    subCatMeats[5] -> {
                        selectedButton = createButton(categories[2], type, name, names)
                        jpPorkCard.add(selectedButton, "wrap")
                    }
                }
            }
        }
    }

}