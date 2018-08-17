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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Displays a fragment that contains a ListView holding several Chat objects. Used in this lesson
 * to show a) how to reduce overdraws, and b) how to flatten needlessly nested hierarchies.
 */
public class ChatumLatinumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatum_latinum);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_chatum_latinum_container, new ChatsFragment())
                    .commit();
        }
    }
}