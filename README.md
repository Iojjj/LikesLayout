# Likes Layout
![Likes Layout Demo](/images/demo.gif)


## Setup and usage

To include this library to your project add dependency in **build.gradle** file:

```groovy
dependencies {
    compile '$coming_soon$'
}
```

There are three implementations of LikesLayout: **LikesFrameLayout**, **LikesLinearLayout**, **LikesRelativeLayout**.

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

###### Restrictions: 
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