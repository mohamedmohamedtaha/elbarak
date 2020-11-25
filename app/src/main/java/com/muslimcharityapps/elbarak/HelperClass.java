package com.muslimcharityapps.elbarak;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HelperClass {
    public static void customToast(Activity activity, String ToastTitle) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        TextView text = layout.findViewById(R.id.text);
        text.setText(ToastTitle);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 150);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
