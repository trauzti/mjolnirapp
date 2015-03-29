package is.mjolnir.android.fonts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCache
{
    private static final Hashtable<String, Typeface> mFontCache = new Hashtable();

    public static Typeface getOpenSansBold(Context context)
    {
        //fonts/Open_Sans/OpenSans-Bold.ttf
        return getFont("fonts/Open_Sans/OpenSans-Bold.ttf", context);
    }

    public static Typeface getOpenSansBoldItalic(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-BoldItalic.ttf", context);
    }

    public static Typeface getOpenSansExtraBold(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-ExtraBold.ttf", context);
    }

    public static Typeface getOpenSansExtraBoldItalic(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-ExtraBoldItalic.ttf", context);
    }

    public static Typeface getOpenSansItalic(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-Italic.ttf", context);
    }

    public static Typeface getOpenSansLight(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-Light.ttf", context);
    }

    public static Typeface getOpenSansLightItalic(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-LightItalic.ttf", context);
    }

    public static Typeface getOpenSansRegular(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-Regular.ttf", context);
    }

    public static Typeface getOpenSansSemiBold(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-Semibold.ttf", context);
    }

    public static Typeface getOpenSansSemiBoldItalic(Context context)
    {
        return getFont("fonts/Open_Sans/OpenSans-SemiboldItalic.ttf", context);
    }

    public static Typeface getOswaldBold(Context context)
    {
        return getFont("fonts/Oswald/Oswald-Bold.ttf", context);
    }

    public static Typeface getOswaldLight(Context context)
    {
        return getFont("fonts/Oswald/Oswald-Light.ttf", context);
    }

    public static Typeface getOswaldRegular(Context context)
    {
        return getFont("fonts/Oswald/Oswald-Regular.ttf", context);
    }

    public static Typeface getFont(String paramString, Context context)
    {
        synchronized (mFontCache)
        {
            if (!mFontCache.containsKey(paramString))
            {
                Typeface localTypeface2 = Typeface.createFromAsset(context.getAssets(), paramString);
                mFontCache.put(paramString, localTypeface2);
            }
            Typeface localTypeface1 = (Typeface) mFontCache.get(paramString);
            return localTypeface1;
        }
    }
}
