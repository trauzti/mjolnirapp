package is.mjolnir.android.views;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by traustis on 4/9/15.
 */
public class BackgroundSetter {
    public static void setBackground(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
