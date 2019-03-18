# BakeThat
An android app that shows recipes' steps and contains a widget. 

This app fetches a list of recipes from the [network](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json)
and displays them to the user. Then the user can select the desired recipe and be presented with step by step video instructions.
The app supports both phones and tablets (using master/ detail flow design), portrait and landscape orientations, using fragments. It also
contains a home screen widget presenting the selected recipe's ingredients. Finally the app contains UI tests using the Espresso Framework.

It was submitted to Udacity's [Android Developer Nanodegree](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801) and passed successfully.

## Screens
![Screen 1] ()

## Used Libraries
* [Retrofit 2](https://github.com/square/retrofit) and [Gson converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
* [OkHttp](https://github.com/square/okhttp)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [ExoPlayer](https://github.com/google/ExoPlayer)

## What Does This Project Contain?
In this project you will:
* Use MediaPlayer/Exoplayer to display videos.
* Handle error cases in Android.
* Add a widget to your app experience.
* Leverage a third-party library in your app.
* Use Fragments to create a responsive design that works on phones and tablets.

## Rubric

### General App Usage
- [x] App should display recipes from provided network resource.
- [x] App should allow navigation between individual recipes and recipe steps.
- [x] App uses RecyclerView and can handle recipe steps that include videos or images.
- [x] App conforms to common standards found in the Android Nanodegree General Project Guidelines.

### Components and Libraries
- [x] Application uses Master Detail Flow to display recipe steps and navigation between them.
- [x] Application uses Exoplayer to display videos.
- [x] Application properly initializes and releases video assets when appropriate.
- [x] Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- [x] Application makes use of Espresso to test aspects of the UI.
- [x] Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with Content Providers if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

### Homescreen Widget
- [x] Application has a companion homescreen widget.
- [x] Widget displays ingredient list for desired recipe.
