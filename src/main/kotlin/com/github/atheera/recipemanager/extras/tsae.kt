package com.github.atheera.recipemanager.extras

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel
import javax.swing.JTextArea

class tsae : JPanel(), KeyListener {
    override fun keyTyped(e: KeyEvent) {}
    override fun keyPressed(e: KeyEvent) {}
    override fun keyReleased(e: KeyEvent) {}

    init {
        val jta = JTextArea()
        jta.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ENTER) {
                    if (e.modifiersEx > 0) {
                    } else {
                    }
                }
            }
        })
    }
}