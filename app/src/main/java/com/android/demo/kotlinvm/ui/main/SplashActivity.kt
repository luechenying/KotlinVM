package com.android.demo.kotlinvm.ui.main


import android.text.Html
import android.text.Spanned
import android.view.animation.AlphaAnimation
import com.android.baselibrary.base.BaseActivity
import com.android.demo.kotlinvm.R
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.main_activity_splash.*
import org.jetbrains.anko.startActivity

/**
 * Des:  启动页
 */
class SplashActivity : BaseActivity() {

    private var alphaAnimation: AlphaAnimation? = null

    override fun getLayoutId(): Int = R.layout.main_activity_splash

    override fun initView() {
        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 800

    }

    override fun initData() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted { handleLogicAndJump() }
                .onDenied { handleLogicAndJump() }
                .start()
    }

    private fun handleLogicAndJump() {
        //TODO 逻辑处理
        // 模拟1000后跳转首页
        layout_splash.postDelayed({
            startActivity<MainActivity>()
            this@SplashActivity.finish()
        }, 1000)

    }

    private fun getMsg(permission: String): Spanned {
        return Html.fromHtml(String.format("您拒绝了我们App的权限申请请求,我们需要获取<font color='#0378d8'>%s</font>权限，否则您将无法正常使用该功能", permission))
    }
}