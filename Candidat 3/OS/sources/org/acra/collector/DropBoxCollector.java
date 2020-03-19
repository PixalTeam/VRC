package org.acra.collector;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import gnu.kawa.functions.GetNamedPart;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import org.acra.ACRA;

final class DropBoxCollector {
    private static final String NO_RESULT = "N/A";
    private static final String[] SYSTEM_TAGS = {"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};

    DropBoxCollector() {
    }

    public static String read(Context context, String[] additionalTags) {
        try {
            String serviceName = Compatibility.getDropBoxServiceName();
            if (serviceName == null) {
                return NO_RESULT;
            }
            Object dropbox = context.getSystemService(serviceName);
            Method getNextEntry = dropbox.getClass().getMethod("getNextEntry", new Class[]{String.class, Long.TYPE});
            if (getNextEntry == null) {
                return "";
            }
            Time timer = new Time();
            timer.setToNow();
            timer.minute -= ACRA.getConfig().dropboxCollectionMinutes();
            timer.normalize(false);
            long time = timer.toMillis(false);
            ArrayList<String> arrayList = new ArrayList<>();
            if (ACRA.getConfig().includeDropBoxSystemTags()) {
                arrayList.addAll(Arrays.asList(SYSTEM_TAGS));
            }
            if (additionalTags != null && additionalTags.length > 0) {
                arrayList.addAll(Arrays.asList(additionalTags));
            }
            if (arrayList.isEmpty()) {
                return "No tag configured for collection.";
            }
            StringBuilder dropboxContent = new StringBuilder();
            for (String tag : arrayList) {
                dropboxContent.append("Tag: ").append(tag).append(10);
                Object entry = getNextEntry.invoke(dropbox, new Object[]{tag, Long.valueOf(time)});
                if (entry == null) {
                    dropboxContent.append("Nothing.").append(10);
                } else {
                    Method getText = entry.getClass().getMethod("getText", new Class[]{Integer.TYPE});
                    Method getTimeMillis = entry.getClass().getMethod("getTimeMillis", null);
                    Method close = entry.getClass().getMethod("close", null);
                    while (entry != null) {
                        long msec = ((Long) getTimeMillis.invoke(entry, null)).longValue();
                        timer.set(msec);
                        dropboxContent.append(GetNamedPart.CAST_METHOD_NAME).append(timer.format2445()).append(10);
                        String text = (String) getText.invoke(entry, new Object[]{Integer.valueOf(500)});
                        if (text != null) {
                            dropboxContent.append("Text: ").append(text).append(10);
                        } else {
                            dropboxContent.append("Not Text!").append(10);
                        }
                        close.invoke(entry, null);
                        entry = getNextEntry.invoke(dropbox, new Object[]{tag, Long.valueOf(msec)});
                    }
                }
            }
            return dropboxContent.toString();
        } catch (SecurityException e) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        } catch (NoSuchMethodException e2) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        } catch (IllegalArgumentException e3) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        } catch (IllegalAccessException e4) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        } catch (InvocationTargetException e5) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        } catch (NoSuchFieldException e6) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
        }
        return NO_RESULT;
    }
}
