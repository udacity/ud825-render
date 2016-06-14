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

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


/**
 * Fragment that encapsulates creating {@link Chat} objects and displaying them using a
 * {@link ListView} layout.
 * Creates seed data consisting of a handful of chats authored by a few authors.
 */
public class ChatsFragment extends Fragment {
    protected static int MILLISECONDS_PER_SECOND = 1000;
    protected static int SECONDS_PER_MINUTE = 60;

    public ChatsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a list of chats and populate it with hardcoded data.
        ArrayList<Chat> chats = new ArrayList<Chat>();
        populateChats(chats);

        // Create the adapter that provides values to the UI Widgets.
        ChatAdapter adapter = new ChatAdapter(getActivity(), chats);

        View rootView = inflater.inflate(R.layout.fragment_chats, container, false);

        // Find the ListView that holds all the chat messages and attach it to the adapter,
        ListView listView = (ListView) rootView.findViewById(R.id.listview_chats);
        listView.setAdapter(adapter);

        return rootView;
    }

    private Date getTimeInPast(int minutesAgo) {
        return new Date(new Date().getTime() -
                (minutesAgo * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND));
    }

    // Creates hardcoded chat objects.
    private void populateChats(ArrayList<Chat> chats) {
        Resources res = getResources();
        Droid alex = new Droid("alex", res.getColor(R.color.alex_color));
        Droid joanna = new Droid("joanna", res.getColor(R.color.joanna_color), R.drawable.joanna);
        Droid shailen = new Droid("shailen", res.getColor(R.color.shailen_color),
                R.drawable.shailen);

        chats.add(new Chat(alex, "Lorem ipsum dolor sit amet, orci nullam cra",
                getTimeInPast(15)));

        chats.add(new Chat(joanna, "Omnis aptent magnis suspendisse ipsum, semper egestas " +
                "magna auctor maecenas",
                getTimeInPast(11)));

        chats.add(new Chat(shailen, "eu nibh, rhoncus wisi posuere lacus, ad erat egestas " +
                "quam, magna ante ultricies sem",
                getTimeInPast(9)));

        chats.add(new Chat(alex, "rhoncus wisi posuere lacus, ad erat egestas quam, magna " +
                "ante ultricies sem lacus",
                getTimeInPast(8)));

        chats.add(new Chat(shailen, "Enim justo nisl sit proin, quis vestibulum vivamus " +
                "suscipit penatibus et id, tempus mauris a lacus blandit, aenean praesent " +
                "arcu scelerisque sociosqu. Nonummy at ut ullamcorper nulla, ligula id, " +
                "nullam donec nisl ante turpis duis mauris, dolor imperdiet a inceptos aliquam",
                getTimeInPast(8)));

        chats.add(new Chat(joanna, "Omnis aptent magnis.",
                getTimeInPast(7)));

        chats.add(new Chat(alex, "Metus tincidunt sit in urna.",
                getTimeInPast(6)));

        chats.add(new Chat(shailen, "Non blandit nulla dapibus, vitae quisque sed cras mi " +
                "leo condimentum sociosqu quis sed pharetra",
                getTimeInPast(4)));

        chats.add(new Chat(joanna, "Enim justo nisl sit proin, quis vestibulum vivamus " +
                "suscipit penatibus et id, tempus mauris a lacus blandit, aenean praesent " +
                "arcu scelerisque sociosqu. Nonummy at ut ullamcorper nulla, ligula id, " +
                "nullam donec nisl ante turpis duis mauris, dolor imperdiet a inceptos.",
                getTimeInPast(3)));
    }
}