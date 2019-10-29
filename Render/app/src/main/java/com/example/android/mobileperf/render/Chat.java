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

import java.util.Date;

/**
 * Describes a Chat message written by a Droid author, consisting of some text and a timestamp.
 */
public class Chat {
    /**
     * The author of the chat.
     */
    private Droid author;

    /**
     * The chat content. This is hardcoded in this sample (see ChatsFragment.java).
     */
    private String text;

    /**
     * The time a chat was posted to the chat board.
     */
    private Date datetime;

    public Chat(Droid author, String text, Date datetime) {
        this.author = author;
        this.text = text;
        this.datetime = datetime;
    }

    public Droid getAuthor() {return author;}
    public String getText() {return text;}
    public Date getDatetime() {return datetime;}
}