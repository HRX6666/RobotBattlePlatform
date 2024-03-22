package com.plcoding.bluetoothchat.presentation.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.plcoding.bluetoothchat.Joysticks.RockerView
import com.plcoding.bluetoothchat.Joysticks.RockerView.OnRockerListener
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.BluetoothUiState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.semantics.Role
import com.plcoding.bluetoothchat.domain.chat.BluetoothMessage
import java.time.Clock.offset

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  Playgame (state: BluetoothUiState,
               onDisconnect: () -> Unit,
               onSendMessage: (String,String) -> Unit,
) {
    var x:String=""
    var y:String=""
    /**
     * 对小车接收数据
     * 防御模块血量，攻击模块血量，行走模块血量，核心模块是否被摧毁，防御模块装甲值，武器模块伤害，射速，行走模块速度
     */
    val STA:String="STA"
    var prevent_blood :String=""//防御模块血量
    var arms_blood:String=""//武器模块血量
    var walk_blood:String=""//行走模块血量
    var isDie :String=""//核心模块是否被摧毁
    var meHurt:String=""//我方武器模块伤害
    var firingRate:String=""//射速
    val workRate:String=""//行走模块速度
    val inputString = "1234567"
    val byteArray = inputString.toByteArray()
    /**
     * 向小车发送数据
     * 开火，发动技能，向前，向后，左转，右转，比赛开始，比赛结束
     */
    val ATS:String="ATS"
    var fire by remember { mutableStateOf("0") }//开火
    var fire_b:Boolean=false
    var who:String="0"//哪方发动技能
    var skill:String="0"//发动技能
    var direction:String="0"//方向
    var isgame:String="1"//比赛进程
    var you_hurt:String="1"//敌方武器模块伤害

    Image(
        painter = painterResource(id = R.drawable.bg1),
        contentDescription = null, // 可以设置图片描述
        modifier = Modifier.fillMaxSize()
    )
    Box (){
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null, // 可以设置图片描述
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds

        )
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                AndroidView(
                    factory =
                    { context ->
                        RockerView(context).apply {
                            getDate { rocker, pX, pY ->
                                x = pX.toString()
                                y = pY.toString()
                                //onSendMessage(ATS,"  "+pX+"  "+pY.toString())
                                if (y == "-12.484848") {
                                    //向前移动
                                    direction = "1"
                                    delayMilliseconds(30)
                                    onSendMessage(
                                        ATS,
                                        fire + who + skill + direction + isgame + you_hurt
                                    )
                                } else if (y == "12.484848") {
                                    //向后移动
                                    direction = "2"
                                    delayMilliseconds(30)
                                    onSendMessage(
                                        ATS,
                                        fire + who + skill + direction + isgame + you_hurt
                                    )
                                } else if (x == "12.484848") {
                                    //向右运动
                                    direction = "4"
                                    delayMilliseconds(30)
                                    Log.d("move", fire)
                                    onSendMessage(
                                        ATS,
                                        fire + who + skill + direction + isgame + you_hurt
                                    )
                                } else if (x == "-12.484848") {
                                    //向左运动
                                    direction = "3"
                                    delayMilliseconds(30)
                                    onSendMessage(
                                        ATS,
                                        fire + who + skill + direction + isgame + you_hurt
                                    )
                                } else {
                                    direction = "0"
                                    onSendMessage(
                                        ATS,
                                        fire + who + skill + direction + isgame + you_hurt
                                    )
                                }

                            }
                        }
                    },
                    modifier = Modifier
                        .offset(y = 135.dp),
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(start = 320.dp)
                ) {

                    Text(text = "血量"+Character.getNumericValue(byteArray[1].toInt()),
                        style = TextStyle(fontSize =12.sp ),
                        color= Color(0x6AEDEDED),
                        modifier = Modifier
                            .offset(y=20.dp,x=50.dp)
                    )
                    LinearProgressIndicator(
                        progress = 0.6.toFloat(),
                        color= Color(0xCDB10000),// 进度值，范围为0.0到1.0
                        modifier = Modifier
                            .size(100.dp, 12.dp)
                            .offset(y = 20.dp, x = 50.dp) // 填充父布局的宽度
                    )
                    Text(text = "技能"+state.messages,
                        style = TextStyle(fontSize =12.sp ),
                        color= Color(0x6AEDEDED),
                        modifier = Modifier
                            .offset(y=44.dp,x=70.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.pozhen),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp, 75.dp)
                            .offset(y = 50.dp, x = 70.dp),
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.CenterStart
                    )

                    Card(
                        shape = RoundedCornerShape(700.dp),
                        backgroundColor = Color(0x6AEDEDED),
                        elevation = 4.dp,
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .offset(y = 90.dp, x = 67.dp)
                            .clickable(
                                enabled = true,
                                onClickLabel = "开火键",
                                onClick = {
                                    fire_b=true
                                    Log.d("TAG", "Playgame: ${fire}")
                                    onSendMessage(
                                        ATS,
                                        "1" + who + skill + direction + isgame + you_hurt
                                    )
                                },
                                role = Role.Button,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )





                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.zidan),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }
            }
        }

    }
    }
@Composable
fun MyComposeLayout() {
}
fun delayMilliseconds(milliseconds: Long) {
    val start = System.currentTimeMillis()
    var elapsed: Long
    do {
        elapsed = System.currentTimeMillis() - start
    } while (elapsed < milliseconds)
}

@Preview(name = "Landscape Preview", showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL or Configuration.UI_MODE_NIGHT_NO, widthDp = 1000, heightDp = 600)
@Composable
fun PreviewMyComposeLayout() {
    MyComposeLayout()
}







