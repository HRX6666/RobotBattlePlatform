package com.plcoding.bluetoothchat.domain.chat

data class BluetoothMessage(
    val message: String, //实际想发送的文本
    val senderName: String,//发件人名称
    val isFromLocalUser: Boolean ,//是否发送信息
    val end :String //发送的帧尾
)