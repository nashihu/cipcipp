package com.cipcipp.main.helper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.HashMap;

public class OpenApp {
//    private Context context;
//    private String packageName;
    private static String[] row_name = {
            "Ovo","Blibli","Flip","Dana","BL","Tokped","Paytren","Payfazz","Lazada","Shopee","Gojek","Akulaku",
            "GjmPulsa","Kudo","LinkAja","m-BL"};
    private static String[] row_package_name = {
            "ovo.id","blibli.mobile.commerce","id.flip","id.dana","com.bukalapak.android","com.tokopedia.tkpd"
            ,"id.co.paytren.user","com.payfazz.android","com.lazada.android","com.shopee.id","com.gojek.app",
    "io.silvrr.installment","com.gjmpulsa.webapp","kudo.mobile.app","com.telkom.mwallet","com.bukalapak.mitra"};

//    public OpenApp(Context context, String packageName) {
//        this.context = context;
//        this.packageName = packageName;
//    }



    public static void openApp(Context context, String packageName) {
        HashMap<String,String> packagename = new HashMap<>();
        for(int i =0; i<row_name.length;i++) {
            packagename.put(row_name[i],row_package_name[i]);
        }
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packagename.get(packageName));
            if (i == null) {
                String url = "https://play.google.com/store/apps/details?id="+packageName;
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
//                return false;
            }
            else {
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);
            }

//            return true;
        } catch (ActivityNotFoundException e) {
//            return false;
        }
    }
}
