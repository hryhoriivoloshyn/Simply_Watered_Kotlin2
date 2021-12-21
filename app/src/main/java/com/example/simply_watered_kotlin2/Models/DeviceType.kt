package com.example.simply_watered_kotlin2.Models

data class DeviceType(
    val deviceDescription: String,
    val deviceName: String,
    val devices: List<Device>,
    val typeId: Int
)