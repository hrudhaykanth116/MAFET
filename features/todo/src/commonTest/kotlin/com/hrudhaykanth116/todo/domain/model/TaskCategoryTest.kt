package com.hrudhaykanth116.todo.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class TaskCategoryTest {

    @Test
    fun fromKey_returnsCorrectCategoryForValidInput() {
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("Work"))
        assertEquals(TaskCategory.PERSONAL, TaskCategory.fromKey("Personal"))
        assertEquals(TaskCategory.SHOPPING, TaskCategory.fromKey("Shopping"))
    }

    @Test
    fun fromKey_isCaseInsensitive() {
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("work"))
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("WORK"))
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("WoRk"))
    }

    @Test
    fun fromKey_returnsGeneralForUnknownInput() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("unknown"))
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("random"))
    }

    @Test
    fun fromKey_returnsGeneralForNull() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey(null))
    }

    @Test
    fun fromKey_returnsGeneralForBlank() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey(""))
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("   "))
    }

    @Test
    fun key_matchesExpectedValues() {
        assertEquals("General", TaskCategory.GENERAL.key)
        assertEquals("Work", TaskCategory.WORK.key)
        assertEquals("Personal", TaskCategory.PERSONAL.key)
    }

    @Test
    fun default_isGeneral() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.DEFAULT)
    }
}
