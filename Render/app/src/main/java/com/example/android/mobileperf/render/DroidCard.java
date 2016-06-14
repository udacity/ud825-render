/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.mobileperf.render;

import android.graphics.Bitmap;

/**
 * Class for creating droid cards, which feature a droid's name and image.
 **/
class DroidCard {

    protected static final float SPACE_AROUND_IMAGE = 20f;

    protected static final float BITMAP_HEIGHT_HEADER_HEIGHT_RATIO = .25f;

    private Droid mDroid;
    private Bitmap mBitmap;

    private float mHeaderHeight;
    private float mBodyHeight;
    private float mTitleSize;

    DroidCard(Droid droid, Bitmap bitmap) {
        mDroid = droid;
        mBitmap = bitmap;

        mBodyHeight = mBitmap.getHeight() + SPACE_AROUND_IMAGE;
        mHeaderHeight = mBitmap.getHeight() * BITMAP_HEIGHT_HEADER_HEIGHT_RATIO;
        mTitleSize = mHeaderHeight / 2;
    }

    private String logDimensions() {
        return "mBodyHeight = " + mBodyHeight +
                ", mHeaderHeight = " + mHeaderHeight +
                ", mTitleSize = " + mTitleSize +
                ", getWidth() = " + String.valueOf(getWidth());
    }

    protected float getWidth() {
        return mBitmap.getWidth() + (2 * SPACE_AROUND_IMAGE);
    }

    protected float getBodyHeight() {
        return mBodyHeight;
    }

    protected float getHeaderHeight() {
        return mHeaderHeight;
    }

    protected float getHeight() {
        return getBodyHeight() + getHeaderHeight();
    }

    protected float getTitleSize() {
        return mTitleSize;
    }

    protected Bitmap getBitmap() {
        return mBitmap;
    }

    protected Droid getDroid() {
        return mDroid;
    }

    protected float getTitleXOffset() {
        return SPACE_AROUND_IMAGE;
    }

    protected float getTitleYOffset() {
        return SPACE_AROUND_IMAGE;
    }
}