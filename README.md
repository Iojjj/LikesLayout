# Likes Layout

LikesLayout is an implementation of layout that can draw likes similar to [Periscope app](https://play.google.com/store/apps/details?id=tv.periscope.android).

![Likes Layout Demo](/images/demo.gif)


## Setup and usage

To include this library to your project add dependency in **build.gradle** file:

```groovy
dependencies {
    compile '$coming_soon$'
}
```

There are three implementations of `LikesLayout` interface: **LikesFrameLayout**, **LikesLinearLayout**, **LikesRelativeLayout**. You can add them to your view via XML or in Java code.

```XML
<ua.vlasov.likeslayout.LikesLinearLayout
        android:layout_width="match_parent"
        android:layout_height="250dp"
        app:likes_drawable="drawable resource id"
        app:likes_drawableWidth="custom drawable width"
        app:likes_drawableHeight="custom drawable height"
        app:likes_produceInterval="produce interval in milliseconds"
        app:likes_animationDuration="animationduration in milliseconds"
        app:likes_tintMode="off|on_successively|on_random"
        app:likes_tintColors="array resource id"
        app:likes_drawableAnimator="cannonical class name"
        app:likes_drawablePositionAnimator="cannonical class name"
        >
        
        <ImageButton
            ...
            app:likes_drawable="drawable resource id"
            app:likes_drawableWidth="custom drawable width"
            app:likes_drawableHeight="custom drawable height"
            app:likes_mode="enabled|disabled"
            app:likes_produceInterval="produce interval in milliseconds"
            app:likes_animationDuration="animationduration in milliseconds"
            app:likes_tintMode="off|on_successively|on_random"
            app:likes_tintColors="array resource id"
            app:likes_drawableAnimator="cannonical class name"
            app:likes_drawablePositionAnimator="cannonical class name"
            />
            
</ua.vlasov.likeslayout.LikesLinearLayout>   
```

or

```JAVA
// create new LikesLayout
LikesLinearLayout likesLinearLayout = new LikesLinearLayout(getContext());
likesLinearLayout.setId(R.id.likes_layout);
// set layout height
final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        (int) (getResources().getDisplayMetrics().density * 250));
likesLinearLayout.setLayoutParams(params);
likesLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
// make sure your buttons located at the bottom of LikesLayout
likesLinearLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
likesLinearLayout.setPadding(0, 0, 0, (int) (getResources().getDisplayMetrics().density * 16));
// get attributes object that will store default values
likesLinearLayout.getAttributes().setAnimationDuration(1200);
likesLinearLayout.getAttributes().setTintMode(LikesAttributes.TINT_MODE_ON_SUCCESSIVELY);
likesLinearLayout.getAttributes().setTintColors(colors);

// it's time to add a button
ImageButton button = new ImageButton(getContext(), null, R.style.LikeButton_Grade);
button.setId(R.id.btn_grade);
button.setImageResource(R.drawable.ic_grade);
DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
// you can create layout params for your button using LikesLayout.newLayoutParamsBuilder() method
// then using a builder you can set all necessary attributes values
final ViewGroup.LayoutParams params = likesLinearLayout
        .newLayoutParamsBuilder(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grade_normal))
        .setAnimationDuration(3000)
        .setProduceInterval(500)
        .setPositionAnimatorFactory(new CustomPositionAnimatorFactory2())
        // don't forget to enable likes mode :)
        .setLikesMode(LikesAttributes.LIKES_MODE_ENABLED)
        .build();
// set parameters and add button to LikesLayout
button.setLayoutParams(params);
likesLinearLayout.addView(button);
```

There is the list of available attributes:

| Attribute name | ATLL | CBOBC | Description | Default value |
| --- | --- | --- | --- | --- |
| likes_animationDuration | yes | yes | Animation duration in milliseconds. | 1200 |
| likes_drawable | yes | yes | Drawable that will be drawn when user presses view. | null |
| likes_drawableAnimator | yes | yes | Drawable animator factory. | @string/likes_drawable_animator_factory |
| likes_drawableHeight | yes | yes | Custom drawable height. | 0 |
| likes_drawablePositionAnimator | yes | yes | Drawable's position animator factory. | @string/likes_position_animator_factory |
| likes_drawableWidth | yes | yes | Custom drawable width. | 0 |
| likes_mode | no | yes | Switch that allows to enable/disable drawing likes on touching child view. If `likes_drawable` is null, `likes_mode` will be considered as `disabled`. | disabled |
| likes_produceInterval | yes | yes | Rate at which new likes drawables are produced in milliseconds. | 300 |
| likes_tintMode | yes | yes | Switch that allows to enable/disable tinting drawables. | not_set |
| likes_tintColors | yes | yes | Array of colors used for tinting drawables. If array is empty, `likes_tintMode` will be considered as `off`.| null | 

#### Produce likes programmatically
For this case you can use `LikesLayout.produceLikes(...)` methods, for example:

```JAVA
mLikesLayout.produceLikes(mBtnFavorite, 3, TimeUnit.SECONDS);
```

Make sure that child passed as an argument (`mBtnFavorite`) was added to this `mLikesLayout` (via XML or Java code). Otherwise you will get `IllegalArgumentException`.

## Restrictions 
* If you will not set `likes_mode` or set it to `disabled`, this view will not draw any likes on touch event.
* All attributes applicable to LikesLayout will be used as default values for child with `likes_mode` set to `enabled`.
* Custom drawable width and height will set exact size ignoring aspect ratio.
* Likes floating from bottom to top.

<br />
## Changelog

| Version | Changes                         |
| --- | --- |
| v.1.0.0 | First public release            |

<br />
## Support

You can support this library by creating a pull request with bug fixes and/or new features on `develop` branch. Any pull requests on `master` branch will be removed. 

<br />
## License
* * *
    The MIT License (MIT)
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.