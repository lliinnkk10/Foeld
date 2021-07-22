package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.panels.MenuPanel
import com.github.atheera.recipemanager.gui.panels.list.NewPCListPanel
import com.github.atheera.recipemanager.gui.panels.list.NewTodoListPanel
import com.github.atheera.recipemanager.gui.panels.list.SavedListsPanel
import com.github.atheera.recipemanager.gui.panels.recipe.*
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
private lateinit var SavDesRecPan: SavedDessertRecipePanel
private lateinit var SavExtRecPan: SavedExtraRecipePanel
private lateinit var SavMeaRecPan: SavedMeatRecipePanel
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
private lateinit var jmiDebug: JMenuItem
        // Recipes
private lateinit var jmiDesRec: JMenuItem
private lateinit var jmiExtraRec: JMenuItem
private lateinit var jmiMeatRec: JMenuItem
private lateinit var jmiSavRec: JMenu
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
    const val SAVEDDESSERTRECIPESTATE: Int = 7
    const val SAVEDEXTRARECIPESTATE: Int = 8
    const val SAVEDMEATRECIPESTATE: Int = 9
}
var currentState: Int = States.MENUSTATE
private val panels = listOf(
    "Menu",
    "New Recipe",
    "Saved Recipe",
    "Favorite Recipe",
    "New PC List",
    "New TODO List",
    "Saved List",
    "Saved Desserts",
    "Saved Extras",
    "Saved Meats"
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

        NewRecPane = NewRecipePanel(true)
        SavRecPane = SavedRecipePanel()
        SavDesRecPan = SavedDessertRecipePanel()
        SavExtRecPan = SavedExtraRecipePanel()
        SavMeaRecPan = SavedMeatRecipePanel()
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
        mainPane.add(SavDesRecPan, panels[7])
        mainPane.add(SavExtRecPan, panels[8])
        mainPane.add(SavMeaRecPan, panels[9])
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
            States.SAVEDDESSERTRECIPESTATE -> States.SAVEDDESSERTRECIPESTATE
            States.SAVEDEXTRARECIPESTATE -> States.SAVEDEXTRARECIPESTATE
            States.SAVEDMEATRECIPESTATE -> States.SAVEDMEATRECIPESTATE
            else -> States.MENUSTATE
        }
    }

    private fun setPanelInfo(panel: JPanel, state: Int) {
        showPanel(panel, state)
        size = changeSize(state)
        title = changeTitle(state)
        setCurrentState(state)
        setLocationRelativeTo(null)
    }

    private fun switchPanels(panel: Int) {
        when (panel) {
            States.MENUSTATE -> setPanelInfo(MenuPane, panel)
            States.NEWRECIPESTATE -> setPanelInfo(NewRecPane, panel)
            States.SAVEDRECIPESTATE -> setPanelInfo(SavRecPane, panel)
            States.FAVORITERECIPESTATE -> setPanelInfo(FavRecPane, panel)
            States.NEWPCLISTSTATE -> setPanelInfo(NewPosNegListPane, panel)
            States.NEWTODOLISTSTATE -> setPanelInfo(NewToDoListPane, panel)
            States.SAVEDLISTSTATE -> setPanelInfo(SavListPane, panel)
            States.SAVEDDESSERTRECIPESTATE -> setPanelInfo(SavDesRecPan, panel)
            States.SAVEDEXTRARECIPESTATE -> setPanelInfo(SavExtRecPan, panel)
            States.SAVEDMEATRECIPESTATE -> setPanelInfo(SavMeaRecPan, panel)
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
        jmiDebug = JMenuItem("Open debug window"); jmSettings.add(jmiDebug); jmiDebug.addActionListener{  }
            // Recipes
        jmiNewRec = JMenuItem("Create new recipe"); jmRecipes.add(jmiNewRec); jmiNewRec.addActionListener{ switchPanels(States.NEWRECIPESTATE) }
        jmiSavRec = JMenu("View all saved recipes"); jmRecipes.add(jmiSavRec); jmiSavRec.addActionListener {  }
        jmiDesRec = JMenuItem("Desserts"); jmiSavRec.add(jmiDesRec); jmiDesRec.addActionListener{ switchPanels(States.SAVEDDESSERTRECIPESTATE); SavDesRecPan.loadRecipes() }
        jmiExtraRec = JMenuItem("Extras"); jmiSavRec.add(jmiExtraRec); jmiExtraRec.addActionListener{ switchPanels(States.SAVEDEXTRARECIPESTATE); SavExtRecPan.loadRecipes() }
        jmiMeatRec = JMenuItem("Meats"); jmiSavRec.add(jmiMeatRec); jmiMeatRec.addActionListener{ switchPanels(States.SAVEDMEATRECIPESTATE); SavMeaRecPan.loadRecipes() }
        jmiFavRec = JMenuItem("View all favorite recipes"); jmRecipes.add(jmiFavRec); jmiFavRec.addActionListener{ switchPanels(States.FAVORITERECIPESTATE) }
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
            States.SAVEDDESSERTRECIPESTATE -> "$TITLE - View Saved Dessert Recipes"
            States.SAVEDEXTRARECIPESTATE -> "$TITLE - View Saved Extra Recipes"
            States.SAVEDMEATRECIPESTATE -> "$TITLE - View Saved Meat Recipes"
            else -> TITLE
        }
    }

    private fun changeSize(panel: Int) : Dimension {
        return when (panel) {
            States.MENUSTATE -> Dimension(backgroundImage.width, backgroundImage.height)
            States.NEWRECIPESTATE -> Dimension(1060, 975)
            States.SAVEDRECIPESTATE -> Dimension(1020, 600)
            States.FAVORITERECIPESTATE -> Dimension(600, 600)
            States.NEWPCLISTSTATE -> Dimension(800, 780)
            States.SAVEDLISTSTATE -> Dimension(600, 640)
            States.NEWTODOLISTSTATE -> Dimension(400, 750)
            States.SAVEDDESSERTRECIPESTATE -> Dimension(600, 740)
            States.SAVEDEXTRARECIPESTATE -> Dimension(600, 740)
            States.SAVEDMEATRECIPESTATE -> Dimension(600, 740)
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
        SavDesRecPan.isVisible = false
        SavExtRecPan.isVisible = false
        SavMeaRecPan.isVisible = false
        cl.show(mainPane, panels[name])
        panel.isVisible = true
    }

}