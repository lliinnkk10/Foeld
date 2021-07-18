package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.extras.ToolTipButton
import com.github.atheera.recipemanager.save.write.WriteRecipeFavorite
import com.github.atheera.recipemanager.save.write.WriteRecipeSaves
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

// Local
val ml = MigLayout()
private val fontA = Font("Tahoma", Font.BOLD, 20)
private val fontB = Font("Tahoma", Font.PLAIN, 16)
val jpInsIng = JPanel(MigLayout())

// Title
var htfTitle = HintTextField("Start with naming the recipe here")

// Categories
var jpCats = JPanel(ml)
lateinit var jcbCategories: JComboBox<String>
var cl = CardLayout()
val jpCatCards = JPanel(cl)
val jpSubCatD = JPanel()
val jpSubCatE = JPanel()
val jpSubCatM = JPanel()
var bgJRBGroup = ButtonGroup()
lateinit var selCategory: String
lateinit var selSubCat: String
lateinit var jrbSel: JRadioButton
lateinit var bmCat: ButtonModel

// Intolerance checkboxes
val jcbEgg = JCheckBox("Egg Free")
val jcbGluten = JCheckBox("Gluten Free")
val jcbLactose = JCheckBox("Lactose Free")
val jcbVegan = JCheckBox("Vegan")
val jcbVeget = JCheckBox("Vegetarian")
val jpIntol = JPanel(MigLayout())

// Degree converter
var factor: Int = 0
var result: Int = 0
val jpDegrees = JPanel(MigLayout())
val jpConv = JPanel(MigLayout())
var htfDegrees = HintTextField("0")
val jlConverted = JLabel("0")
val jlCF = JLabel("")
val jrbCFahr = JRadioButton("Convert to Fahrenheit")
val jrbCCEls = JRadioButton("Convert to Celsius")
val bgCF = ButtonGroup()

// Ingredients list
val jpIng = JPanel(MigLayout())
val jpIngOut = JPanel(MigLayout())
val jspIng = JScrollPane(jpIng)
val jbIngAdd = JButton("Add to list")
var htfIngAmount = HintTextField("Amount")
var jcbIngMeasure = JComboBox(measures.toTypedArray())
var htfIngItem = HintTextField("Item")
var alIngredients = mutableListOf<String>()
var counterIng: Int = 0
lateinit var selectedMeasure: String
val ttbIng = ToolTipButton("You can also press enter when typing the item to add!")

// Instructions list
val jpIns = JPanel(MigLayout())
val jpInsOut = JPanel(MigLayout())
val jspIns = JScrollPane(jpIns)
val jtaIns = JTextArea()

// Save buttons
val jbSave = JButton()
val jbFavorite = JButton()
val jpButtons = JPanel(MigLayout())

class NewRecipePanel : JPanel(MigLayout()), ItemListener, ActionListener {

    init {
        border = setBorder("Here you can create a new recipe!")

        // Functions
        htfTitle.minimumSize = Dimension(250, 25)
        addJCBList()
        addCheckBoxes()
        addDegreeConverter()
        addIngredientList()
        addInstructionList()
        addSaveButtons()

        // Add
        add(htfTitle, "align center, wrap")
        add(jpCats, "align center, wrap")
        add(jpIntol, "align center, split 2")
        add(jpDegrees, "align center, wrap")
        add(jpInsIng, "align center, wrap")
        add(jpButtons, "align center")
    }

    fun addSaveButtons() {
        updateButtons()

        jbSave.addActionListener {
            if(getInformation()) {
                WriteRecipeSaves(
                    recipeTitle,
                    recipeCategory,
                    recipeSubCategory,
                    recipeInstructions,
                    recipeIngredients,
                    recipeTemperature,
                    recipeConvTemperature,
                    recipeEgg,
                    recipeGluten,
                    recipeLactose,
                    recipeVegan,
                    recipeVegetarian
                )
                JOptionPane.showMessageDialog(this, "Successfully saved recipe file to $recipePath$selCategory/$selSubCat")
                clearInformation()
            }
        }

        jbFavorite.addActionListener {
            if(getInformation()) {
                WriteRecipeFavorite(
                    recipeTitle,
                    recipeCategory,
                    recipeSubCategory,
                    recipeInstructions,
                    recipeIngredients,
                    recipeTemperature,
                    recipeConvTemperature,
                    recipeEgg,
                    recipeGluten,
                    recipeLactose,
                    recipeVegan,
                    recipeVegetarian
                )
                JOptionPane.showMessageDialog(this, "Successfully saved recipe file to $recipeFavPath")
                clearInformation()
            }
        }

        jpButtons.add(jbSave, "align center")

        jpButtons.add(jbFavorite, "align center")
    }

    fun addInstructionList() {
        jpInsOut.border = setBorder("Here you can enter instructions for the recipe")

        jtaIns.wrapStyleWord = true
        jtaIns.lineWrap = true
        jtaIns.minimumSize = Dimension(442, 505)
        jtaIns.font = fontB

        jspIns.minimumSize = Dimension(475, 522)
        jspIns.maximumSize = Dimension(475, 522)
        jspIns.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspIns.verticalScrollBar.unitIncrement = 16

        jpIns.add(jtaIns)
        jpInsOut.add(jspIns)
        jpInsIng.add(jpInsOut, "wrap")
    }

    fun addIngredientList() {
        selectedMeasure = measures[0]
        jpIngOut.border = setBorder("Here you can enter the amount of an item in the recipe")
        jspIng.minimumSize = Dimension(440, 458)
        jspIng.maximumSize = Dimension(440, 458)
        jspIng.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspIng.verticalScrollBar.unitIncrement = 16
        htfIngItem.minimumSize = Dimension(250, 25)
        htfIngAmount.minimumSize = Dimension(100, 25)


        jcbIngMeasure.selectedIndex = 0
        jcbIngMeasure.isEditable = false
        jcbIngMeasure.addItemListener { if(it.stateChange == ItemEvent.SELECTED) selectedMeasure = it.item.toString() }

        jbIngAdd.addActionListener {
            if(check()) {
                try {
                    val ingCard = createCard()
                    jpIng.add(ingCard, "wrap")
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    JOptionPane.showMessageDialog(this, "You need to enter some valid information first, numbers only!")
                }
            }
        }

        htfIngItem.addActionListener {
            if(check()) {
                try {
                    val ingCard = createCard()
                    jpIng.add(ingCard, "wrap")
                    htfIngAmount.requestFocus()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    JOptionPane.showMessageDialog(this, "You need to enter some valid information first, numbers only!")
                }
            }
        }

        jpIngOut.add(jbIngAdd, "align center, split 2")
        jpIngOut.add(ttbIng, "wrap")
        jpIngOut.add(htfIngAmount, "align center, split 3")
        jpIngOut.add(jcbIngMeasure)
        jpIngOut.add(htfIngItem, "wrap")
        jpIngOut.add(jspIng, "align center")
        jpInsIng.add(jpIngOut, "split 2")
    }

    fun addDegreeConverter() {

        jpDegrees.border = setBorder("Here you can convert temperature F/C")
        bgCF.add(jrbCCEls)
        bgCF.add(jrbCFahr)
        htfDegrees.minimumSize = Dimension(50, 25)
        jlConverted.font = fontA

        jrbCFahr.addActionListener {
            try {
                factor = htfDegrees.text.toInt()
                result = (factor * 9/5) + 32
                jlConverted.text = "$result° F"
                jlCF.text = "° C"
                updateUI()
            } catch(e: NumberFormatException) {
                JOptionPane.showMessageDialog(this, "That is not a whole number! Try again!")
            }
        }

        jrbCCEls.addActionListener {
            try {
                factor = htfDegrees.text.toInt()
                result = (factor - 32)*5/9
                jlConverted.text = "$result° C"
                jlCF.text = "° F"
                updateUI()
            } catch(e: NumberFormatException) {
                JOptionPane.showMessageDialog(this, "That is not a whole number! Try again!")
            }

        }
        //jpDegrees.maximumSize = Dimension(700, 90)

        //jpDegrees.add(jlDesc)
        jpDegrees.add(htfDegrees)
        jpDegrees.add(jlCF)
        jpConv.add(jrbCCEls, "wrap")
        jpConv.add(jrbCFahr)
        jpDegrees.add(jpConv)
        jpDegrees.add(jlConverted)

    }

    fun addCheckBoxes() {
        jpIntol.border = setBorder("Check any of these to show intolerances or restrictions")
        jpIntol.add(jcbEgg)
        jpIntol.add(jcbGluten)
        jpIntol.add(jcbLactose)
        jpIntol.add(jcbVegan)
        jpIntol.add(jcbVeget)
    }

    private fun addJCBList() {

        jcbCategories = JComboBox(categories.toTypedArray())
        jcbCategories.isEditable = false
        jcbCategories.addItemListener(this)
        jcbCategories.selectedIndex = 0
        selCategory = categories[0]

        for(i in subCatDesserts.indices) {
            val jrb = JRadioButton(subCatDesserts[i])
            jpSubCatD.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
            if(i == 0)
                jrb.doClick()
        }
        for(i in subCatExtras.indices) {
            val jrb = JRadioButton(subCatExtras[i])
            jpSubCatE.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for(i in subCatMeats.indices) {
            val jrb = JRadioButton(subCatMeats[i])
            jpSubCatM.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        bmCat = bgJRBGroup.selection
        bgJRBGroup.setSelected(bmCat, true)
        jpCatCards.add(jpSubCatD, categories[0])
        jpCatCards.add(jpSubCatE, categories[1])
        jpCatCards.add(jpSubCatM, categories[2])
        jpCats.add(jcbCategories, "align center, wrap")
        jpCats.add(jpCatCards, "wrap")
        jpCats.border = setBorder("Select the category in the drop box, and what kind of food with the buttons")
    }

    fun createCard(amount: Double = htfIngAmount.text.toDouble(), measure: String = selectedMeasure, item: String = htfIngItem.text, removePane: JPanel = jpIng) : JPanel {

        val jp = JPanel(MigLayout("","[]10[]","[]20[]20[]20[]"))
        val jbDelete = JButton("Remove:")
        val jlAmount = JLabel("$amount")
        val jlMeasure = JLabel(measure)
        val jlItem = JLabel(item)

        jlAmount.font = fontA
        jlMeasure.font = fontA
        jlItem.font = fontA

        val combinedIngredient = ("$amount $measure $item")

        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.minimumSize = Dimension(408, 45)
        jp.maximumSize = Dimension(408, 45)

        jbDelete.addActionListener {
            removePane.remove(jp)
            updateUI()
            alIngredients.remove(combinedIngredient)
            counterIng--
        }

        htfIngAmount.text = ""
        htfIngItem.text = ""

        alIngredients.add(counterIng, combinedIngredient)
        counterIng++

        jp.add(jbDelete)
        jp.add(jlAmount)
        jp.add(jlMeasure)
        jp.add(jlItem)
        updateUI()
        return jp
    }

    fun getInformation() : Boolean {
        val c1: Boolean
        val c2: Boolean
        val c3: Boolean
        var c4 = false
        if((htfTitle.text.isEmpty() || htfTitle.text == "Start with naming the recipe here")) {
            JOptionPane.showMessageDialog(this, "The recipe needs a title first!")
            return false
        } else { c1 = true }
        if(alIngredients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The ingredient list is empty!")
            return false
        } else { c2 = true }
        if(jtaIns.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You should add some instructions!")
            return false
        } else { c3 = true }
        if(c1 && c2 && c3) {
            recipeTitle = htfTitle.text
            recipeCategory = selCategory
            recipeSubCategory = selSubCat
            recipeIngredients = alIngredients
            recipeInstructions = jtaIns.text
            recipeTemperature = factor
            recipeConvTemperature = result
            recipeEgg = jcbEgg.isSelected
            recipeGluten = jcbGluten.isSelected
            recipeLactose = jcbLactose.isSelected
            recipeVegan = jcbVegan.isSelected
            recipeVegetarian = jcbVeget.isSelected
            c4 = true
        }
        return c4
    }

    fun clearInformation() {
        htfTitle.text = ""
        selCategory = categories[0]
        jcbCategories.selectedIndex = 0
        selSubCat = subCatDesserts[0]
        alIngredients.clear()
        jpIng.removeAll()
        jtaIns.text = ""
        factor = 0
        htfDegrees.text = "0"
        result = 0
        jlConverted.text = ""
        jcbEgg.isSelected = false
        jcbGluten.isSelected = false
        jcbLactose.isSelected = false
        jcbVegan.isSelected = false
        jcbVeget.isSelected = false
        updateUI()
    }

    private fun check() : Boolean {
        val check = (htfIngAmount.text.isEmpty() || htfIngAmount.text == "Amount")
        val check2 = (htfIngItem.text.isEmpty() || htfIngItem.text == "Item")
        val info = "You need to enter some valid information first, numbers only!"
        val info2 = "You need to enter an item to add first!"

        if(check) {
            JOptionPane.showMessageDialog(this, info)
            return false
        }
        if(check2) {
            JOptionPane.showMessageDialog(this, info2)
            return false
        }

        return true
    }

    private fun updateButtons() {
        updateUI()
        jbSave.text = "Click me to save the recipe to: $recipePath$selCategory/$selSubCat"
        jbFavorite.text = recipePath+"Favorites"
        updateUI()
    }

    private fun setBorder(title: String) : Border {
        return TitledBorder(EtchedBorder(), title)
    }

    override fun itemStateChanged(e: ItemEvent) {
        cl = jpCatCards.layout as CardLayout
        cl.show(jpCatCards, e.item as String)
        if(e.stateChange == ItemEvent.SELECTED)
            selCategory = e.item.toString()
        updateButtons()
    }

    override fun actionPerformed(e: ActionEvent) {
        jrbSel = e.source as JRadioButton
        selSubCat = jrbSel.text
        updateButtons()
    }
}