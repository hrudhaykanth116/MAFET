package com.hrudhaykanth116.todo.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TaskCategoryTest {

    @Test
    fun `fromKey returns correct category for valid input`() {
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("Work"))
        assertEquals(TaskCategory.PERSONAL, TaskCategory.fromKey("Personal"))
        assertEquals(TaskCategory.SHOPPING, TaskCategory.fromKey("Shopping"))
    }

    @Test
    fun `fromKey is case insensitive`() {
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("work"))
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("WORK"))
        assertEquals(TaskCategory.WORK, TaskCategory.fromKey("WoRk"))
    }

    @Test
    fun `fromKey returns GENERAL for unknown input`() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("unknown"))
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("random"))
    }

    @Test
    fun `fromKey returns GENERAL for null`() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey(null))
    }

    @Test
    fun `fromKey returns GENERAL for blank`() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey(""))
        assertEquals(TaskCategory.GENERAL, TaskCategory.fromKey("   "))
    }

    @Test
    fun `key matches expected values`() {
        assertEquals("General", TaskCategory.GENERAL.key)
        assertEquals("Work", TaskCategory.WORK.key)
        assertEquals("Personal", TaskCategory.PERSONAL.key)
    }

    @Test
    fun `DEFAULT is GENERAL`() {
        assertEquals(TaskCategory.GENERAL, TaskCategory.DEFAULT)
    }
}
