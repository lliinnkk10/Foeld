package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.onStartUp
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.WriteSettingsFile
import net.miginfocom.swing.MigLayout
import java.util.Timer
import javax.swing.*
import kotlin.concurrent.schedule

class ChangeSaveDirectory : JFrame() {

    private var jtaCD = HintTextField("Type new save location here")
    private var jbSetDefault = JButton("Set to default: c://FOE/")
    private var jbSetNew = JButton("Set to text:")
    private var jlDesc = JLabel("NOTE: changing directory after saving files will not transfer files!")
    private var jlDesc2 = JLabel("NOTE: it will automatically add /FOE/ to the end!")
    private var jpMain = JPanel(MigLayout())

    init {
        title = "Change the location of saved files"
        defaultCloseOperation = DISPOSE_ON_CLOSE
        isResizable = false

        // Adding functionality
        jbSetDefault.addActionListener {
            Files().setPath("C://FOE/")
            WriteSettingsFile("C://FOE/")
            JOptionPane.showMessageDialog(this, "Successfully set save location to: c://FOE/")
            onStartUp()
            Timer("SettingUp", false).schedule(300) {
                dispose()
            }
        }

        jbSetNew.addActionListener {
            if(jtaCD.text.isEmpty() || jtaCD.text == "Type new save location here") {
                JOptionPane.showMessageDialog(this, "You need to set a location first! ex: c://Users/(user)/Desktop")
            } else {
                Files().setPath(jtaCD.text + "/FOE/")
                WriteSettingsFile(jtaCD.text + "/FOE/")
                JOptionPane.showMessageDialog(this, "Successfully set save location to: ${jtaCD.text+"/FOE/"}")
                Timer("SettingUp", false).schedule(300) {
                    dispose()
                }
                onStartUp()
            }
        }

        // Add to window
        jpMain.add(jtaCD, "align center, wrap")
        jpMain.add(jbSetNew, "align center, split 2")
        jpMain.add(jbSetDefault, "align center, wrap")
        jpMain.add(jlDesc, "align center, wrap")
        jpMain.add(jlDesc2, "align center")
        add(jpMain)

        pack()
        isVisible = true
    }

}