package com.marioszou.android.bakethat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.marioszou.android.bakethat.UI.SelectRecipeActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class SelectRecipeScreenTest {

  private static final int BROWNIES_POSITION = 1;
  private static final String BROWNIES_INGREDIENTS_TEXT_PART = "Bittersweet chocolate (60-70% cacao) (350.0 G)";

  private IdlingResource mIdlingResource;

  /**
   * The ActivityTestRule is a rule provided by Android used for functional testing of a single
   * activity. The activity that will be tested will be launched before each test that's annotated
   * with @Test and before methods annotated with @Before. The activity will be terminated after the
   * test and methods annotated with @After are complete. This rule allows you to directly access
   * the activity during the test.
   */
  @Rule
  public ActivityTestRule<SelectRecipeActivity> mActivityTestRule = new ActivityTestRule<>(
      SelectRecipeActivity.class);

  @Before
  public void registerIdlingResource() {
    mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
    // To prove that the test fails, omit this call:
    IdlingRegistry.getInstance().register(mIdlingResource);
  }

  @Test
  public void recipeItemClick_opensRecipeSteps_showsIngredients() {

    onView(withId(R.id.recyclerview_recipes))
        .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES_POSITION, click()));

    onView(withId(R.id.tv_ingredients))
        .check(matches(withText(containsString(BROWNIES_INGREDIENTS_TEXT_PART))));

  }


  // Remember to unregister resources when not needed to avoid malfunction.
  @After
  public void unregisterIdlingResource() {
    if (mIdlingResource != null) {
      IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
  }
}
