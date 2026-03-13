package com.hrudhaykanth116.todo.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SyncStatusTest {

    @Test
    fun `fromKey returns correct status for valid input`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("synced"))
        assertEquals(SyncStatus.PENDING_CREATE, SyncStatus.fromKey("pending_create"))
        assertEquals(SyncStatus.PENDING_UPDATE, SyncStatus.fromKey("pending_update"))
        assertEquals(SyncStatus.PENDING_DELETE, SyncStatus.fromKey("pending_delete"))
    }

    @Test
    fun `fromKey is case insensitive`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("SYNCED"))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("Synced"))
        assertEquals(SyncStatus.PENDING_CREATE, SyncStatus.fromKey("PENDING_CREATE"))
    }

    @Test
    fun `fromKey returns SYNCED for unknown input`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("unknown"))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("random"))
    }

    @Test
    fun `fromKey returns SYNCED for null`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey(null))
    }

    @Test
    fun `fromKey returns SYNCED for blank`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey(""))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("   "))
    }

    @Test
    fun `key matches expected values`() {
        assertEquals("synced", SyncStatus.SYNCED.key)
        assertEquals("pending_create", SyncStatus.PENDING_CREATE.key)
        assertEquals("pending_update", SyncStatus.PENDING_UPDATE.key)
        assertEquals("pending_delete", SyncStatus.PENDING_DELETE.key)
    }

    @Test
    fun `DEFAULT is SYNCED`() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.DEFAULT)
    }
}
