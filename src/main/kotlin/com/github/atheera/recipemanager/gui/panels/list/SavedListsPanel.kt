package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonListCard
import com.github.atheera.recipemanager.gui.frames.list.SavedPCList
import com.github.atheera.recipemanager.gui.frames.list.SavedTDList
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadListPC
import com.github.atheera.recipemanager.save.read.ReadListTD
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ItemEvent
import java.io.File
import java.io.IOException
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

private lateinit var jcbTypes: JComboBox<String>
private lateinit var clTypes: CardLayout
private lateinit var panTypes: JPanel
private lateinit var contentPane: JPanel

private lateinit var panCardPC: JPanel
private lateinit var panCardTD: JPanel
private lateinit var jspCardPC: JScrollPane
private lateinit var jspCardTD: JScrollPane
private lateinit var selectedType: String
private lateinit var tempButton: JButton

class SavedListsPanel : JPanel() {

    init {
        border = TitledBorder(EtchedBorder(), "Select which list type to load buttons for")

        // Initialize
        jcbTypes = JComboBox(listCategories.toTypedArray())
        clTypes = CardLayout()
        panTypes = JPanel(clTypes)
        contentPane = JPanel(MigLayout())

        panCardPC = JPanel(MigLayout("align center", "[]", "[]10[]"))
        panCardTD = JPanel(MigLayout("align center", "[]", "[]10[]"))
        jspCardPC = JScrollPane(panCardPC)
        jspCardTD = JScrollPane(panCardTD)

        jspCardPC.verticalScrollBar.unitIncrement = 16
        jspCardTD.verticalScrollBar.unitIncrement = 16
        jspCardPC.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        jspCardTD.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        jspCardTD.minimumSize = Dimension(350, 500)

        clTypes.show(panTypes, listCategories[0])

        // Functions
        jcbTypes.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {

                selectedType = it.item as String

                clTypes.show(panTypes, selectedType)
                updateUI()
            }
        }

        // Add to screen
        add(contentPane)
        contentPane.add(jcbTypes, "align center, wrap")

        panTypes.add(jspCardPC, listCategories[0])
        panTypes.add(jspCardTD, listCategories[1])
        contentPane.add(panTypes, "align center")
    }

    private fun createButton(title: String, type: String, file: String) : ButtonListCard {
        val jb = ButtonListCard(title, type)

        when(type) {
            listCategories[0] -> {
                jb.addActionListener {
                    ReadListPC(file)
                    val titled = listPCTitle
                    val posList = listPCPos
                    val negList = listPCNeg
                    val spcl = SavedPCList(titled, posList, negList)
                    spcl.setLocationRelativeTo(this)
                }
            }
            listCategories[1] -> {
                jb.addActionListener {
                    ReadListTD(file)
                    val titled = listTDTitle
                    val todolist = listTD
                    val checked = listTDChecked
                    val stdl = SavedTDList(titled, todolist, checked)
                    stdl.setLocationRelativeTo(this)
                }
            }
        }

        return jb
    }

    fun loadLists() {
        try {
            panCardPC.removeAll()
            panCardTD.removeAll()
        } catch (e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }
        for(type in listCategories) { // Loop through all list types
            val file = File(Files().makeListDir(type))
            val files = file.listFiles()
            for(allFiles in files!!) { // Loop through all files in that type
                // Gets the name for each file and removes .json
                val names = allFiles.name
                val name = names.substring(0, names.length - 5)
                println(names)

                when (type) { // Adds buttons and function to each list type
                    listCategories[0] -> { // Pros/Cons
                        tempButton = createButton(name, listCategories[0], names)
                        panCardPC.add(tempButton, "align center, wrap")
                    }
                    listCategories[1] -> { // To Do List
                        tempButton = createButton(name, listCategories[1], names)
                        panCardTD.add(tempButton, "align center, wrap")
                    }
                }

            }
        }
    }

}