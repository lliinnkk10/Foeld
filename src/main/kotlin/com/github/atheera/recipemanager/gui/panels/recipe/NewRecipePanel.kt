package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.gui.States
import com.github.atheera.recipemanager.gui.currentState
import net.miginfocom.swing.MigLayout
import javax.swing.JPanel
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

class NewRecipePanel : JPanel(MigLayout()) {

    var htfTitle = HintTextField("Start with naming the recipe here")

    init {
        border = TitledBorder(EtchedBorder(), "Here you can create a new recipe!")

        add(htfTitle)
    }

    fun removeContent() {
        if(currentState != States.NEWRECIPESTATE) {
            htfTitle.text = ""
        }
    }

}