# Pre-work - Can Do

**Can Do** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Jayanth Devulapally**

Time spent: **20hrs** hours spent in total

## User Stories

The following **required** functionality is completed:

* [done] User can **successfully add and remove items** from the todo list
* [done] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [done] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [done] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [done] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [done] Add support for completion due dates for todo items (and display within listview item)
* [ - ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [done] Add support for selecting the priority of each todo item (and display in listview item)
* [done] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* Added the Tabs "To do","Done" & "Archived".
* User can mark the to do item as Done or Archived.
* Only the list of To do tab is editable.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/W4jDdVF.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I feel android XML layout editor is powerful but the drag and drop feature is poor comparatively to the storyboards in the Xcode. Android has nice usage of the xml file for layout, drawables, values, strings & colors, this helps to avoid typing the duplication of strings or colors. In iOS development the storyboards has nice layout options like assign the constrains respective to views.I feel adding constraints to the layouts in android is bit complex.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** I used the recycler view in my pre-work Project. Adapter are the data source (comparing iOS TableView and data source) for the recycler view or list view or grid view. They tell the view about home many items to display, layout of the each item at index. They can hold the behavior of the items displayed in the view. The 'getView'  method of the adpater returns the view at that particular index in the view.  

## Notes
Describe any challenges encountered while building the app.
1) Handling the persist storage with SQLite database was new to me.
2) Handling the fragments and updating the list was the most challenging thing I faced in developing this project.
## License

    Copyright 2017 Jayanth Devulapally

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
