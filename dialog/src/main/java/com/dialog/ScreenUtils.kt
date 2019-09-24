//package com.dialog
//
//import android.app.Activity
//import android.os.Build
//import android.view.View
//import android.view.Window
//import android.view.WindowManager
//
///**
// * 添加人：  Tom Hawk
// * 添加时间：2018/7/30 10:26
// * 功能描述：
// *
// * 修改人：  Tom Hawk
// * 修改时间：2018/7/30 10:26
// * 修改内容：
// */
//internal object ScreenUtils {
//    /**
//     * 隐藏虚拟按键，并且全屏
//     *
//     * @param context
//     */
//    @JvmStatic
//    fun hideBottomUIMenu(context: Activity) {
//        hideBottomUIMenu(context.window)
//    }
//
//    /**
//     * 隐藏虚拟按键
//     *
//     * @param window
//     * @param isAutoFullscreen 是否全屏
//     */
//    @JvmStatic
//    fun hideBottomUIMenu(window: Window, isAutoFullscreen: Boolean = true) {
//        totalFullScreen(window)
//        if (isAutoFullscreen) {
//            //当用户划出虚拟按键后，自动再隐藏
//            window.decorView.setOnSystemUiVisibilityChangeListener {
//                totalFullScreen(window)
//                //KLog.e("键盘是否弹出:" + (window.getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED));
//            }
//        }
//    }
//
//    /**
//     * 隐藏虚拟按键，并且全屏
//     *
//     * @param window
//     */
//    private fun totalFullScreen(window: Window) {
//        if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    // hide nav bar
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    // hide status bar
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//            window.decorView.systemUiVisibility = uiOptions
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            return
//        }
//        // lower api
//        window.decorView.systemUiVisibility = View.GONE
//    }
//}