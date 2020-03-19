package com.google.appinventor.components.runtime.util;

import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;

public class AssetFetcher {
    private static final String LOG_TAG = AssetFetcher.class.getSimpleName();
    private static final String REPL_ASSET_DIR = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppInventor/");
    private static ExecutorService background = Executors.newSingleThreadExecutor();
    private static volatile boolean inError = false;
    private static final Object semaphore = new Object();

    private AssetFetcher() {
    }

    public static void fetchAssets(final String cookieValue, final String projectId, final String uri, final String asset) {
        background.submit(new Runnable() {
            public void run() {
                if (AssetFetcher.getFile(uri + "/ode/download/file/" + projectId + "/" + asset, cookieValue, asset, 0) != null) {
                    RetValManager.assetTransferred(asset);
                }
            }
        });
    }

    public static void upgradeCompanion(String cookieValue, String inputUri) {
    }

    public static void loadExtensions(String jsonString) {
        Log.d(LOG_TAG, "loadExtensions called jsonString = " + jsonString);
        try {
            ReplForm form = (ReplForm) Form.getActiveForm();
            JSONArray array = new JSONArray(jsonString);
            List<String> extensionsToLoad = new ArrayList<>();
            if (array.length() == 0) {
                Log.d(LOG_TAG, "loadExtensions: No Extensions");
                RetValManager.extensionsLoaded();
                return;
            }
            int i = 0;
            while (i < array.length()) {
                String extensionName = array.optString(i);
                if (extensionName != null) {
                    Log.d(LOG_TAG, "loadExtensions, extensionName = " + extensionName);
                    extensionsToLoad.add(extensionName);
                    i++;
                } else {
                    Log.e(LOG_TAG, "extensionName was null");
                    return;
                }
            }
            try {
                form.loadComponents(extensionsToLoad);
                RetValManager.extensionsLoaded();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in form.loadComponents", e);
            }
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "JSON Exception parsing extension string", e2);
        }
    }

    /* access modifiers changed from: private */
    public static File getFile(String fileName, String cookieValue, String asset, int depth) {
        if (depth > 1) {
            synchronized (semaphore) {
                if (inError) {
                    return null;
                }
                inError = true;
                final String str = fileName;
                Form.getActiveForm().runOnUiThread(new Runnable() {
                    public void run() {
                        RuntimeErrorAlert.alert(Form.getActiveForm(), "Unable to load file: " + str, "Error!", "End Application");
                    }
                });
                return null;
            }
        }
        boolean error = false;
        File outFile = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileName).openConnection();
            if (connection != null) {
                connection.setRequestMethod("GET");
                connection.addRequestProperty("Cookie", "AppInventor = " + cookieValue);
                Log.d(LOG_TAG, "asset = " + asset + " responseCode = " + connection.getResponseCode());
                File outFile2 = new File(REPL_ASSET_DIR + asset);
                try {
                    File parentOutFile = outFile2.getParentFile();
                    if (!parentOutFile.exists()) {
                        parentOutFile.mkdirs();
                    }
                    BufferedInputStream in = new BufferedInputStream(connection.getInputStream(), 4096);
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile2), 4096);
                    while (true) {
                        try {
                            int b = in.read();
                            if (b == -1) {
                                break;
                            }
                            out.write(b);
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "copying assets", e);
                            error = true;
                        } finally {
                            out.close();
                        }
                    }
                    out.flush();
                    connection.disconnect();
                    outFile = outFile2;
                } catch (Exception e2) {
                    File file = outFile2;
                }
            } else {
                error = true;
            }
            if (error) {
                return getFile(fileName, cookieValue, asset, depth + 1);
            }
            return outFile;
        } catch (Exception e3) {
        }
        Log.e(LOG_TAG, "Exception while fetching " + fileName);
        return getFile(fileName, cookieValue, asset, depth + 1);
    }
}
