package com.example.simply_watered_kotlin2.Models

data class UserModel(
    val accessFailedCount: Int,
    val concurrencyStamp: String,
    val email: String,
    val emailConfirmed: Boolean,
    val id: String,
    val lockoutEnabled: Boolean,
    val lockoutEnd: Any,
    val normalizedEmail: String,
    val normalizedUserName: String,
    val passwordHash: String,
    val phoneNumber: Any,
    val phoneNumberConfirmed: Boolean,
    val regionGroups: Any,
    val securityStamp: String,
    val twoFactorEnabled: Boolean,
    val userName: String
)