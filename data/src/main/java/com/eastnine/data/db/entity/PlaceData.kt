package com.eastnine.data.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "place")
data class PlaceData(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rate") val rate: Double,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "imagePath") val imagePath: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "like") val like: Boolean,
    @ColumnInfo(name = "create") val create: Long
): Parcelable {
    override fun equals(other: Any?): Boolean =
        if (other is PlaceData) {
            other.id == id
        } else {
            super.equals(other)
        }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + rate.hashCode()
        result = 31 * result + thumbnail.hashCode()
        result = 31 * result + imagePath.hashCode()
        result = 31 * result + price
        result = 31 * result + subject.hashCode()
        result = 31 * result + like.hashCode()
        result = 31 * result + create.hashCode()
        return result
    }
}