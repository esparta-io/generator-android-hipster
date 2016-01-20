package <%=appPackage%>.storage;

/**
 * Copyright 2016 Deividi Cavarzan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Storage extends GsonStorage {

    private static Storage instance;

    /**
     * Create a instance for Storage using your SharedPreferences object and custom Gson configuration
     * @param gson
     * @param preferences
     * @return default storage with simple Gson
     */
    public Storage(SharedPreferences preferences, Gson gson) {
        super(preferences, gson);
    }

    /**
     * Get a singleton object for GsonStorage using packageName + "_GsonStorage
     * @param context
     * @return default storage with simple Gson
     */
    public static final Storage getDefault(Context context) {
        if (instance == null) {
            synchronized (Storage.class) {
                if (instance == null) {
                    instance = createInstance(context, null);
                }
            }
        }
        return instance;
    }

    /**
     * Get a singleton object for GsonStorage with provided name
     * @param context
     * @return default storage with simple Gson
     */
    public static final Storage getDefault(Context context, String name) {
        if (instance == null) {
            synchronized (Storage.class) {
                if (instance == null) {
                    instance = createInstance(context, name);
                }
            }
        }
        return instance;
    }

    private static Storage createInstance(Context context, String sharedPreferencesName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName != null ? sharedPreferencesName : context.getPackageName() + "_GsonStorage", Context.MODE_PRIVATE);
        return new Storage(sharedPreferences, new Gson());
    }
}
