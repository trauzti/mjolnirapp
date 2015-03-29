package is.mjolnir.android.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class OswaldBoldTextView extends TextView {

    public OswaldBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public OswaldBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OswaldBoldTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = FontCache.getOswaldBold(context);
            setTypeface(tf);
        }
    }

}
