package com.oppo.marketdemo.utils;

import android.content.Intent;

import com.oppo.marketdemo.globle.VApplication;


public class CameraUtil {

    //照片模式
    public static Intent getPhotoStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 0);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    public static Intent getFontPhotoStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 0);
        intent.putExtra("front", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //前置美颜模式
    public static Intent getBeautyStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 0);
        intent.putExtra("front", true);
        intent.putExtra("beauty", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //夜景模式
    public static Intent getNightStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 1);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //夜景模式
    public static Intent getNightStyleFront() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 1);
        intent.putExtra("front", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //人像模式
    public static Intent getPortalStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 2);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //人像前置虚化模式
    public static Intent getPortalFrontStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 2);
        intent.putExtra("front", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //全景拍照
    public static Intent getPanoramaStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 3);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //专业拍照模式
    public static Intent getMajorStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 4);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //后置视频模式
    public static Intent getVideoStyle() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 6);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //前置视频模式
    public static Intent getVideoStyle2() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.setClassName("com.oppo.camera", "com.oppo.camera.Camera");
        intent.putExtra("mode", 6);
        intent.putExtra("front", true);
        intent.putExtra("rear", true);
        intent.setAction("com.oppo.camera.action.SHORTCUT_TYPE_MENU");
        return intent;
    }

    //即录2.0
    public static Intent Recording() {
        Intent intent = VApplication.getInstance().getPackageManager().getLaunchIntentForPackage("com.coloros.videoeditor");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    //超级省电
    public static Intent Saving() {
        Intent intent = new Intent("android.intent.action.POWER_USAGE_SUMMARY");
        return intent;
    }

    //防偷窥
    public static Intent Prevent() {
        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS");
        return intent;
    }

    //隔空手势
    public static Intent Hand() {
//        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS");
        Intent intent = new Intent("com.coloros.action.AONGestureActivity");
        return intent;
    }
}
