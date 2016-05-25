package dk.nodes.gutenberg;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by joso on 27/11/15.
 */
public class FontManager {

    public static final int LIGHT = 4;

    private static FontManager instance;

    private SparseArray<Typeface> fonts = new SparseArray<>();

    private FontManager() {

    }

    public static FontManager getInstance() {
        if(instance == null) {
            instance = new FontManager();
        }

        return instance;
    }

    public FontManager mapFont(int style, @NonNull Typeface typeface) {
        fonts.put(style, typeface);
        return this;
    }

    public Typeface getFont(int style) {
        return fonts.get(style);
    }

    public void changeFonts(@NonNull Object view) {
        if(fonts.size() == 0) {
            throw new RuntimeException("No fonts, make sure to add them with the method mapFont");
        }

        Field[] fields = view.getClass().getDeclaredFields();

        for (Field f : fields) {
                if (f.getType() == Button.class || f.getType() == TextView.class || f.getType() == EditText.class) {
                        f.setAccessible(true);
                    TextView fieldTextView = null;
                    try {
                        fieldTextView = (TextView) f.get(view);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Field could not be accessed");
                    }

                    switch (fieldTextView.getTypeface().getStyle()) {
                            case Typeface.NORMAL:
                                if( fonts.get(Typeface.NORMAL) != null ) {
                                    fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                                }
                                break;

                            case Typeface.BOLD:
                                if( fonts.get(Typeface.BOLD) != null ) {
                                    fieldTextView.setTypeface(fonts.get(Typeface.BOLD));
                                }
                                break;

                            case Typeface.ITALIC:
                                if( fonts.get(Typeface.ITALIC) != null ) {
                                    fieldTextView.setTypeface(fonts.get(Typeface.ITALIC));
                                }
                                break;

                            case Typeface.BOLD_ITALIC:
                                if( fonts.get(Typeface.BOLD_ITALIC) != null ) {
                                    fieldTextView.setTypeface(fonts.get(Typeface.BOLD_ITALIC));
                                }
                                break;

                            case FontManager.LIGHT:
                                if( fonts.get(FontManager.LIGHT) != null ) {
                                    fieldTextView.setTypeface(fonts.get(FontManager.LIGHT));
                                }
                                break;

                            default:

                                break;

                        }
                }
        }
    }

    public void changeFonts(@NonNull View view) {
        if(fonts.size() == 0) {
            return;
        }

        for(int i=0; i<((ViewGroup)view).getChildCount(); ++i) {
            View nextChild = ((ViewGroup)view).getChildAt(i);

            if( nextChild instanceof Button || nextChild instanceof  TextView || nextChild instanceof EditText ) {
                TextView fieldTextView = (TextView) nextChild;

                if( fieldTextView.getTypeface() == null ) {
                    fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                    continue;
                }

                switch (fieldTextView.getTypeface().getStyle()) {
                    case Typeface.NORMAL:
                        if( fonts.get(Typeface.NORMAL) != null ) {
                            fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                        }
                        break;

                    case Typeface.BOLD:
                        if( fonts.get(Typeface.BOLD) != null ) {
                            fieldTextView.setTypeface(fonts.get(Typeface.BOLD));
                        }
                        break;

                    case Typeface.ITALIC:
                        if( fonts.get(Typeface.ITALIC) != null ) {
                            fieldTextView.setTypeface(fonts.get(Typeface.ITALIC));
                        }
                        break;

                    case Typeface.BOLD_ITALIC:
                        if( fonts.get(Typeface.BOLD_ITALIC) != null ) {
                            fieldTextView.setTypeface(fonts.get(Typeface.BOLD_ITALIC));
                        }
                        break;

                    case FontManager.LIGHT:
                        if( fonts.get(FontManager.LIGHT) != null ) {
                            fieldTextView.setTypeface(fonts.get(FontManager.LIGHT));
                        }
                        break;

                    default:

                        break;

                }
            }
        }
    }

}
