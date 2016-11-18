package <%= appPackage %>;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import <%= appPackage %>.application.TestApp;

/**
 * Created by deividi on 23/03/16.
 */
public class MockTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestApp.class.getName(), context);
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
    }
}
