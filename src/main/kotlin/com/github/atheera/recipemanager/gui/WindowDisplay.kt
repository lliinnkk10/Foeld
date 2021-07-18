package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.panels.MenuPanel
import com.github.atheera.recipemanager.gui.panels.list.NewPCListPanel
import com.github.atheera.recipemanager.gui.panels.list.NewTodoListPanel
import com.github.atheera.recipemanager.gui.panels.list.SavedListsPanel
import com.github.atheera.recipemanager.gui.panels.recipe.FavoriteRecipePanel
import com.github.atheera.recipemanager.gui.panels.recipe.NewRecipePanel
import com.github.atheera.recipemanager.gui.panels.recipe.SavedRecipePanel
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Dimension
import javax.swing.*

// Panels
private lateinit var mainPane: JPanel
private lateinit var MenuPane: MenuPanel
    // Recipes
private lateinit var NewRecPane: NewRecipePanel
private lateinit var SavRecPane: SavedRecipePanel
private lateinit var FavRecPane: FavoriteRecipePanel
    // Lists
private lateinit var NewPosNegListPane: NewPCListPanel
private lateinit var NewToDoListPane: NewTodoListPanel
private lateinit var SavListPane: SavedListsPanel
    // Layout
private lateinit var cl: CardLayout

// Menu
private lateinit var jmSettings: JMenu
private lateinit var jmRecipes: JMenu
private lateinit var jmLists: JMenu
private lateinit var jmb: JMenuBar
    // Items
        // Lists
private lateinit var jmSubList: JMenu
private lateinit var jmiPosNegList: JMenuItem
private lateinit var jmiToDoList: JMenuItem
private lateinit var jmiSavList: JMenuItem
        // Settings
private lateinit var jmiSettings: JMenuItem
        // Recipes
/*private lateinit var jmiDesRec: JMenu
private lateinit var jmiExtraRec: JMenu
private lateinit var jmiMeatRec: JMenu
private lateinit var jmiSubCatRec: JMenuItem*/
private lateinit var jmiSavRec: JMenuItem
private lateinit var jmiNewRec: JMenuItem
private lateinit var jmiFavRec: JMenuItem

// States of panels and names
object States {
    const val MENUSTATE: Int = 0
    const val NEWRECIPESTATE: Int = 1
    const val SAVEDRECIPESTATE: Int = 2
    const val FAVORITERECIPESTATE: Int = 3
    const val NEWPCLISTSTATE: Int = 4
    const val NEWTODOLISTSTATE: Int = 5
    const val SAVEDLISTSTATE: Int = 6
}
var currentState: Int = States.MENUSTATE
private val panels = listOf(
    "Menu",
    "New Recipe",
    "Saved Recipe",
    "Favorite Recipe",
    "New PC List",
    "New TODO List",
    "Saved List"
)

// Misc
private const val TITLE = "FOE: Files Organized Easily"

class WindowDisplay : JFrame() {

    init {
        iconImage = imageIcon
        // Initialize
            // Panels
        cl = CardLayout()
        mainPane = JPanel(cl)
        MenuPane = MenuPanel()

        NewRecPane = NewRecipePanel()
        SavRecPane = SavedRecipePanel()
        FavRecPane = FavoriteRecipePanel()

        NewPosNegListPane = NewPCListPanel()
        NewToDoListPane = NewTodoListPanel()
        SavListPane = SavedListsPanel()

        // Set data
        buildMenu()

        // Add to screen
            // Panels to main panel
        mainPane.add(MenuPane, panels[0])
        mainPane.add(NewRecPane, panels[1])
        mainPane.add(SavRecPane, panels[2])
        mainPane.add(FavRecPane, panels[3])
        mainPane.add(NewPosNegListPane, panels[4])
        mainPane.add(NewToDoListPane, panels[5])
        mainPane.add(SavListPane, panels[6])
        add(mainPane, BorderLayout.CENTER)
            // Menu buttons to menu panel
        jmb.add(jmSettings)
        jmb.add(jmRecipes)
        jmb.add(jmLists)

        // Add menu buttons to main panel
        add(jmb, BorderLayout.NORTH)

        switchPanels(States.MENUSTATE)

        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setCurrentState(panel: Int) {
        currentState = when(panel) {
            States.MENUSTATE -> States.MENUSTATE
            States.NEWRECIPESTATE -> States.NEWRECIPESTATE
            States.SAVEDRECIPESTATE -> States.SAVEDRECIPESTATE
            States.FAVORITERECIPESTATE -> States.FAVORITERECIPESTATE
            States.NEWPCLISTSTATE -> States.NEWPCLISTSTATE
            States.NEWTODOLISTSTATE -> States.NEWTODOLISTSTATE
            States.SAVEDLISTSTATE -> States.SAVEDLISTSTATE
            else -> States.MENUSTATE
        }
    }

    private fun switchPanels(panel: Int) {
        when (panel) {
            States.MENUSTATE -> {
                showPanel(MenuPane, States.MENUSTATE)
                size = changeSize(States.MENUSTATE)
                title = changeTitle(States.MENUSTATE)
                setCurrentState(States.MENUSTATE)
                setLocationRelativeTo(null)
            }
            States.NEWRECIPESTATE -> {
                showPanel(NewRecPane, States.NEWRECIPESTATE)
                size = changeSize(States.NEWRECIPESTATE)
                title = changeTitle(States.NEWRECIPESTATE)
                setCurrentState(States.NEWRECIPESTATE)
                setLocationRelativeTo(null)
            }
            States.SAVEDRECIPESTATE -> {
                showPanel(SavRecPane, States.SAVEDRECIPESTATE)
                size = changeSize(States.SAVEDRECIPESTATE)
                title = changeTitle(States.SAVEDRECIPESTATE)
                setCurrentState(States.SAVEDRECIPESTATE)
                setLocationRelativeTo(null)
            }
            States.FAVORITERECIPESTATE -> {
                showPanel(FavRecPane, States.FAVORITERECIPESTATE)
                size = changeSize(States.FAVORITERECIPESTATE)
                title = changeTitle(States.FAVORITERECIPESTATE)
                setCurrentState(States.FAVORITERECIPESTATE)
                setLocationRelativeTo(null)
            }
            States.NEWPCLISTSTATE -> {
                showPanel(NewPosNegListPane, States.NEWPCLISTSTATE)
                size = changeSize(States.NEWPCLISTSTATE)
                title = changeTitle(States.NEWPCLISTSTATE)
                setCurrentState(States.NEWPCLISTSTATE)
                setLocationRelativeTo(null)
            }
            States.NEWTODOLISTSTATE -> {
                showPanel(NewToDoListPane, States.NEWTODOLISTSTATE)
                size = changeSize(States.NEWTODOLISTSTATE)
                title = changeTitle(States.NEWTODOLISTSTATE)
                setCurrentState(States.NEWTODOLISTSTATE)
                setLocationRelativeTo(null)
            }
            States.SAVEDLISTSTATE -> {
                showPanel(SavListPane, States.SAVEDLISTSTATE)
                size = changeSize(States.SAVEDLISTSTATE)
                title = changeTitle(States.SAVEDLISTSTATE)
                setCurrentState(States.SAVEDLISTSTATE)
                setLocationRelativeTo(null)
            }
        }
    }

    private fun buildMenu() {

        // Menu buttons
        jmb = JMenuBar()
        jmSettings = JMenu("Settings")
        jmRecipes = JMenu("Recipes")
        jmLists = JMenu("Lists")

        // Menu items
            // Lists
        jmSubList = JMenu("Create new list"); jmLists.add(jmSubList)
        jmiToDoList = JMenuItem("Todo"); jmSubList.add(jmiToDoList); jmiToDoList.addActionListener{ switchPanels(States.NEWTODOLISTSTATE) }
        jmiPosNegList = JMenuItem("Positive/negative"); jmSubList.add(jmiPosNegList); jmiPosNegList.addActionListener { switchPanels(States.NEWPCLISTSTATE) }
        jmiSavList = JMenuItem("View all lists"); jmLists.add(jmiSavList); jmiSavList.addActionListener{ switchPanels(States.SAVEDLISTSTATE); SavListPane.loadLists() }
            // Settings
        jmiSettings = JMenuItem("Change save location"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ val csd = ChangeSaveDirectory(); csd.setLocationRelativeTo(this) }
        jmiSettings = JMenuItem("Go back to main menu"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ switchPanels(States.MENUSTATE) }
            // Recipes
        jmiNewRec = JMenuItem("Create new recipe"); jmRecipes.add(jmiNewRec); jmiNewRec.addActionListener{ switchPanels(States.NEWRECIPESTATE) }
        /*jmiDesRec = JMenu("Desserts"); jmRecipes.add(jmiDesRec); jmiDesRec.addActionListener{  }
        jmiExtraRec = JMenu("Extras"); jmRecipes.add(jmiExtraRec); jmiExtraRec.addActionListener{  }
        jmiMeatRec = JMenu("Meats"); jmRecipes.add(jmiMeatRec); jmiMeatRec.addActionListener{  }*/
        jmiSavRec = JMenuItem("View all saved recipes"); jmRecipes.add(jmiSavRec); jmiSavRec.addActionListener { switchPanels(States.SAVEDRECIPESTATE) }
        jmiFavRec = JMenuItem("View all favorite recipes"); jmRecipes.add(jmiFavRec); jmiFavRec.addActionListener{ switchPanels(States.FAVORITERECIPESTATE) }
                // Sub categories
                    // Desserts
        /*jmiSubCatRec = JMenuItem(subCatDesserts[0]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[1]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[2]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[3]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[4]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[5]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[6]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[7]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[8]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[9]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[10]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[11]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatDesserts[12]) ; jmiDesRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
                    // Extras
        jmiSubCatRec = JMenuItem(subCatExtras[0]) ; jmiExtraRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatExtras[1]) ; jmiExtraRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatExtras[2]) ; jmiExtraRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatExtras[3]) ; jmiExtraRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatExtras[4]) ; jmiExtraRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
                    // Meats
        jmiSubCatRec = JMenuItem(subCatMeats[0]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatMeats[1]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatMeats[2]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatMeats[3]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatMeats[4]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }
        jmiSubCatRec = JMenuItem(subCatMeats[5]) ; jmiMeatRec.add(jmiSubCatRec); jmiSubCatRec.addActionListener{  }*/
    }

    private fun changeTitle(panel: Int) : String {
        return when (panel) {
            States.MENUSTATE -> "$TITLE - Main Menu"
            States.NEWRECIPESTATE -> "$TITLE - Create New Recipe"
            States.SAVEDRECIPESTATE -> "$TITLE - View Saved Recipes"
            States.FAVORITERECIPESTATE -> "$TITLE - View Favorite Recipes"
            States.NEWPCLISTSTATE -> "$TITLE - Create New Pros/Cons List"
            States.SAVEDLISTSTATE -> "$TITLE - View Saved Lists"
            States.NEWTODOLISTSTATE -> "$TITLE - Create New ToDo List"
            else -> TITLE
        }
    }

    private fun changeSize(panel: Int) : Dimension {
        return when (panel) {
            States.MENUSTATE -> Dimension(backgroundImage.width, backgroundImage.height)
            States.NEWRECIPESTATE -> Dimension(1025, 975)
            States.SAVEDRECIPESTATE -> Dimension(600, 600)
            States.FAVORITERECIPESTATE -> Dimension(600, 600)
            States.NEWPCLISTSTATE -> Dimension(800, 780)
            States.SAVEDLISTSTATE -> Dimension(400, 640)
            States.NEWTODOLISTSTATE -> Dimension(400, 750)
            else -> Dimension(backgroundImage.width, backgroundImage.height)
        }
    }

    private fun showPanel(panel: JPanel, name: Int) {
        MenuPane.isVisible = false
        NewRecPane.isVisible = false
        SavRecPane.isVisible = false
        FavRecPane.isVisible = false
        NewPosNegListPane.isVisible = false
        SavListPane.isVisible = false
        NewToDoListPane.isVisible = false
        cl.show(mainPane, panels[name])
        panel.isVisible = true
    }

}