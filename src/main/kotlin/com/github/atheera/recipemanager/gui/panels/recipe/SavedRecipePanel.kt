package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.categories
import com.github.atheera.recipemanager.subCatDesserts
import com.github.atheera.recipemanager.subCatExtras
import com.github.atheera.recipemanager.subCatMeats
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

// Categories
var jpCat = JPanel(ml)
lateinit var jcbCategorie: JComboBox<String>
var cla = CardLayout()
val jpCatCard = JPanel(cla)
val jpSubCatDe = JPanel()
val jpSubCatEx = JPanel()
val jpSubCatMe = JPanel()
var bgJRBGroups = ButtonGroup()
lateinit var selCategorys: String
lateinit var selSubCats: String
lateinit var jrbSels: JRadioButton
lateinit var bmCats: ButtonModel

class SavedRecipePanel : JPanel(MigLayout()), ItemListener, ActionListener {

    init {

        addJCBList()

        add(jpCat, "align center, wrap")
    }
    fun addJCBList() {

        jcbCategorie = JComboBox(categories.toTypedArray())
        jcbCategorie.isEditable = false
        jcbCategorie.addItemListener(this)
        jcbCategorie.selectedIndex = 0
        selCategorys = categories[0]

        for(i in subCatDesserts.indices) {
            val jrb = JRadioButton(subCatDesserts[i])
            jpSubCatDe.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
            if(i == 0)
                jrb.doClick()
        }
        for(i in subCatExtras.indices) {
            val jrb = JRadioButton(subCatExtras[i])
            jpSubCatEx.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for(i in subCatMeats.indices) {
            val jrb = JRadioButton(subCatMeats[i])
            jpSubCatMe.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        bmCats = bgJRBGroups.selection
        bgJRBGroups.setSelected(bmCats, true)
        jpCatCard.add(jpSubCatDe, categories[0])
        jpCatCard.add(jpSubCatEx, categories[1])
        jpCatCard.add(jpSubCatMe, categories[2])
        jpCat.add(jcbCategorie, "align center, wrap")
        jpCat.add(jpCatCard, "wrap")
        jpCat.border = setBorder("Select the category in the drop box, and what kind of food with the buttons")
    }

    private fun setBorder(title: String) : Border {
        return TitledBorder(EtchedBorder(), title)
    }

    override fun itemStateChanged(e: ItemEvent) {
        cla = jpCatCard.layout as CardLayout
        cla.show(jpCatCard, e.item as String)
        if(e.stateChange == ItemEvent.SELECTED)
            selCategorys = e.item.toString()
        //updateButtons()
    }

    override fun actionPerformed(e: ActionEvent) {
        jrbSels = e.source as JRadioButton
        selSubCats = jrbSels.text
        //updateButtons()
    }
}