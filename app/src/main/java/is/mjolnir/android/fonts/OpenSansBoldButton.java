package is.mjolnir.android.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class OpenSansBoldButton extends Button {

    public OpenSansBoldButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public OpenSansBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OpenSansBoldButton(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = FontCache.getOpenSansBold(context);
            setTypeface(tf);
        }
    }

}
