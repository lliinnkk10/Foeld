package com.github.atheera.recipemanager.extras

import com.google.gson.JsonArray
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.ArrayList
import javax.swing.JPanel
import javax.swing.JTextField

class test : JPanel(), KeyListener {
    var jtf = JTextField()
    var list = ArrayList<String>()
    var arr = JsonArray()
    override fun keyTyped(e: KeyEvent) {}
    override fun keyPressed(e: KeyEvent) {}
    override fun keyReleased(e: KeyEvent) {}

    init {
        list.add("asf")
        list.add("asdf0gas")
        for (i in 0 until arr.size()) {
            println(arr[i])
        }
        for (i in list.indices) {
            println(list[i])
        }
        jtf.addActionListener { e ->
            if (e.modifiers > 0) {
            }
        }
    }
}