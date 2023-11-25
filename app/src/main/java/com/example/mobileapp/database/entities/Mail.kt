package com.example.mobileapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "mails",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.RESTRICT
        )
    ]
)
data class Mail(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "postdate")
    val postdate: Long? = Date().time,
    @ColumnInfo(name="user_id")
    val userId: Int
){
    override fun hashCode(): Int {
        return id ?: -1
    }
}
