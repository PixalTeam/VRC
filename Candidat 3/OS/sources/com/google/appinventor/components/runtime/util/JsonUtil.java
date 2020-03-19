package com.google.appinventor.components.runtime.util;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import gnu.lists.FString;
import gnu.math.IntFraction;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
    private static final String BINFILE_DIR = "/AppInventorBinaries";
    private static final String LOG_TAG = "JsonUtil";

    private JsonUtil() {
    }

    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            returnList.add(jArray.getString(i));
        }
        return returnList;
    }

    @Deprecated
    public static List<Object> getListFromJsonArray(JSONArray jsonArray) throws JSONException {
        return getListFromJsonArray(jsonArray, false);
    }

    public static List<Object> getListFromJsonArray(JSONArray jsonArray, boolean useDicts) throws JSONException {
        List<Object> returnList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            returnList.add(convertJsonItem(jsonArray.get(i), useDicts));
        }
        return returnList;
    }

    public static List<Object> getListFromJsonObject(JSONObject jObject) throws JSONException {
        List<Object> returnList = new ArrayList<>();
        Iterator<String> keys = jObject.keys();
        List<String> keysList = new ArrayList<>();
        while (keys.hasNext()) {
            keysList.add(keys.next());
        }
        Collections.sort(keysList);
        for (String key : keysList) {
            List<Object> nestedList = new ArrayList<>();
            nestedList.add(key);
            nestedList.add(convertJsonItem(jObject.get(key), false));
            returnList.add(nestedList);
        }
        return returnList;
    }

    public static YailDictionary getDictionaryFromJsonObject(JSONObject jsonObject) throws JSONException {
        YailDictionary result = new YailDictionary();
        TreeSet<String> keys = new TreeSet<>();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            keys.add(it.next());
        }
        Iterator it2 = keys.iterator();
        while (it2.hasNext()) {
            String key = (String) it2.next();
            result.put(key, convertJsonItem(jsonObject.get(key), true));
        }
        return result;
    }

    @Deprecated
    public static Object convertJsonItem(Object o) throws JSONException {
        return convertJsonItem(o, false);
    }

    public static Object convertJsonItem(Object o, boolean useDicts) throws JSONException {
        if (o == null) {
            return "null";
        }
        if (o instanceof JSONObject) {
            if (useDicts) {
                return getDictionaryFromJsonObject((JSONObject) o);
            }
            return getListFromJsonObject((JSONObject) o);
        } else if (o instanceof JSONArray) {
            List<Object> array = getListFromJsonArray((JSONArray) o, useDicts);
            if (useDicts) {
                return YailList.makeList((List) array);
            }
            return array;
        } else if (o.equals(Boolean.FALSE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("false"))) {
            return Boolean.valueOf(false);
        } else {
            if (o.equals(Boolean.TRUE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("true"))) {
                return Boolean.valueOf(true);
            }
            if (o instanceof Number) {
                return o;
            }
            return o.toString();
        }
    }

    public static String getJsonRepresentation(Object value) throws JSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        if (value instanceof FString) {
            return JSONObject.quote(value.toString());
        }
        if (value instanceof YailList) {
            return ((YailList) value).toJSONString();
        }
        if (value instanceof IntFraction) {
            return JSONObject.numberToString(Double.valueOf(((IntFraction) value).doubleValue()));
        }
        if (value instanceof Number) {
            return JSONObject.numberToString((Number) value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof List) {
            value = ((List) value).toArray();
        }
        if (value instanceof YailDictionary) {
            StringBuilder sb = new StringBuilder();
            YailDictionary dict = (YailDictionary) value;
            String sep = "";
            sb.append('{');
            for (Entry<Object, Object> entry : dict.entrySet()) {
                sb.append(sep);
                sb.append(JSONObject.quote(entry.getKey().toString()));
                sb.append(':');
                sb.append(getJsonRepresentation(entry.getValue()));
                sep = ",";
            }
            sb.append('}');
            return sb.toString();
        } else if (!value.getClass().isArray()) {
            return JSONObject.quote(value.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[");
            String separator = "";
            for (Object o : (Object[]) value) {
                sb2.append(separator).append(getJsonRepresentation(o));
                separator = ",";
            }
            sb2.append("]");
            return sb2.toString();
        }
    }

    @Deprecated
    public static Object getObjectFromJson(String jsonString) throws JSONException {
        return getObjectFromJson(jsonString, false);
    }

    public static Object getObjectFromJson(String jsonString, boolean useDicts) throws JSONException {
        if (jsonString == null || jsonString.equals("")) {
            return "";
        }
        Object value = new JSONTokener(jsonString).nextValue();
        if (value == null || value.equals(JSONObject.NULL)) {
            return null;
        }
        if ((value instanceof String) || (value instanceof Number) || (value instanceof Boolean)) {
            return value;
        }
        if (value instanceof JSONArray) {
            return getListFromJsonArray((JSONArray) value, useDicts);
        }
        if (!(value instanceof JSONObject)) {
            throw new JSONException("Invalid JSON string.");
        } else if (useDicts) {
            return getDictionaryFromJsonObject((JSONObject) value);
        } else {
            return getListFromJsonObject((JSONObject) value);
        }
    }

    public static String getJsonRepresentationIfValueFileName(Object value) {
        List list;
        try {
            if (value instanceof String) {
                list = getStringListFromJsonArray(new JSONArray((String) value));
            } else if (value instanceof List) {
                list = (List) value;
            } else {
                throw new YailRuntimeError("getJsonRepresentationIfValueFileName called on unknown type", value.getClass().getName());
            }
            if (list.size() != 2) {
                return null;
            }
            if (!((String) list.get(0)).startsWith(".")) {
                return null;
            }
            String filename = writeFile((String) list.get(1), ((String) list.get(0)).substring(1));
            System.out.println("Filename Written: " + filename);
            return getJsonRepresentation(filename.replace("file:/", "file:///"));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
            return null;
        }
    }

    private static String writeFile(String input, String fileExtension) {
        FileOutputStream outStream = null;
        try {
            if (fileExtension.length() == 3 || fileExtension.length() == 4) {
                byte[] content = Base64.decode(input, 0);
                File destDirectory = new File(Environment.getExternalStorageDirectory() + BINFILE_DIR);
                destDirectory.mkdirs();
                File dest = File.createTempFile("BinFile", "." + fileExtension, destDirectory);
                FileOutputStream outStream2 = new FileOutputStream(dest);
                try {
                    outStream2.write(content);
                    String retval = dest.toURI().toASCIIString();
                    trimDirectory(20, destDirectory);
                    IOUtils.closeQuietly(LOG_TAG, outStream2);
                    return retval;
                } catch (Exception e) {
                    e = e;
                    outStream = outStream2;
                    try {
                        throw new YailRuntimeError(e.getMessage(), "Write");
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.closeQuietly(LOG_TAG, outStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outStream = outStream2;
                    IOUtils.closeQuietly(LOG_TAG, outStream);
                    throw th;
                }
            } else {
                throw new YailRuntimeError("File Extension must be three or four characters", "Write Error");
            }
        } catch (Exception e2) {
            e = e2;
            throw new YailRuntimeError(e.getMessage(), "Write");
        }
    }

    private static void trimDirectory(int maxSavedFiles, File directory) {
        File[] files = directory.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
            }
        });
        int excess = files.length - maxSavedFiles;
        for (int i = 0; i < excess; i++) {
            files[i].delete();
        }
    }

    public static String encodeJsonObject(Object jsonObject) throws IllegalArgumentException {
        try {
            return getJsonRepresentation(jsonObject);
        } catch (JSONException e) {
            throw new IllegalArgumentException("jsonObject is not a legal JSON object");
        }
    }
}
