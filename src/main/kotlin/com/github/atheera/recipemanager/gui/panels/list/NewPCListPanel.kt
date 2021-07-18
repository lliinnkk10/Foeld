package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.listCategories
import com.github.atheera.recipemanager.listPath
import com.github.atheera.recipemanager.save.write.WriteListPC
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

class NewPCListPanel : JPanel(MigLayout("align center")), KeyListener {

    var posPane = JPanel(MigLayout())
    private var posOutPane = JPanel(MigLayout())
    private var posScroll = JScrollPane(posPane)
    private var posButton = JButton("Add to pros side")

    var negPane = JPanel(MigLayout())
    private var negOutPane = JPanel(MigLayout())
    private var negScroll = JScrollPane(negPane)
    private var negButton = JButton("Add to cons side")

    private var htaPane = JPanel(MigLayout("align center", "[]10[]10[]", ""))
    var htaTitle = HintTextField("Enter the list title here")
    private var htaArg = HintTextField("Enter the argument here")
    var saveBtn = JButton("Press me to save the list to: $listPath/${listCategories[0]}")
    private var jlDesc = JLabel("NOTE: pressing enter or shift+enter adds to pros or cons respectively")
    private var jlPos = JLabel("Pros:")
    private var jlNeg = JLabel("Cons:")

    private var posList = mutableListOf<String>()
    private var negList = mutableListOf<String>()
    private var posCounter: Int = 0
    private var negCounter: Int = 0
    private var holdingShift: Boolean = false
    private val fontA = Font("Tahoma", Font.PLAIN, 16)
    private val fontB = Font("Tahoma", Font.BOLD, 20)
    private val dim = Dimension(350, 500)

    init {
        border = TitledBorder(EtchedBorder(), "Here you can make a positive and negative arguments for a subject")

        // Functions
        htaTitle.minimumSize = Dimension(250, 25)
        htaArg.minimumSize = Dimension(350, 25)
        saveBtn.minimumSize = Dimension(350, 40)
            // Positive
        posPane.minimumSize = dim
        posOutPane.minimumSize = dim
        posScroll.minimumSize = dim
        posScroll.verticalScrollBar.unitIncrement = 16
        posScroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            // Negative
        negPane.minimumSize = dim
        negOutPane.minimumSize = dim
        negScroll.minimumSize = dim
        negScroll.verticalScrollBar.unitIncrement = 16
        negScroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            // Misc
        jlDesc.font = fontA
        jlNeg.font = fontB
        jlPos.font = fontB

        htaArg.addKeyListener(this)

        posButton.addActionListener {
            addToList(true)
        }
        negButton.addActionListener {
            addToList(false)
        }
        saveBtn.addActionListener {
            if((htaTitle.text.isEmpty() || htaTitle.text == "Enter the list title here")|| posList.isEmpty() || negList.isEmpty())
                JOptionPane.showMessageDialog(this, "You need to enter information to save first!")
            else {
                WriteListPC(listCategories[0], htaTitle.text, posList, negList)
                JOptionPane.showMessageDialog(this, "Successfully saved list to: $listPath/${listCategories[0]}")
                clearInfo()
            }
        }

        // Add to panel
        add(htaTitle, "align center, wrap")
        htaPane.add(posButton, "align center")
        htaPane.add(htaArg, "align center")
        htaPane.add(negButton, "align center, wrap")
        htaPane.add(jlDesc, "align center, span 3")
        add(htaPane, "align center, wrap")
        add(posOutPane, "split 2")
        add(negOutPane, "wrap")
        add(saveBtn, "align center")
            // Positive
        posOutPane.add(jlPos, "align center, wrap")
        posOutPane.add(posScroll)

            // Negative
        negOutPane.add(jlNeg, "align center, wrap")
        negOutPane.add(negScroll)


    }

    private fun clearInfo() {
        htaArg.text = ""
        htaTitle.text = ""

        posPane.removeAll()
        negPane.removeAll()

        posList.clear()
        negList.clear()
        updateUI()
    }

    private fun addToList(isPos: Boolean) {

        if(htaArg.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to enter an argument!")
        }
        if(isPos) {
            val argCard = createCard(htaArg.text, posPane)
            posPane.add(argCard, "wrap")
        } else {
            val argCard = createCard(htaArg.text, negPane)
            negPane.add(argCard, "wrap")
        }
        htaArg.text = ""

    }



    fun createCard(argument: String, removePane: JPanel) : JPanel {

        val jp = JPanel(MigLayout("", "[]10[]", ""))
        val jlArg = JLabel(argument)
        val jbDelete = JButton("Delete")

        when (removePane) {
            posPane -> {
                posList.add(posCounter, argument)
                posCounter++
            }
            negPane -> {
                negList.add(negCounter, argument)
                negCounter++
            }
        }

        jlArg.font = fontA
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.minimumSize = Dimension(340, 40)
        jp.maximumSize = Dimension( 340, 40)

        jbDelete.addActionListener {
            htaArg.text = ""
            removePane.remove(jp)
            updateUI()
            when (removePane) {
                posPane -> {
                    posList.remove(argument)
                    posCounter--
                }
                negPane -> {
                    negList.remove(argument)
                    negCounter--
                }
            }
        }

        jp.add(jbDelete)
        jp.add(jlArg)
        updateUI()
        return jp
    }


    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        if(e!!.keyCode == KeyEvent.VK_SHIFT) {
            holdingShift = true
        }
        if(holdingShift && e.keyCode == KeyEvent.VK_ENTER) {
            addToList(false)
        } else if(e.keyCode == KeyEvent.VK_ENTER) {
            addToList(true)
        }

    }

    override fun keyReleased(e: KeyEvent?) {
        if(e!!.keyCode == KeyEvent.VK_SHIFT) {
            holdingShift = false
        }
    }

}