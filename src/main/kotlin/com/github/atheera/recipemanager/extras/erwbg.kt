package com.github.atheera.recipemanager.extras

import javax.swing.JPanel
import java.util.LinkedHashMap

class erwbg {
    private val panelMap: MutableMap<String, JPanel> = LinkedHashMap()
    var panel = JPanel()

    init {
        panelMap[""] = panel
    }
}