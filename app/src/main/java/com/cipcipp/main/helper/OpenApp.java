package com.cipcipp.main.helper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import static com.cipcipp.main.utils.Util.packagename;

public class OpenApp {


    public static void openApp(Context context, String appName) {
        PackageManager manager = context.getPackageManager();
        try {
            String test = packagename.get(appName);
            if(test==null) test = "";
            Intent i = manager.getLaunchIntentForPackage(test);
            if (i == null) {
                String url = "https://play.google.com/store/apps/details?id="+packagename.get(appName);
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
            else {
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);
            }

        } catch (ActivityNotFoundException e) {
            //
        } catch (NullPointerException e) {
            //
        }
    }
}
