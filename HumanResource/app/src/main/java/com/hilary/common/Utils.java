package com.hilary.common;

import android.content.Context;
import android.widget.Toast;

public class Utils{

    /**
     * @param context 上下文
     * @param id 显示的字符串资源ID
     */
    public static void Toast(Context context,int id){
        Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context 上下文
     * @param text 显示的文字
     */
    public static void Toast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
