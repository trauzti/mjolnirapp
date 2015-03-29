package is.mjolnir.android.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OswaldRegularTextView extends TextView {

    public OswaldRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public OswaldRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OswaldRegularTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = FontCache.getOswaldRegular(context);
            setTypeface(tf);
        }
    }

}
