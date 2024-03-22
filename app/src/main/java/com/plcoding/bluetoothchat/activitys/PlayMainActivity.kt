package com.plcoding.bluetoothchat.activitys
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.BluetoothController
import com.plcoding.bluetoothchat.presentation.BluetoothUiState
import com.plcoding.bluetoothchat.presentation.BluetoothViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayMainActivity(state: BluetoothUiState,
                       onDisconnect: () -> Unit,
                       private val onSendMessage: (String,String) -> Unit): AppCompatActivity() {
    private lateinit var cardView: CardView
    private  lateinit var bluetoothController:BluetoothController// 假设BluetoothController是你自定义的类
    private val mainScope = MainScope()
    val context: Context by lazy { context }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_main)
        inite()
    }

    private fun inite() {
        cardView = findViewById(R.id.cd_fire)
        cardView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
                // 处理按钮点击事件的逻辑
                // 在初始化后调用sendMessage（假设这是你想要发送的消息）
                onSendMessage("STA","xxx")
            }
        })

    }

//    private fun sendMessage(message: String) {
//        // 使用协程来调用trySendMessage方法
//        mainScope.launch {
//            try {
//                val bluetoothMessage = withContext(Dispatchers.IO) {
//                    //bluetoothController.trySendMessage(message)
//                }
//                if (bluetoothMessage != null) {
//                    // 处理成功发送消息的逻辑
//                  // Toast.makeText(context, bluetoothMessage.message, Toast.LENGTH_SHORT).show()
//                }else if (bluetoothMessage==null){
//                    Toast.makeText(context, "xxxxxx", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mainScope.cancel()
//    }

}
