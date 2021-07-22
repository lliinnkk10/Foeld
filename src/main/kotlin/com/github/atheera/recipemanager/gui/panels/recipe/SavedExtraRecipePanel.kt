package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadRecipe
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.event.ItemEvent
import java.io.File
import java.io.IOException
import javax.swing.*

class SavedExtraRecipePanel: JPanel() {

    var jcbCat: JComboBox<String>
    var clCat: CardLayout
    var jpCat: JPanel
    var jpContent: JPanel

    var jpBreadCard = JPanel()
    var jpGreenCard = JPanel()
    var jpSauceCard = JPanel()
    var jpPieCard = JPanel()
    var jpSoupCard = JPanel()
    var jspBreadCard = createJSP(jpBreadCard)
    var jspGreenCard = createJSP(jpGreenCard)
    var jspSauceCard = createJSP(jpSauceCard)
    var jspPieCard = createJSP(jpPieCard)
    var jspSoupCard = createJSP(jpSoupCard)

    lateinit var selectedCat: String
    lateinit var selectedButton: JButton

    init {
        border = setBorder("Select which extra category to load buttons for")

        jcbCat = JComboBox(subCatExtras.toTypedArray())
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

        clCat.show(jpCat, subCatExtras[0])

        add(jpContent)
        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jspBreadCard, subCatExtras[0])
        jpCat.add(jspGreenCard, subCatExtras[1])
        jpCat.add(jspSauceCard, subCatExtras[2])
        jpCat.add(jspPieCard, subCatExtras[3])
        jpCat.add(jspSoupCard, subCatExtras[4])

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
            jpBreadCard.removeAll()
            jpGreenCard.removeAll()
            jpPieCard.removeAll()
            jpSauceCard.removeAll()
            jpSoupCard.removeAll()
        } catch (e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }

        for(type in subCatExtras) {
            val file = File(Files().makeRecipeDir(categories[1], type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)
                println(allFiles)
                dw.add(names)

                when (type) {
                    subCatExtras[0] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpBreadCard.add(selectedButton, "wrap")
                    }
                    subCatExtras[1] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpGreenCard.add(selectedButton, "wrap")
                    }
                    subCatExtras[2] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpSauceCard.add(selectedButton, "wrap")
                    }
                    subCatExtras[3] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpPieCard.add(selectedButton, "wrap")
                    }
                    subCatExtras[4] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpSoupCard.add(selectedButton, "wrap")
                    }
                }
            }
        }

    }

}