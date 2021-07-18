package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.listCategories
import com.github.atheera.recipemanager.listPath
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ItemEvent
import java.awt.font.TextAttribute
import java.awt.font.TextAttribute.STRIKETHROUGH_ON
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

class NewTodoListPanel : JPanel(MigLayout("align center")) {

    var jpList = JPanel(MigLayout())
    private var jspList = JScrollPane(jpList)
    private var jpOuter = JPanel(MigLayout())

    private var jbAdd = JButton("Add to list")
    private var jbRemove = JButton("Remove all checked items")
    var htfTitle = HintTextField("Enter the list title here")
    private var htfItem = HintTextField("Enter the item to be done here")
    var jbSave = JButton("Press me to save the list to: $listPath/${listCategories[1]}")

    private var list = mutableListOf<String>()
    private var checkList = mutableListOf<JCheckBox>()
    private var listCounter: Int = 0
    private var fontA = Font("Tahoma", Font.BOLD, 20)
    private val fontB = Font("Tahoma", Font.PLAIN, 20)
    private val dim = Dimension(350, 500)

    init {
        border = TitledBorder(EtchedBorder(), "Here you can make a list of things to do")

        htfTitle.minimumSize = Dimension(250, 25)
        htfItem.minimumSize = Dimension(350, 25)

        jpList.minimumSize = dim
        jspList.minimumSize = dim
        jpOuter.minimumSize = dim
        jspList.verticalScrollBar.unitIncrement = 16
        jspList.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED

        htfItem.addActionListener {
            addToList()
        }
        jbAdd.addActionListener {
            addToList()
        }
        jbRemove.addActionListener {
            println(checkList.iterator())
            println(list)
            for(i in 0 until checkList.size) {
                jpList.remove(checkList[i])
                checkList.removeAt(i)
                list.removeAt(i)
            }
            updateUI()
        }
        jbSave.addActionListener {
            println(list)
        }

        add(htfTitle, "align center, wrap")
        add(htfItem, "align center, wrap")
        add(jbAdd, "align center, split 2")
        add(jbRemove, "align center, wrap")
        add(jpOuter, "align center, wrap")
        jpOuter.add(jspList, "align center, wrap")
        add(jbSave, "align center")

    }

    private fun addToList() {
        if(htfItem.text.isEmpty() || htfItem.text == "Enter the item to be done here") {
            JOptionPane.showMessageDialog(this, "You need to enter an item first!")
        } else {
            val itemCard = createCard(htfItem.text)
            jpList.add(itemCard, "wrap")
            htfItem.text = ""
        }
    }

    fun createCard(item: String) : JCheckBox {
        val jcbChecked = JCheckBox(item)
        jcbChecked.font = fontA

        list.add(item)

        jcbChecked.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                checkList.add(jcbChecked)
                jcbChecked.font = strikeThrough(fontA)
            } else if ((it.stateChange == ItemEvent.DESELECTED)) {
                checkList.remove(jcbChecked)
                jcbChecked.font = fontA
            }
        }

        updateUI()
        return jcbChecked
    }

    private fun clearInfo() {
        htfItem.text = ""

        jspList.removeAll()

        checkList.removeAll(checkList)
        updateUI()
    }

    private fun strikeThrough(font: Font): Font {
        val attributes = font.attributes
        attributes.set(TextAttribute.STRIKETHROUGH, STRIKETHROUGH_ON)
        return Font(attributes)
    }

}

private fun <K, V> Map<K, V>.set(key: V, value: V) {

}
