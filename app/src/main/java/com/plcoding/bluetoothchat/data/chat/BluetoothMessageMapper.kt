package com.plcoding.bluetoothchat.data.chat

import com.plcoding.bluetoothchat.domain.chat.BluetoothMessage

/**
 * 将有字节的蓝牙转化成数组通过蓝牙发送
 * 从数组中获得字符串转换回蓝牙消息对象
 */
fun String.toBluetoothMessage(isFromLocalUser: Boolean): BluetoothMessage {//将字符串转换为蓝牙消息
    val name = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(
        message = message,
        senderName = name,
        isFromLocalUser = isFromLocalUser
    )
}

fun BluetoothMessage.toByteArray(): ByteArray {//两字节数组，通过数组返回
    return "$senderName#$message".encodeToByteArray()
}