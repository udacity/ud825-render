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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A custom view that displays a stacked collection of droid cards. See onDraw() for use of a
 * clipRect() to restrict the drawing area to avoid partial overdraws.
 */
class DroidCardsView extends View {
    /**
     * The list of droids displayed in this view.
     */
    private Droid[] mDroids;

    /**
     * The width of the droid image. In this sample, we hard code an image width in
     * DroidCardsActivity and pass it as an argument to this view.
     */
    float mDroidImageWidth;

    /**
     * The distance between the left edges of two adjacent cards. The cards overlap horizontally.
     */
    protected float mCardSpacing;

    /**
     * Keeps track of the left coordinate for each card.
     */
    private float mCardLeft;

    /**
     * A list of DroidCards objected. We use Asynctasks to populate the contents of this list. See
     * the DroidCardWorkerTask class that extends AsyncTask.
     */
    private ArrayList<DroidCard> mDroidCards = new ArrayList<DroidCard>();

    /**
     *
     * @param context           The app context.
     * @param droids            The Droid objects associated with DroidCards.
     * @param droidImageWidth   The width of each Droid image. Hardcoded in DroidCardsActivity.
     * @param cardSpacing       The distance between the left edges of two adjacent cards.
     */
    public DroidCardsView(Context context, Droid[] droids, float droidImageWidth,
                          float cardSpacing) {
        super(context);

        mDroids = droids;
        mDroidImageWidth = droidImageWidth;
        mCardSpacing = cardSpacing;

        // Fire AsyncTasks to fetch and scale the bitmaps.
        for (int i = 0; i < mDroids.length; i++) {
            new DroidCardWorkerTask().execute(mDroids[i]);
        }
    }

    /**
     * Custom implementation to do drawing in this view. Waits for the AsyncTasks to fetch
     * bitmaps for each Droid and populate mDroidCards, a list of DroidCard objects. Then, draws
     * overlapping droid cards.
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Don't draw anything until all the Asynctasks are done and all the DroidCards are ready.
        if (mDroids.length > 0 && mDroidCards.size() == mDroids.length) {
            // Loop over all the droids, except the last one.
            for (int i = 0; i < mDroidCards.size(); i++) {
                // Each card is laid out a little to the right of the previous one.
                mCardLeft = i * mCardSpacing;
                drawDroidCard(canvas, mDroidCards.get(i), mCardLeft, 0);
            }
        }

        // Invalidate the whole view. Doing this calls onDraw() if the view is visible.
        invalidate();
    }

    /**
     * Draws a droid card to a canvas at the specified position.
     */
    protected void drawDroidCard(Canvas canvas, DroidCard droidCard, float left, float top) {
        Paint paint = new Paint();
        Bitmap bm = droidCard.getBitmap();
        Droid droid = droidCard.getDroid();

        // Draw outer rectangle.
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        Rect cardRect = new Rect(
                (int)left,
                (int)top,
                (int)left + (int) droidCard.getWidth(),
                (int)top + (int) droidCard.getHeight()
        );
        canvas.drawRect(cardRect, paint);

        // Draw border.
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(cardRect, paint);

        // Draw the bitmap centered in the card body.
        canvas.drawBitmap(
                bm,
                (cardRect.left + (droidCard.getWidth() / 2)) - (bm.getWidth() / 2),
                (cardRect.top + (int) droidCard.getHeaderHeight() + (droidCard.getBodyHeight() / 2)
                        - (bm.getHeight() / 2)),
                null
        );

        // Write the droid's name in the header.
        paint.setTextSize(droidCard.getTitleSize());
        paint.setColor(getResources().getColor(droid.getColor()));
        paint.setStyle(Paint.Style.STROKE);

        // Calculate the the left and top offsets for the title.
        int titleLeftOffset = cardRect.left + (int) droidCard.getTitleXOffset();
        int titleTopOffset = cardRect.top + (int) droidCard.getTitleYOffset() +
                (int) droidCard.getTitleSize();

        // Draw the title text.
        canvas.drawText(droid.getName(), titleLeftOffset, titleTopOffset, paint);
    }

    /**
     * Creates and returns a bitmap from a drawable resource.
     */
    public Bitmap makeBitmap(Resources res, int resId, int reqWidth) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize.
        options.inSampleSize = calculateInSampleSize(options, reqWidth);

        // Decode bitmap with inSampleSize set.
        options.inJustDecodeBounds = false;

        // Decode bitmap.
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Returns a bitmap scaled to a specific width.
     */
    private Bitmap getScaledBitmap(Bitmap bitmap, float width) {
        float scale = width / bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale),
                (int) (bitmap.getHeight() * scale), false);
    }

    /**
     * Requests the decoder to subsample the original image, possibly returning a smaller image to
     * save memory.
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        // Get the raw width of image.
        final int width = options.outWidth;
        int inSampleSize = 1;

        // Calculate the best inSampleSize.
        if (width > reqWidth) {
            final int halfWidth = width / 2;
            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * Worker that fetches bitmaps in the background and stores them in a list of DroidCards.
     * The order of the
     */
    class DroidCardWorkerTask extends AsyncTask<Droid, Void, Bitmap> {
        Droid droid;
        private final WeakReference<ArrayList<DroidCard>> mDroidCardsReference;

        public DroidCardWorkerTask() {
            mDroidCardsReference = new WeakReference<ArrayList<DroidCard>>(mDroidCards);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Droid... params) {
            droid = params[0];
            // Scale the bitmap.
            return getScaledBitmap(
                    makeBitmap(getResources(), droid.getAvatarId(), (int) mDroidImageWidth),
                    mDroidImageWidth
            );
        }

        /**
         * Creates a DroidCard using the retrieved bitmap and stores it in a DroidCards list.
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // Check that the list and bitmap are not null.
            if (mDroidCardsReference != null && bitmap != null) {
                // Create a new DroidCard.
                mDroidCards.add(new DroidCard(droid, bitmap));
            }
        }
    }
}