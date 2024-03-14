package com.plcoding.bluetoothchat.domain.chat

/**
 * 定义不同的连接结果
 */
sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult //对象连接结果
    data class TransferSucceeded(val message: BluetoothMessage): ConnectionResult
    data class Error(val message: String): ConnectionResult //错误
}