package com.hrudhaykanth116.todo.ui.mappers

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoDefaults
import com.hrudhaykanth116.todo.domain.model.TodoModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class TodoDomainModelMapperTest {

    private lateinit var dateTimeUtils: DateTimeUtils
    private lateinit var mapper: TodoDomainModelMapper

    @Before
    fun setup() {
        dateTimeUtils = mockk()
        every { dateTimeUtils.getFormattedDateTime(any(), any()) } returns "2024-01-15 10:30 AM"
        mapper = TodoDomainModelMapper(dateTimeUtils)
    }

    @Test
    fun `mapToUIModel with null returns default UIModel`() {
        val result = mapper.mapToUIModel(null)

        assertNull(result.id)
        assertEquals(TextFieldValue(), result.title)
        assertEquals(TextFieldValue(), result.description)
        assertEquals(TextFieldValue(TaskCategory.GENERAL.key), result.category)
        assertEquals(3, result.priority)
    }

    @Test
    fun `mapToUIModel maps all fields correctly`() {
        val todoModel = TodoModel(
            id = "task-123",
            title = "Buy groceries",
            description = "Milk, eggs, bread",
            category = TaskCategory.SHOPPING,
            priority = 4,
            targetTime = 1705312200000L
        )

        val result = mapper.mapToUIModel(todoModel)

        assertEquals("task-123", result.id)
        assertEquals("Buy groceries", result.title.text)
        assertEquals("Milk, eggs, bread", result.description.text)
        assertEquals(TaskCategory.SHOPPING.key, result.category.text)
        assertEquals(4, result.priority)
        assertEquals("2024-01-15 10:30 AM", result.targetTime.text)
    }

    @Test
    fun `mapToUIModel with null targetTime returns empty string`() {
        val todoModel = TodoModel(
            id = "1",
            title = "Task without deadline",
            targetTime = null
        )

        val result = mapper.mapToUIModel(todoModel)

        assertEquals("", result.targetTime.text)
    }

    @Test
    fun `mapToUIModel with empty description maps correctly`() {
        val todoModel = TodoModel(
            id = "1",
            title = "Simple task",
            description = ""
        )

        val result = mapper.mapToUIModel(todoModel)

        assertEquals("", result.description.text)
    }

    @Test
    fun `mapToUIModel preserves category key for all categories`() {
        val categories = listOf(
            TaskCategory.WORK,
            TaskCategory.PERSONAL,
            TaskCategory.SHOPPING,
            TaskCategory.HEALTH,
            TaskCategory.GENERAL
        )

        categories.forEach { category ->
            val todoModel = TodoModel(id = "1", title = "Test", category = category)
            val result = mapper.mapToUIModel(todoModel)
            assertEquals(category.key, result.category.text)
        }
    }

    @Test
    fun `mapToUIState wraps UIModel correctly`() {
        val todoModel = TodoModel(
            id = "state-test",
            title = "State mapping test",
            priority = 2
        )

        val result = mapper.mapToUIState(todoModel)

        assertEquals("state-test", result.data.id)
        assertEquals("State mapping test", result.data.title.text)
        assertEquals(2, result.data.priority)
    }

    @Test
    fun `mapToUIState with null returns default state`() {
        val result = mapper.mapToUIState(null)

        assertNull(result.data.id)
        assertEquals(TextFieldValue(), result.data.title)
    }

    @Test
    fun `mapListToUIStates maps all items`() {
        val todoModels = listOf(
            TodoModel(id = "1", title = "First task", priority = 1),
            TodoModel(id = "2", title = "Second task", priority = 5),
            TodoModel(id = "3", title = "Third task", priority = 3)
        )

        val result = mapper.mapListToUIStates(todoModels)

        assertEquals(3, result.size)
        assertEquals("First task", result[0].data.title.text)
        assertEquals("Second task", result[1].data.title.text)
        assertEquals("Third task", result[2].data.title.text)
    }

    @Test
    fun `mapListToUIStates with empty list returns empty`() {
        val result = mapper.mapListToUIStates(emptyList())

        assertEquals(0, result.size)
    }

    @Test
    fun `mapListToUIStates preserves order`() {
        val todoModels = listOf(
            TodoModel(id = "a", title = "Alpha"),
            TodoModel(id = "b", title = "Beta"),
            TodoModel(id = "c", title = "Gamma")
        )

        val result = mapper.mapListToUIStates(todoModels)

        assertEquals("a", result[0].data.id)
        assertEquals("b", result[1].data.id)
        assertEquals("c", result[2].data.id)
    }

    @Test
    fun `mapToUIModel with default priority uses default value`() {
        val todoModel = TodoModel(id = "1", title = "Default priority task")

        val result = mapper.mapToUIModel(todoModel)

        assertEquals(TodoDefaults.PRIORITY, result.priority)
    }

    @Test
    fun `mapToUIModel with min priority maps correctly`() {
        val todoModel = TodoModel(id = "1", title = "Low priority", priority = TodoDefaults.PRIORITY_MIN)

        val result = mapper.mapToUIModel(todoModel)

        assertEquals(TodoDefaults.PRIORITY_MIN, result.priority)
    }

    @Test
    fun `mapToUIModel with max priority maps correctly`() {
        val todoModel = TodoModel(id = "1", title = "High priority", priority = TodoDefaults.PRIORITY_MAX)

        val result = mapper.mapToUIModel(todoModel)

        assertEquals(TodoDefaults.PRIORITY_MAX, result.priority)
    }
}
