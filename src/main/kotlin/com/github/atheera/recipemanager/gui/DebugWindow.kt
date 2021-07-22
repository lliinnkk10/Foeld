package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.errorPath
import com.github.atheera.recipemanager.extras.TextLineNumber
import com.github.atheera.recipemanager.getCurrentTime
import com.github.atheera.recipemanager.imageIcon
import net.miginfocom.swing.MigLayout
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.io.*
import java.lang.Exception
import javax.swing.*

class DebugWindow : JFrame() {

    private val jpInfo = JPanel(MigLayout())
    private val jtaInfo = JTextArea()
    private val jspInfo = JScrollPane(jtaInfo)
    private val tlnInfo = TextLineNumber(jtaInfo)
    private val fontB = Font("Tahoma", Font.PLAIN, 14)

    private val jmb = JMenuBar()
    private val jm = JMenu("Save")
    private val jmi = JMenuItem("Save error log")

    init {
        title = "Debug Window - DO NOT CLOSE!"
        minimumSize = Dimension(500, 500)
        maximumSize = Dimension(500, 500)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        isVisible = true
        iconImage = imageIcon
        //isResizable = false

        jpInfo.minimumSize = Dimension(500, 500)
        jpInfo.maximumSize = Dimension(500, 500)

        jtaInfo.minimumSize = Dimension(467, 500)
        jtaInfo.maximumSize = Dimension(467, Integer.MAX_VALUE)

        jtaInfo.wrapStyleWord = true
        jtaInfo.lineWrap = true
        jtaInfo.isEditable = false
        jtaInfo.font = fontB

        jspInfo.minimumSize = Dimension(500, 500)
        jspInfo.setRowHeaderView(tlnInfo)
        jspInfo.verticalScrollBar.unitIncrement = 32
        jspInfo.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspInfo.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER

        jmi.addActionListener { saveLog() }
        jm.add(jmi)
        jmb.add(jm)
        add(jmb, BorderLayout.NORTH)

        add(jspInfo)
    }

    fun add(info: String) {
        jtaInfo.foreground = Color.BLACK
        jtaInfo.append("$info\n")
        jtaInfo.updateUI()
    }

    fun exc(e: Exception) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        val str = sw.toString()
        jtaInfo.foreground = Color.RED
        jtaInfo.append("$str\n")
        jtaInfo.updateUI()
    }

    private fun saveLog() {
        try {
            val file = File("${errorPath}error_log-${getCurrentTime()}.txt")
            val writer = FileWriter(file)
            writer.write(jtaInfo.text)
            writer.close()
            JOptionPane.showMessageDialog(this, "Successfully wrote error log at: $errorPath")
            Runtime.getRuntime().exec("explorer.exe /select, $file")
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
    }

}