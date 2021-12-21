package com.example.simply_watered_kotlin2.Models

data class DeviceModel(
    val active: Boolean,
    val deviceId: Int,
    val deviceReadings: List<Any>,
    val deviceType: DeviceType,
    val irrigMode: Any,
    val irrigModeId: Int,
    val irrigationHistories: List<Any>,
    val maxHumidity: Double,
    val minimalHumidity: Double,
    val region: Any,
    val regionId: Int,
    val serialNumber: String,
    val typeId: Int
)