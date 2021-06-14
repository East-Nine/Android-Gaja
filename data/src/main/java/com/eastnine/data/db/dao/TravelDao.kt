package com.eastnine.data.db.dao

import androidx.room.*
import com.eastnine.data.db.entity.PlaceData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TravelDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: PlaceData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg userData: PlaceData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: List<PlaceData>): Completable

    //delete
    @Delete
    fun delete(userData: PlaceData): Completable

    @Delete
    fun delete(vararg userData: PlaceData): Completable

    @Delete
    fun delete(userData: List<PlaceData>): Completable

    @Query("""
        SELECT * 
        FROM place 
        """)
    fun getData(): Single<List<PlaceData>>

    @Query("""
        SELECT * 
        FROM place 
        WHERE id = :id
        """)
    fun getData(id: Int): Single<List<PlaceData>>

    @Query("""
        SELECT * 
        FROM place 
        ORDER BY rate ASC
        LIMIT :itemSize OFFSET :itemSize * (:page - 1)
        """)
    fun getSortRateASC(itemSize: Int, page: Int): Single<List<PlaceData>>

    @Query("""
        SELECT * 
        FROM place 
        ORDER BY rate DESC
        LIMIT :itemSize OFFSET :itemSize * (:page - 1)
    """)
    fun getSortRateDESC(itemSize: Int, page: Int): Single<List<PlaceData>>

    @Query("""
        SELECT * 
        FROM place 
        ORDER BY `create` ASC
        LIMIT :itemSize OFFSET :itemSize * (:page - 1)
    """)
    fun getSortTimeASC(itemSize: Int, page: Int): Single<List<PlaceData>>

    @Query("""
        SELECT * 
        FROM place 
        ORDER BY `create` DESC
        LIMIT :itemSize OFFSET :itemSize * (:page - 1)
    """)
    fun getSortTimeDESC(itemSize: Int, page: Int): Single<List<PlaceData>>
}