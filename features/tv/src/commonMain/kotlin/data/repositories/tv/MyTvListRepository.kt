package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.mappers.toDomainMyTvList
import com.hrudhaykanth116.tv.data.mappers.toEntity
import com.hrudhaykanth116.tv.domain.models.MyTv
import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyTvListRepository(
    private val localDataSource: MyTvListLocalDataSource,
) : IMyTvListRepository {

    override fun observeMyTvList(): Flow<List<MyTv>> {
        return localDataSource.observeMyTvList().map { it.toDomainMyTvList() }
    }

    override suspend fun getMyTvList(): List<MyTv> {
        return localDataSource.getMyTvList().toDomainMyTvList()
    }

    override suspend fun insertMyTv(myTv: MyTv) {
        localDataSource.insertMyTvEntity(myTv.toEntity())
    }

    override suspend fun updateMyTv(myTv: MyTv) {
        localDataSource.updateMyTvEntity(myTv.toEntity())
    }

    override suspend fun deleteMyTvListItem(myTv: MyTv) {
        localDataSource.deleteMyTvListItemEntity(myTv.toEntity())
    }

    override suspend fun deleteMyTv(id: Int) {
        localDataSource.deleteMyTv(id)
    }

}