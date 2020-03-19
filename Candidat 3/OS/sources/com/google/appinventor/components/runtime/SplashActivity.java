package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import com.google.appinventor.components.runtime.util.SdkLevel;

public final class SplashActivity extends AppInventorCompatActivity {
    Handler handler;
    WebView webview;

    public class JavaInterface {
        Context mContext;

        public JavaInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public boolean hasPermission(String permission) {
            if (SdkLevel.getLevel() >= 23 && ContextCompat.checkSelfPermission(this.mContext, permission) != 0) {
                return false;
            }
            return true;
        }

        @JavascriptInterface
        public void askPermission(String permission) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{permission}, 1);
        }

        @JavascriptInterface
        public String getVersion() {
            try {
                return this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                return "Unknown";
            }
        }

        @JavascriptInterface
        public void finished() {
            SplashActivity.this.handler.post(new Runnable() {
                public void run() {
                    SplashActivity.this.webview.destroy();
                    SplashActivity.this.finish();
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaInterface android2 = new JavaInterface(this);
        this.handler = new Handler();
        this.webview = new WebView(this);
        WebSettings webSettings = this.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabasePath(getApplicationContext().getDir("database", 0).getPath());
        this.webview.setWebChromeClient(new WebChromeClient() {
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5242880);
            }
        });
        setContentView(this.webview);
        this.webview.addJavascriptInterface(android2, "Android");
        this.webview.loadUrl("file:///android_asset/splash.html");
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            boolean granted = false;
            if (grantResults[i] == 0) {
                granted = true;
            }
            this.webview.loadUrl("javascript:permresult('" + permission + "'," + granted + ")");
        }
    }
}
