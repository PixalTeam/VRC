package com.google.appinventor.components.runtime.util;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.appinventor.components.runtime.PhoneStatus;
import com.google.appinventor.components.runtime.ReplForm;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetValManager {
    private static final String LOG_TAG = "RetValManager";
    private static final long TENSECONDS = 10000;
    private static ArrayList<JSONObject> currentArray = new ArrayList<>(10);
    private static final Object semaphore = new Object();

    private RetValManager() {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void appendReturnValue(String blockid, String ok, String item) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, ok);
                retval.put("type", "return");
                retval.put("value", item);
                retval.put("blockid", blockid);
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void sendError(String error) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, "OK");
                retval.put("type", "error");
                retval.put("value", error);
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void pushScreen(String screenName, Object value) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, "OK");
                retval.put("type", "pushScreen");
                retval.put("screen", screenName);
                if (value != null) {
                    retval.put("value", value.toString());
                }
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void popScreen(String value) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, "OK");
                retval.put("type", "popScreen");
                if (value != null) {
                    retval.put("value", value.toString());
                }
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void assetTransferred(String name) {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, "OK");
                retval.put("type", "assetTransferred");
                if (name != null) {
                    retval.put("value", name.toString());
                }
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public static void extensionsLoaded() {
        synchronized (semaphore) {
            JSONObject retval = new JSONObject();
            try {
                retval.put(NotificationCompat.CATEGORY_STATUS, "OK");
                retval.put("type", "extensionsLoaded");
                boolean sendNotify = currentArray.isEmpty();
                currentArray.add(retval);
                if (PhoneStatus.getUseWebRTC()) {
                    webRTCsendCurrent();
                } else if (sendNotify) {
                    semaphore.notifyAll();
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error building retval", e);
            }
        }
    }

    public static String fetch(boolean block) {
        String str;
        long startTime = System.currentTimeMillis();
        synchronized (semaphore) {
            while (currentArray.isEmpty() && block && System.currentTimeMillis() - startTime <= 9900) {
                try {
                    semaphore.wait(TENSECONDS);
                } catch (InterruptedException e) {
                }
            }
            JSONArray arrayoutput = new JSONArray(currentArray);
            JSONObject output = new JSONObject();
            try {
                output.put(NotificationCompat.CATEGORY_STATUS, "OK");
                output.put("values", arrayoutput);
                currentArray.clear();
                str = output.toString();
            } catch (JSONException e2) {
                Log.e(LOG_TAG, "Error fetching retvals", e2);
                str = "{\"status\" : \"BAD\", \"message\" : \"Failure in RetValManager\"}";
            }
        }
        return str;
    }

    private static void webRTCsendCurrent() {
        try {
            JSONObject output = new JSONObject();
            output.put(NotificationCompat.CATEGORY_STATUS, "OK");
            output.put("values", new JSONArray(currentArray));
            ReplForm.returnRetvals(output.toString());
            currentArray.clear();
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error building retval", e);
        }
    }
}
