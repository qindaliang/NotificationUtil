package com.qin.notificationutil

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import org.jetbrains.anko.notificationManager
import android.widget.Toast
import android.content.Intent
import android.app.PendingIntent
import android.support.v4.app.TaskStackBuilder


/**
 * Created by Qin
 * on 2018/11/24.
 */

class NotifiationWrapper(context: Context?) : ContextWrapper(context) {

    private var mContext: Context? = null

    init {
        mContext = context
    }

    companion object {
        const val CHANNEL_ID0 = "id_名称0"
        const val CHANNEL_NAME0 = "name_描述0"
        const val CHANNEL_ID1 = "id_名称1"
        const val CHANNEL_NAME1 = "name_描述1"
        const val CHANNEL_ID2 = "id_名称2"
        const val CHANNEL_NAME2 = "name_描述2"
    }

    fun getNotificationManager(): NotificationManager {
        return mContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun showNotifyOn80() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel0 = NotificationChannel(
                CHANNEL_ID0,
                CHANNEL_NAME0,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel0.apply {
                name = "name0"
                description = "description0"
                //  group = "group0"
                enableLights(true)//是否在桌面icon右上角展示小红点
                lightColor = Color.RED // 小红点颜色
                setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                enableVibration(true)//开启震动
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI, Notification.AUDIO_ATTRIBUTES_DEFAULT)
            }
            getNotificationManager().createNotificationChannel(channel0)

            val channel1 = NotificationChannel(
                CHANNEL_ID1,
                CHANNEL_NAME1,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel1.apply {
                name = "name1"
                description = "description1"
            }
            getNotificationManager().createNotificationChannel(channel1)

            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID0)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        builder.apply {
            setContentTitle("标题")
            setContentText("内容text")
            setContentInfo("信息info")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID0)
            setNumber(5)
            setAutoCancel(true)
        }
        getNotificationManager().notify(1, builder.build())
    }

    fun showNotifyOn801() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }
        builder.apply {
            setContentTitle("标题1")
            setContentText("内容text1")
            setContentInfo("信息info1")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setNumber(5)
            setAutoCancel(true)
        }
        val notification = builder.build()
        notification.flags = Notification.FLAG_NO_CLEAR//不能滑动取消，只能cancel
        // notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL 自动取消
        //notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT//正在运行 只能cancel
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun showNotifyOn802() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
                shouldShowLights()
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        //发送一个点击跳转到MainActivity的消息
        val mainIntent = Intent(this, MainActivity::class.java)
        val mainPendingIntent = PendingIntent.getActivity(mContext, 2, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        val vibrate = longArrayOf(0, 500, 1000, 1500)

        builder.apply {
            setContentTitle("标题2")
            setContentText("内容text2")
            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setNumber(5)
            setAutoCancel(true)
            setContentIntent(mainPendingIntent)
            setVibrate(vibrate)
            setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
            setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
            //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
            setLights(0xFF0000, 3000, 3000);
            //调用系统默认响铃,设置此属性后setSound()会无效
            //setDefaults(Notification.DEFAULT_SOUND)
            //调用系统多媒体裤内的铃声
            //setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));
            //调用自己提供的铃声
            //    setSound(Uri.parse("android.resource://com.littlejie.notification/" + R.raw.sound));

            //等价于setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
            //setDefaults(Notification.DEFAULT_ALL);
        }
        val notification = builder.build()
        //只有在设置了标志符Flags为Notification.FLAG_SHOW_LIGHTS的时候，才支持呼吸灯提醒。
        //   notification.flags = Notification.FLAG_SHOW_LIGHTS
        // notification.flags = Notification.FLAG_NO_CLEAR//不能滑动取消，只能cancel
        // notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL 自动取消
        //notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT//正在运行 只能cancel
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun showNotifyOn803() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        //发送一个点击跳转到MainActivity的消息
        val resultIntent = Intent(this, NormalActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        // Adds the back stack
        stackBuilder.addParentStack(NormalActivity::class.java)
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent)
        // Gets a PendingIntent containing the entire back stack
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.apply {
            setContentTitle("标题2")
            setContentText("点击跳转activity,正常流程退出")
            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setNumber(5)
            setAutoCancel(true)
            setContentIntent(resultPendingIntent)
        }
        val notification = builder.build()
        notification.flags = Notification.FLAG_NO_CLEAR//不能滑动取消，只能cancel
        // notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL 自动取消
        //notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT//正在运行 只能cancel
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun showNotifyOn804() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        //发送一个点击跳转到MainActivity的消息
        // Creates an Intent for the Activity
        val notifyIntent = Intent(this, SpecialActivity::class.java)
        // Sets the Activity to start in a new, empty task
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // Creates the PendingIntent
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.apply {
            setContentTitle("标题2")
            setContentText("进入特殊页面,按返回键直接回到桌面")
            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setNumber(5)
            setAutoCancel(true)
            setContentIntent(notifyPendingIntent)
        }
        val notification = builder.build()
        notification.flags = Notification.FLAG_NO_CLEAR//不能滑动取消，只能cancel
        // notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL 自动取消
        //notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT//正在运行 只能cancel
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun inboxStyle() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle("系统支持InboxStyle时显示的标题")
            //InboxStyle最多支持添加5行数据，超过5行不显示
            .addLine("Line 1")
            .addLine("Line 2")
            .addLine("Line 6")
            .setSummaryText("+3 more")
        builder.apply {
            //            setContentTitle("标题2")
//            setContentText("bigTextStyle")
//            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setAutoCancel(true)
            setStyle(inboxStyle)
        }
        val notification = builder.build()
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun bigTextStyle() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        val bigTextStyle = NotificationCompat.BigTextStyle()
        //相当于 setContentTitle()
        bigTextStyle.setBigContentTitle("系统支持BigTextStyle时显示的标题")
        //bigText() 方法相当于 setContentText()
        bigTextStyle.bigText("系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n系统支持BigTextStyle\n")
        //该方法没什么用，可以不设置
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle("系统支持InboxStyle时显示的标题")
            //InboxStyle最多支持添加5行数据，超过5行不显示
            .addLine("Line 1")
            .addLine("Line 2")
            .addLine("Line 6")
            .setSummaryText("+3 more")
        builder.apply {
            //            setContentTitle("标题2")
//            setContentText("bigTextStyle")
//            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setLargeIcon(BitmapFactory.decodeResource(mContext.resources, R.drawable.dog))
            setChannelId(CHANNEL_ID2)
            setAutoCancel(true)
            setStyle(bigTextStyle)
        }
        val notification = builder.build()
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun progress() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        builder.apply {
            setContentTitle("标题2")
//            setContentText("bigTextStyle")
//            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setChannelId(CHANNEL_ID2)
            setProgress(100, 0, false);
            //下载以及安装线程模拟
            Thread {
                for (i in 0..100) {
                    setProgress(100, i, false)
                    getNotificationManager().notify(2, builder.build())
                    //下载进度提示
                    setContentText("下载$i%")
                    Thread.sleep(50)
                }
                //下载完成后更改标题以及提示信息
                setContentTitle("开始安装")
                setContentText("安装中...")
                //设置进度为不确定，用于模拟安装
                setProgress(0, 0, true)
                getNotificationManager().notify(2, builder.build())
                //                manager.cancel(NO_3);//设置关闭通知栏
            }.start()
        }
        val notification = builder.build()
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }

    fun progress1() {
        val builder = NotificationCompat.Builder(mContext!!, CHANNEL_ID2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel2 = NotificationChannel(
                CHANNEL_ID2,
                CHANNEL_NAME2,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.apply {
                name = "name2"
                description = "description2"
            }
            getNotificationManager().createNotificationChannel(channel2)
            val channel = getNotificationManager().getNotificationChannel(CHANNEL_ID2)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show()
            }
        }

        builder.apply {
            setContentTitle("标题2")
            setContentText("bigTextStyle")
            setContentInfo("信息info2")
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            setChannelId(CHANNEL_ID2)
        }
        val notification = builder.build()
        getNotificationManager().notify(2, notification)//2每一条通知的id都不一样，一样则替换掉前面一条
    }
}