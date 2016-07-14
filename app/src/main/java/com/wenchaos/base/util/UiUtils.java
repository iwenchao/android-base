package com.wenchaos.base.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.wenchaos.base.R;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UiUtils {

    /**
     * 隐藏输入法键盘
     *
     * @param ctx
     */
    public static void hideKeyboard(Context ctx) {
        if(ctx == null) return;
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showErrorToaster(int resId, Context context) {
        String errorMessage = context.getResources().getString(resId);
        showErrorToaster(errorMessage, context);
    }

    /**
     * 提示Toaster
     *
     * @param errorMessage 为空时提示默认信息
     * @param context
     */
    public static void showErrorToaster(String errorMessage, Context context) {
        if (context instanceof Activity) {
            Toaster.showShort((Activity) context, TextUtils.isEmpty(errorMessage) ?
                    context.getResources().getString(R.string.error_genernal) : errorMessage);
        }
    }

    /**
     * long类型时间格式化
     */
    public static String convertToTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(time);
        return df.format(date);
    }

    /**
     * 获得图片Url
     *
     * @param sever
     * @param path
     * @return
     */
    public static String getImageUrl(String sever, String path) {
        return "http://" + sever + "/upload_files/" + path;
    }

    public static Uri getDraweeImageUri(String url){
        if(TextUtils.isEmpty(url)){
            url = "";
        }
        return Uri.parse(url);
    }


    /**
     * 返回距离显示
     *
     * @param distance
     * @return
     */
    public static String getNearByDistance(double distance) {
        if (distance <= 0.05) {
            if(distance == 0){
                return "暂无";
            }else {
                return "小于50m";
            }
        } else if (distance > 0.05 && distance < 1) {
            return FomatDouble(distance * 1000, 0) + "m";
        } else {
            return FomatDouble(distance, 0) + "Km";
        }
    }


    /**
     * 检查手机号码合法性
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileValid(@NonNull String mobile) {
        return mobile.length() == 11;
    }

    /**
     * 检查密码合法性
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        return password.length() > 5 && password.length() < 20;
    }

    /**
     * 检查验证码合法性
     * @param code
     * @return
     */
    public static boolean isVeryCodedValid(String code) {
        return code.length() > 3 && code.length() <= 20;
    }

    /**
     * 检查名称合法性
     *
     * @param name
     * @return
     */
    public static boolean isNameValid(String name) {
        return name.length() > 0 && name.length() < 30;
    }

    public static int getDensityDpi(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_HIGH:
                return 0;
            case DisplayMetrics.DENSITY_XHIGH:
                return 1;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 2;
            default:
                return 0;
        }
    }


    /**
     * @param discount
     * @param type     保留类型 0 为不保留小数点, 1保留一位, 2保留两位, 默认保留一位
     * @return
     */
    public static String FomatDouble(double discount, int type) {
        DecimalFormat format;
        if (type == 0) {
            format = new DecimalFormat("#0");
        } else if (type == 1) {
            format = new DecimalFormat("#0.0");
        } else if (type == 2) {
            format = new DecimalFormat("#0.00");
        } else {
            format = new DecimalFormat("#0.0");
        }
        return format.format(discount);
    }

    public static void setColorFulText(String str, TextView textView, int colorRes, Context context) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SpannableString ss = new SpannableString(str);
        int start = str.indexOf(" ");
        int end = str.indexOf(" ", start + 1);
        if (start < 0 || start > str.length() || end < 0 || end > str.length()) {
            return;
        }
        ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorRes)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
    }



    //将pixel转换成dip(dp)
    public static int pixelToDip(Context context, float pixelValue) {
        float density = context.getResources().getDisplayMetrics().density;
        int dipValue = (int) (pixelValue / density + 0.5f);
        return dipValue;
    }

    //将dip(dp)转换成pixel
    public static int dipToPixel(Context context, float dipValue) {
        if(context == null){
            return 0;
        }
        float density = context.getResources().getDisplayMetrics().density;
        int pixelValue = (int) (dipValue * density + 0.5f);
        return pixelValue;
    }

    //将pixel转换成sp
    public static int pixelToSp(Context context, float pixelValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int sp = (int) (pixelValue / scaledDensity + 0.5f);
        return sp;
    }

    //将sp转换成pixel
    public static int spToPixel(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int pixelValue = (int) (spValue * scaledDensity + 0.5f);
        return pixelValue;
    }

    /**
     * 文件夹大小
     *
     * @param directory
     * @return
     */
    private static long folderSize(File directory) {
        if(directory == null){
            return 0;
        }
        long length = 0;
        if(directory.isDirectory()){
            for (File file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else
                    length += folderSize(file);
            }
        }else {
            return directory.getTotalSpace();
        }

        return length;
    }

    public static String getFormattedFoldSize(File directory) {
        long size = folderSize(directory);
        long mbSize = size / (1000 * 1000);
            return mbSize + " MB";
    }

    public static void removePrevDialog(Activity context, String tag) {
        if(context.isFinishing()){
            return;
        }
        FragmentTransaction ft = context.getFragmentManager().beginTransaction();
        Fragment prev = context.getFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }


    public static String getFormattedCouponDate(long time){
        long millisecond = time * 1000;
        Date d = new Date(millisecond);
        DateFormat df = new SimpleDateFormat("yyyy.M.d", Locale.CHINA);
        return df.format(d);
    }
}
