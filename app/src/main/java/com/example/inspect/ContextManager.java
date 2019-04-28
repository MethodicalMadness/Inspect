/*package com.example.inspect;

import android.content.Context;
import androidx.annotation.NonNull;

public class ContextManager {

    private static volatile ContextManager instance;
    private final Context mContext;

    private ContextManager(@NonNull Context context) {
        mContext = context.getApplicationContext();
        // ...
    }

    public static ContextManager getInstance(@NonNull Context context) {
        synchronized(ContextManager.class) {
            if (instance == null) {
                instance = new ContextManager(context);
            }
            return instance;
        }
    }

    public void doSomething() {
        // here I need to do something which requires the context
        mContext.getPackageName();
    }
}
*/