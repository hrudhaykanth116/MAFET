package com.hrudhaykanth116.todo.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class SyncStatusTest {

    @Test
    fun fromKey_returnsCorrectStatusForValidInput() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("synced"))
        assertEquals(SyncStatus.PENDING_CREATE, SyncStatus.fromKey("pending_create"))
        assertEquals(SyncStatus.PENDING_UPDATE, SyncStatus.fromKey("pending_update"))
        assertEquals(SyncStatus.PENDING_DELETE, SyncStatus.fromKey("pending_delete"))
    }

    @Test
    fun fromKey_isCaseInsensitive() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("SYNCED"))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("Synced"))
        assertEquals(SyncStatus.PENDING_CREATE, SyncStatus.fromKey("PENDING_CREATE"))
    }

    @Test
    fun fromKey_returnsSyncedForUnknownInput() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("unknown"))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("random"))
    }

    @Test
    fun fromKey_returnsSyncedForNull() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey(null))
    }

    @Test
    fun fromKey_returnsSyncedForBlank() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey(""))
        assertEquals(SyncStatus.SYNCED, SyncStatus.fromKey("   "))
    }

    @Test
    fun key_matchesExpectedValues() {
        assertEquals("synced", SyncStatus.SYNCED.key)
        assertEquals("pending_create", SyncStatus.PENDING_CREATE.key)
        assertEquals("pending_update", SyncStatus.PENDING_UPDATE.key)
        assertEquals("pending_delete", SyncStatus.PENDING_DELETE.key)
    }

    @Test
    fun default_isSynced() {
        assertEquals(SyncStatus.SYNCED, SyncStatus.DEFAULT)
    }
}
