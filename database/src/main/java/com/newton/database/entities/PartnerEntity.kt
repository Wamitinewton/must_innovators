package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.newton.core.enums.PartnerType
import com.newton.core.enums.PartnershipStatus
import java.io.File

@Entity(tableName = "partners")
data class PartnerEntity(
    @PrimaryKey
    val id: Int,
    val name:String,
    val type: String,
    val description:String,
    val logo: String,
    val webUrl:String,
    val contactEmail:String,
    val contactPerson:String,
    val linkedIn:String,
    val twitter:String,
    val startDate: String,
    val endDate: String,
    val ongoing:Boolean,
    val status: String,
    val scope:String,
    val benefits:String,
    val eventsSupported:String,
    val resources:String,
    val achievements:String,
    val targetAudience:String
)
