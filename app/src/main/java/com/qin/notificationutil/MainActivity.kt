package com.qin.notificationutil

import android.content.ComponentName
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val mNtifyWrapper by lazy {
        NotifiationWrapper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn1 -> {
                mNtifyWrapper.showNotifyOn80()
            }
            R.id.btn2 -> {
                mNtifyWrapper.showNotifyOn801()
            }
            R.id.btn3 -> {
                mNtifyWrapper.showNotifyOn802()
            }

            R.id.btn4 -> {
                mNtifyWrapper.showNotifyOn803()
            }
            R.id.btn5 -> {
                mNtifyWrapper.showNotifyOn804()
            }
            R.id.btn6 -> {
                mNtifyWrapper.bigTextStyle()
            }
            R.id.btn7 -> {
                mNtifyWrapper.inboxStyle()
            }
            R.id.btn8 -> {
                mNtifyWrapper.progress()
            }
            R.id.btn9 -> {
                mNtifyWrapper.bigTextStyle()
            }

            else -> {
                toast("点击事件未响应")
            }
        }
    }

    /**
     * 判断 Notification access 是否开启
     * @return
     */
    //此为 Settings 中的常量,不过是属于隐藏字段
    private val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    private fun isEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
