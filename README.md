# Async Task

## Overview
This is an example of Async Task, how to handle orientation change in Async Task, can we execute multiple Async Task serially, if not then how to achieve that. These all scenarios has been covered.

## Handling Orientation
There are few ways to handle the orientation change in Async Task. 
1. Disable the Orientation as soon as your onPreExecute() starts, and enable it again on onPostExecute().
2. Use the life cycle onSaveInstanceState() and onRestoreInstanceState()
3. Use the Async Task in the Fragment, in Fragnment one life cycle callback is setRetainInstance(true), this method will be called when the fragment is created. 
