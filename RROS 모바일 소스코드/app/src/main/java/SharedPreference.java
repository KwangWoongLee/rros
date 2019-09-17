import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String APP_SHARED_PREFS = "thisApp.SharedPreference";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void setSharedTest(String test) {
        editor.putString("test", test);
        editor.commit();
    }

    public String getSharedTest() {
        return sharedPreferences.getString("test", "defValue");
    }
}

