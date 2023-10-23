package com.company.Renderer_6_3;

import java.util.Collections;
import java.util.List;

public class AbstractRenderer {
    class ImageData {

    }

    class ImageInfo {
        public ImageData downloadImage() {
           return new ImageData();
        }
    }

    protected void renderText(CharSequence source) {
        //do smth
    }

    protected void renderImage(ImageData data) {
        //do smth
    }

    protected List<ImageInfo> scanForImageInfo(CharSequence source) {
        return Collections.emptyList();
    }

    /**
     * If the Throwable is an Error, throw it; if it is a RuntimeException return it,
     * otherwise throw IllegalStateException
     **/
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
