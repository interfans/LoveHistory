package com.love.history.utils.router;

import android.app.Activity;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import net.wequick.small.Small;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * 通用路由跳转工具类
 * Created by wlx on 17/9/1.
 */

public class Router {

    public static Activity application;
    public static HashMap<String, String> mRouteUri;
    public static String TAG = "RouterDebug";

    public static void initRouter(Activity application) {
        Router.application = application;
        Router.mRouteUri = new HashMap<>();
        Log.d(TAG, "initRouter");
    }

    public static String getIlistenUri(Activity activity) {
        Uri uri = Small.getUri(activity);
        Log.i(TAG,"getIlistenUri"+uri.toString());
        String url = null;
        if (uri != null) {
            String from = uri.getQueryParameter("uri");
            String noSpace = from.replace(" ", "+");
            if (from != null) {
                byte[] bytes = null;
                bytes = Base64.decode(noSpace.getBytes(), Base64.DEFAULT);
                try {
                    url = new String(bytes, "utf-8");
                    Log.i(TAG,"getIlistenUri"+url.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    /**
     * @param module 插件注册名,详见 bundle.json
     * @param uri    插件中的路由落地页面路由，同ilisten路由表
     * @throws Exception
     */
    public static void registerRoute(String module, String uri) throws Exception {
        if (mRouteUri != null) {
            mRouteUri.put(uri, module);
            Log.d(TAG, "registerRoute,routeUri count is " + mRouteUri.size());
        } else {
            Log.d(TAG, "sorry, you need initRouter in application first. ");
            throw new Exception("sorry, you need initRouter in application first.");
        }
    }

    /**
     * @param module 插件注册名,详见 bundle.json
     * @param uri    插件中的路由落地页面路由，同ilisten路由表
     * @throws Exception
     */
    public static void registerRoute(String module, String... uri) throws Exception {
        if (mRouteUri != null) {
            for (String s : uri) {
                mRouteUri.put(s, module);
            }
            Log.d(TAG, "registerRoute,routeUri count is " + mRouteUri.size());
        } else {
            Log.d(TAG, "sorry, you need initRouter in application first. ");
            throw new Exception("sorry, you need initRouter in application first.");

        }
    }

    /**
     * @param uri 插件中的路由落地页面路由，同ilisten路由表
     * @throws Exception
     */
    public static void gotoActivity(String uri) throws Exception {
        Log.d(TAG, "gotoActivity, uri: " + uri);
        StringBuilder builder = new StringBuilder();
        Uri uris = Uri.parse(uri);
        String module = mRouteUri.get(uris.getPath());
        Log.d(TAG, "gotoActivity, module: " + module);
        if (module != null) {
            builder.append(module).append("/route?uri=").append(Base64.encodeToString(uri.getBytes(), Base64.DEFAULT));
            Log.d(TAG, "gotoActivity, url: " + builder.toString());
            Small.openUri(builder.toString(), Router.application);
        } else {
            // TODO: 2017/9/1
            //ToastUtils.show(MyNewAppliction.getmContext(), "不支持该功能，请升级您的软件版本");

        }
    }
}
