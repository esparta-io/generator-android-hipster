package <%=appPackage%>.storage;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;

public final class Storage extends GsonStorage {

    public Storage(Gson gson, SharedPreferences preferences) {
        super(preferences, gson);
    }

}