package dk.nodes.gutenberg;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
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
public class Gutenberg {

    public static final int LIGHT = 4;

    private static Gutenberg instance;

    private SparseArray<Typeface> fonts = new SparseArray<>();

    private Gutenberg() {

    }

    public static Gutenberg getInstance() {
        if (instance == null) {
            instance = new Gutenberg();
        }
        return instance;
    }

    public Gutenberg mapFont(int style, @NonNull Typeface typeface) {
        fonts.put(style, typeface);
        return this;
    }

    public Typeface getFont(int style) {
        return fonts.get(style);
    }

    public void changeFonts(@NonNull Object view) {
        if (fonts.size() == 0) {
            throw new RuntimeException("No fonts, make sure to add them with the method mapFont");
        }

        Field[] fields = view.getClass().getDeclaredFields();

        for (Field f : fields) {
            if (f.getType() == Toolbar.class || f.getType() == android.widget.Toolbar.class ||
                    f.getType() == Button.class || f.getType() == AppCompatButton.class ||
                    f.getType() == TextView.class || f.getType() == AppCompatTextView.class ||
                    f.getType() == EditText.class || f.getType() == AppCompatEditText.class) {
                f.setAccessible(true);
                TextView fieldTextView = null;
                try {
                    fieldTextView = (TextView) f.get(view);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Field could not be accessed");
                }

                Typeface typeface = fieldTextView.getTypeface();

                if (typeface == null) {
                    if (fonts.get(Typeface.NORMAL) != null) {
                        fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                    }
                } else {

                    switch (typeface.getStyle()) {
                        case Typeface.NORMAL:
                            if (fonts.get(Typeface.NORMAL) != null) {
                                fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                            }
                            break;
                        case Typeface.BOLD:
                            if (fonts.get(Typeface.BOLD) != null) {
                                fieldTextView.setTypeface(fonts.get(Typeface.BOLD));
                            }
                            break;

                        case Typeface.ITALIC:
                            if (fonts.get(Typeface.ITALIC) != null) {
                                fieldTextView.setTypeface(fonts.get(Typeface.ITALIC));
                            }
                            break;

                        case Typeface.BOLD_ITALIC:
                            if (fonts.get(Typeface.BOLD_ITALIC) != null) {
                                fieldTextView.setTypeface(fonts.get(Typeface.BOLD_ITALIC));
                            }
                            break;

                        case Gutenberg.LIGHT:
                            if (fonts.get(Gutenberg.LIGHT) != null) {
                                fieldTextView.setTypeface(fonts.get(Gutenberg.LIGHT));
                            }
                            break;

                        default:

                            break;

                    }
                }
            }
        }
    }

    public void changeFonts(@NonNull View view) {
        if (fonts.size() == 0) {
            throw new RuntimeException("No fonts, make sure to add them with the method mapFont");
        }

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
            View nextChild = ((ViewGroup) view).getChildAt(i);

            if (nextChild instanceof Button || nextChild instanceof EditText || nextChild instanceof TextView) {
                TextView fieldTextView = (TextView) nextChild;

                if (fieldTextView.getTypeface() == null) {
                    fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                    continue;
                }

                switch (fieldTextView.getTypeface().getStyle()) {
                    case Typeface.NORMAL:
                        if (fonts.get(Typeface.NORMAL) != null) {
                            fieldTextView.setTypeface(fonts.get(Typeface.NORMAL));
                        }
                        break;

                    case Typeface.BOLD:
                        if (fonts.get(Typeface.BOLD) != null) {
                            fieldTextView.setTypeface(fonts.get(Typeface.BOLD));
                        }
                        break;

                    case Typeface.ITALIC:
                        if (fonts.get(Typeface.ITALIC) != null) {
                            fieldTextView.setTypeface(fonts.get(Typeface.ITALIC));
                        }
                        break;

                    case Typeface.BOLD_ITALIC:
                        if (fonts.get(Typeface.BOLD_ITALIC) != null) {
                            fieldTextView.setTypeface(fonts.get(Typeface.BOLD_ITALIC));
                        }
                        break;

                    case Gutenberg.LIGHT:
                        if (fonts.get(Gutenberg.LIGHT) != null) {
                            fieldTextView.setTypeface(fonts.get(Gutenberg.LIGHT));
                        }
                        break;

                    default:
                        break;

                }
            }

            if (nextChild instanceof ViewGroup) {
                if (((ViewGroup) nextChild).getChildCount() > 0) {
                    changeFonts(nextChild);
                }
            }
        }
    }
}
