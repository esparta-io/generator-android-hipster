package <%=appPackage%>.storage;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public abstract class Storage {

    protected final SharedPreferences.Editor editor;
    protected final SharedPreferences preferences;
    protected final Gson gson;

    protected Storage(SharedPreferences preferences, Gson gson) {
        this.preferences = preferences;
        this.editor = preferences.edit();
        this.gson = gson;
    }

    public <T> void set(String type, T payload, Class<T> clazz) {
        editor.putString(type, gson.toJson(payload, clazz));
        editor.commit();
    }

    public <T> void set(T payload, Class<T> clazz) {
        editor.putString(clazz.getSimpleName(), gson.toJson(payload, clazz));
        editor.commit();
    }

    public <T> void set(String type, List<T> payload, Class<T[]> clazz) {
        editor.putString(type, gson.toJson(payload.toArray(), clazz));
        editor.commit();
    }

    public <T> void set(String type, HashSet<T> payload, Class<T[]> clazz) {
        editor.putString(type, gson.toJson(payload.toArray(), clazz));
        editor.commit();
    }

    public <T> T get(String type, Class<T> clazz) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return null;
        }
        return clazz.cast(gson.fromJson(json, clazz));
    }

    public <T> T get(Class<T> krazz) {
        String json = preferences.getString(krazz.getSimpleName(), null);
        if (json == null) {
            return null;
        }
        return krazz.cast(gson.fromJson(json, krazz));
    }

    public <T> List<T> getList(String type, Class<T[]> clazz) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return null;
        }
        T[] objects = gson.fromJson(json, clazz);
        return Arrays.asList(objects);
    }

    public <T> T[] getPrimitiveList(String type, Class<T[]> clazz) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    public <T> List<T> getList(String type, Class<T[]> clazz, List<T> defaultValue) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return defaultValue;
        }
        T[] objects = gson.fromJson(json, clazz);
        return Arrays.asList(objects);

    }

    public <T> HashSet<T> getHashSet(String type, Class<T[]> clazz, HashSet<T> defaultValue) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return defaultValue;
        }
        T[] objects = gson.fromJson(json, clazz);

        return new HashSet<>(Arrays.asList(objects));

    }

    public <T> void updateSet(String type, T value, Class<T[]> krazz, boolean add) {
        HashSet<T> hashSet = getHashSet(type, krazz, new HashSet<>());
        if (add) {
            if (!hashSet.add(value)) { // equals can not be able to force the replace
                hashSet.remove(value);
                hashSet.add(value);
            }
        } else {
            hashSet.remove(value);
        }
        set(type, hashSet, krazz);
    }


    public <T> T get(String type, Class<T> clazz, T defaultValue) {
        String json = preferences.getString(type, null);
        if (json == null) {
            return defaultValue;
        }
        return clazz.cast(gson.fromJson(json, clazz));
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

    public void clear(String... values) {
        if (values == null || values.length == 0) {
            return;
        }
        for (String value : values) {
            editor.remove(value);
        }
        editor.commit();
    }

    public void setBoolean(String type, Boolean payload) {
        editor.putBoolean(type, payload);
        editor.commit();
    }

    public void setLong(String type, long payload) {
        editor.putLong(type, payload);
        editor.commit();
    }

    public void setInt(String type, int payload) {
        editor.putInt(type, payload);
        editor.commit();
    }

    public void setFloat(String type, float payload) {
        editor.putFloat(type, payload);
        editor.commit();
    }

    public void setString(String type, String payload) {
        editor.putString(type, payload);
        editor.commit();
    }

    public boolean getBoolean(String type) {
        return preferences.getBoolean(type, false);
    }

    public long getLong(String type) {
        return preferences.getLong(type, 0);
    }

    public int getInt(String type) {
        return preferences.getInt(type, 0);
    }

    public float getFloat(String type) {
        return preferences.getFloat(type, 0.0f);
    }

    public String getString(String type) {
        return preferences.getString(type, null);
    }

    public String getString(String type, String defaultValue) {
        String result = getString(type);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

}