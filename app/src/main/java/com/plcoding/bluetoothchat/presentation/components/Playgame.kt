package com.plcoding.bluetoothchat.presentation.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.layout.getDefaultLazyLayoutKey
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.plcoding.bluetoothchat.Joysticks.RockerView
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.BluetoothUiState
import androidx.compose.ui.semantics.Role
import com.plcoding.bluetoothchat.domain.chat.BluetoothMessage
import java.time.Clock.offset
import kotlin.reflect.KSuspendFunction0

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  Playgame (
    state: BluetoothUiState,
    onDisconnect: () -> Unit,
    onSendMessage: (String, String) -> Unit,

) {
    //摇杆的x、y轴的位置
    var x:String=""
    var y:String=""
    val message = rememberSaveable {
        mutableStateOf("")
    }
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
    var isgame:String="0"//比赛进程
    val item:Int=state.messages.size
    var message1: String = state.messages.lastOrNull()?.message ?: ""
    var message2:List<Int>?=null
    if (message1.isNotEmpty()) {
        Log.e("meassage1",message1)
        message2 = parseAsciiString(message1)
        Log.d("hex", message2.toString())
        // 继续处理 message2
    } else {
        // 处理空字符串的情况
    }






    /**
     * 对小车接收数据
     * 防御模块血量，攻击模块血量，行走模块血量，核心模块是否被摧毁，防御模块装甲值，武器模块伤害，射速，行走模块速度
     */
    var prevent_blood :Float= 100.0F
    var arms_blood:Float=100.0F
    var walk_blood:Float=100.0F
    var isDie :String="0"
    var meHurt:String="0"
    var firingRate:String="0"
    var workRate1:String="0"
    var workRate2:String="0"
    if (message2!=null&& message2.size>=12) {
        prevent_blood = message2[3].toFloat()//防御模块血量
        arms_blood=message2[4].toFloat()//武器模块血量
        walk_blood=message2[5].toFloat()//行走模块血量
        isDie =message2[6].toFloat().toString()//核心模块是否被摧毁
        meHurt=message2[7].toFloat().toString()//我方武器模块伤害
        firingRate=message2[8].toFloat().toString()//射速
        workRate1=message2[9].toFloat().toString()//行走模块速度
        workRate2=message2[10].toFloat().toString()//行走模块速度
    } else {
        // 处理长度不足的情况
    }
    var you_hurt:String=meHurt//敌方武器模块伤害

    val inputString = "1234567"
    val byteArray = inputString.toByteArray()


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
                Image(
                    painter = painterResource(id = robot(arms_blood,walk_blood)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp, 95.dp)
                        .offset(y = 50.dp, x = 60.dp),
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.CenterStart
                )
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
                        .offset(y = 45.dp),
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(start = 80.dp)
                ) {
                   Column(
                       modifier = Modifier.padding(start = 20.dp)
                   ) {
                       Text(text = "武器模块"+arms_blood+"%",
                           style = TextStyle(fontSize =11.sp ),
                           color= Color(0x6AEDEDED),
                           modifier = Modifier
                               .offset(y=20.dp,x=50.dp)
                       )

                       LinearProgressIndicator(
                           progress = arms_blood/100,
                           color= Color(0xCDEB6363),// 进度值，范围为0.0到1.0
                           modifier = Modifier
                               .size(100.dp, 12.dp)
                               .offset(y = 20.dp, x = 50.dp) // 填充父布局的宽度
                       )

                   }
                    Column(
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(text = "行走模块"+walk_blood+"%",
                            style = TextStyle(fontSize =11.sp ),
                            color= Color(0x6AEDEDED),
                            modifier = Modifier
                                .offset(y=20.dp,x=50.dp)
                        )
                        LinearProgressIndicator(
                            progress = walk_blood/100,
                            color= Color(0xCDFF3A3A),// 进度值，范围为0.0到1.0
                            modifier = Modifier
                                .size(100.dp, 12.dp)
                                .offset(y = 20.dp, x = 50.dp) // 填充父布局的宽度
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(text = "血量"+prevent_blood+"%",
                            style = TextStyle(fontSize =11.sp ),
                            color= Color(0x6AEDEDED),
                            modifier = Modifier
                                .offset(y=20.dp,x=50.dp)
                        )
                        LinearProgressIndicator(
                            progress = prevent_blood/100,
                            color= Color(0xCDB10000),// 进度值，范围为0.0到1.0
                            modifier = Modifier
                                .size(100.dp, 12.dp)
                                .offset(y = 20.dp, x = 50.dp) // 填充父布局的宽度
                        )
                        Text(text = "技能"+message2,
                            style = TextStyle(fontSize =10.sp ),
                            color= Color(0x6AEDEDED),
                            modifier = Modifier
                                .offset(y=50.dp,x=80.dp)
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
                                .offset(y = 90.dp, x = 58.dp)
                                .clickable(
                                    enabled = true,
                                    onClickLabel = "开火键",
                                    onClick = {
                                        fire_b = true
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
    }
@Composable
fun RoundedLinearProgressIndicator(preventBlood: Float) {
    val progress = preventBlood / 100

    Canvas(
        modifier = Modifier
            .size(100.dp, 12.dp)
            .offset(y = 20.dp, x = 50.dp)
    ) {
        drawRect(
            color = Color(0xCDC0BEBE),
            topLeft = Offset(0f, 0f),
            size = Size(size.width * progress, size.height),
            style = Stroke(width = 12f)
        )

        drawRoundRect(
            color = Color(0xCDB10000),
            topLeft = Offset(0f, 0f),
            size = Size(size.width * progress, size.height),
            cornerRadius = CornerRadius(6.dp.value, 6.dp.value)
        )
    }
}
/**
 * 任意毫秒延时函数
 */
fun delayMilliseconds(milliseconds: Long) {
    val start = System.currentTimeMillis()
    var elapsed: Long
    do {
        elapsed = System.currentTimeMillis() - start
    } while (elapsed < milliseconds)
}

fun parseAsciiString(ascii: String): List<Int> {

    return try {
      // ascii.split(" ").map { it.toInt() }
        val asciiValues = ascii.split(" ").map { if (it.length == 2 && it.all { c -> c in '0'..'9' || c in 'A'..'F' || c in 'a'..'f' }) Integer.parseInt(it, 16) else throw NumberFormatException() }
        val asciiChars = asciiValues.map { it.toChar().toInt() }
        asciiChars
        } catch (e: NumberFormatException) {
        emptyList()

        }

}
fun robot(arm:Float,walk:Float): Int {
    var armResource = if (arm>50.0&&walk>50.0) {
        R.drawable.robot
    } else if(arm<=50.0F&&arm!=0.0F&&walk>50.0){
        R.drawable.robot_arm1
    } else if(arm<=50.0F&&arm!=0.0F&&walk<=50.0&&walk!=0.0F){

        R.drawable.robot_w_a
    }else if(arm==0.0F&&walk>50.0){
        R.drawable.robot_arm2
    }else if(arm==0.0F&&walk==0.0F){
        R.drawable.robot_all
    }else if(arm>50.0&&walk<=50.0&&walk!=0.0F){
        R.drawable.robot_walk1
    }else if(arm>50.0&&walk==0.0F){
        R.drawable.robot_walk2
    }else{
        R.drawable.robot
    }



    // 根据arm和walk的值选择不同的图片资源
   return armResource
}
fun hexToDecimal(hex: String): Int {
    return try {
        hex.toInt(16)
    }catch (e:NumberFormatException){
        0
    }
}

fun extractValueAtIndex(values: List<Int>, index: Int): Int? {
    if (index < 0 || index >= values.size) {
        return null
    }
    return values[index]
}


@Preview(name = "Landscape Preview", showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL or Configuration.UI_MODE_NIGHT_NO, widthDp = 1000, heightDp = 600)
@Composable
fun PreviewMyComposeLayout() {

}
