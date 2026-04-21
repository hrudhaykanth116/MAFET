package com.hrudhaykanth116.composeapp.testing

object TestTags {
    const val DASHBOARD_ROOT = "dashboard_root"
    const val HOME_BOTTOM_NAV = "home_bottom_nav"
    const val TODO_LIST_ROOT = "todo_list_root"
    const val TODO_ADD_BUTTON = "todo_add_button"
    const val TODO_TITLE_INPUT = "todo_title_input"

    fun bottomNavItem(name: String): String = "bottom_nav_item_$name"
    fun todoItem(title: String): String = "todo_item_$title"
}
