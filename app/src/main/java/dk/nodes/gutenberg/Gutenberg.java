package dk.nodes.gutenberg;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Gutenberg {

    public static final int REGULAR = Typeface.NORMAL;
    public static final int BOLD = Typeface.BOLD;
    public static final int ITALIC = Typeface.ITALIC;
    public static final int BOLD_ITALIC = Typeface.BOLD_ITALIC;
    public static final int MEDIUM = 4;
    public static final int LIGHT = 5;

    private static Gutenberg gutenberg;

    private HashMap<Integer, Typeface> fonts = new HashMap<>();

    private Gutenberg() {

    }

    public static Gutenberg getInstance() {
        if (gutenberg == null) {
            gutenberg = new Gutenberg();
        }
        return gutenberg;
    }

    public Gutenberg mapFont(int style, @NonNull Typeface typeface) {
        fonts.put(style, typeface);
        return this;
    }

    /* Uses typeface already set to TextView in the XML. You can specify which style to use
    *   by calling the method below and specifying the typeface to be applied to any contained views */

    public void changeFonts(@NonNull Object object) {
        changeFonts(-1, object);
    }

    /* Change the fonts of anything that may contain a view.
       This method will detect any subclasses of TextView and any nested TextViews within
       an activity or fragment. Views and ViewGroups are also handled by this method */

    public void changeFonts(@NonNull final int typeface, @NonNull final Object object) {
        if (object instanceof View) {
            changeViewFonts(typeface, (View) object);
        } else {
            Field[] fields = object.getClass().getDeclaredFields();
            View fieldTextView;
            for (Field f : fields) {
                if (View.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    try {
                        fieldTextView = (View) f.get(object);
                        changeViewFonts(typeface, fieldTextView);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Field could not be accessed");
                    }
                }
            }
        }
    }

    /* Change fonts for View and any subviews they contain. This will reach every nested child TextView */

    private void changeViewFonts(final int typeface, @NonNull View view) {
        if (fonts.size() == 0) {
            throw new RuntimeException("No fonts, make sure to add them with the method mapFont");
        }
        if (view instanceof TextView) {
            transform(typeface, (TextView) view);
        }
        if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
                View nextChild = ((ViewGroup) view).getChildAt(i);
                if (nextChild instanceof TextView) {
                    transform(typeface, (TextView) nextChild);
                } else if (nextChild instanceof ViewGroup) {
                    if (((ViewGroup) nextChild).getChildCount() > 0) {
                        changeFonts(nextChild);
                    }
                }
            }
        }
    }

    /* Change fonts of only one TextView programmatically */

    private void transform(final TextView view) {
        transform(-1, view);
    }

    private void transform(final int typeface, final TextView view) {
        if (typeface == -1 && fonts.containsValue(view.getTypeface())) {
            return; // font has already been applied
        }
        switch (typeface == -1 ? view.getTypeface().getStyle() : typeface) {
            case Gutenberg.REGULAR:
                if (fonts.get(Gutenberg.REGULAR) != null) {
                    view.setTypeface(fonts.get(Gutenberg.REGULAR));
                }
                break;
            case Gutenberg.MEDIUM:
                if (fonts.get(Gutenberg.MEDIUM) != null) {
                    view.setTypeface(fonts.get(Gutenberg.MEDIUM));
                }
                break;
            case Gutenberg.BOLD:
                if (fonts.get(Gutenberg.BOLD) != null) {
                    view.setTypeface(fonts.get(Gutenberg.BOLD));
                }
                break;
            case Gutenberg.ITALIC:
                if (fonts.get(Gutenberg.ITALIC) != null) {
                    view.setTypeface(fonts.get(Gutenberg.ITALIC));
                }
                break;
            case Gutenberg.BOLD_ITALIC:
                if (fonts.get(Gutenberg.BOLD_ITALIC) != null) {
                    view.setTypeface(fonts.get(Gutenberg.BOLD_ITALIC));
                }
                break;
            case Gutenberg.LIGHT:
                if (fonts.get(Gutenberg.LIGHT) != null) {
                    view.setTypeface(fonts.get(Gutenberg.LIGHT));
                }
                break;
            default:
                break;
        }
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
