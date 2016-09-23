# Gutenberg

Applies fonts to all TextViews within an Object, such as an Activity, Fragment or ViewGroup

Usage:

in Application Class:

    Gutenberg.getInstance()
      .mapFont(Gutenberg.REGULAR, Typeface.createFromAsset(getAssets(), "font_regular.otf"))
      .mapFont(Gutenberg.MEDIUM, Typeface.createFromAsset(getAssets(), "font_medium.otf"))
      .mapFont(Gutenberg.LIGHT, Typeface.createFromAsset(getAssets(), "font_light.otf"))
      .mapFont(Gutenberg.BOLD, Typeface.createFromAsset(getAssets(), "font_bold.otf"));

In Activity, Fragment or ViewGroup (or any class with views as fields/children)
  
      Gutenberg.getInstance().changeFonts(this); 

You may also specify a style to apply to a view:

      Gutenberg.getInstance().changeFonts(Gutenberg.LIGHT, inputField);
