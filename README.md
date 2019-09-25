# BDialog
[![](https://jitpack.io/v/cbfg5210/BDialog.svg)](https://jitpack.io/#cbfg5210/BDialog)

最近又抽空重新封装了一下 DialogFragment，其中有参考了这篇文章：[所见即所得 dialog](https://juejin.im/post/5b3f6e33e51d4519503af921)。下面和大家分享一下封装后的使用方法，还望各位看官多多指点!

## 引入依赖
### Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
	repositories {
	  ...
	  maven { url 'https://jitpack.io' }
    }
}
```
### Step 2. Add the dependency
```gradle
dependencies {
       implementation 'com.github.cbfg5210:BDialog:0.2'
}
```

## 使用
```java
BDialog.get()
        //设置主题
        //.apply { setStyle(DialogFragment.STYLE_NORMAL, R.style.BLayoutDialogTheme) }

        //将外部数据传递进去,在 init 方法中获取出来使用
        .apply { args().putString("key", "value") }
        /*
         * 页面设置了全屏的前提下,
         * Dialog 显示的时候可能会触发虚拟导航栏显示出来,
         * setFullScreen(true)可以隐藏虚拟导航栏,
         * 不过会看到导航栏由显示到隐藏的过程
         */
        .setFullScreen(true)
        /*
         * 页面设置了全屏的前提下,
         * setNotFocusable(true)不会触发虚拟导航栏显示出来,
         * 不适用于需要输入内容的弹窗,因为会导致输入框无法获取到焦点
         */
        .setNotFocusable(true)

        //通过重写 DialogFragment.onCreateDialog(...) 方法实现弹窗
        //.init { dialog, builder -> }

        //通过重写 DialogFragment.onCreateView(...) 方法实现弹窗
        .init(R.layout.layout_dialog_input) { dialog, view ->
            //获取传递进来的数据使用
            val value = dialog.args().getString("key")

            //隐藏弹窗
            dialog.dismiss(lifecycle)
        }
        //弹窗关闭回调
        .setDismissListener(DialogInterface.OnDismissListener { })
        //使用这个方法显示弹窗,无惧 IllegalStateException (Can not perform this action after onSaveInstanceState)
        .show(lifecycle, childFragmentManager, "DialogTag")
```

#### 最后附上几个 Demo 截图：

![capture_dialog](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_dialog.png)

![capture_dialog_1](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_dialog_1.png)

![capture_dialog_2](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_dialog_2.png)

![capture_layout](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_layout.png)

![capture_layout_1](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_layout_1.png)

![capture_layout_2](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_layout_2.png)

![capture_layout_3](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_layout_3.png)

![capture_layout_4](https://raw.githubusercontent.com/cbfg5210/BDialog/master/captures/capture_layout_4.png)
