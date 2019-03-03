package com.marioszou.android.bakethat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.marioszou.android.bakethat.UI.RecipeStepsActivity;
import com.marioszou.android.bakethat.UI.SelectRecipeActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsIntentTest {

  private static final int BROWNIES_POSITION = 1;

  private IdlingResource mIdlingResource;

  @Rule
  public IntentsTestRule<SelectRecipeActivity> mActivityTestRule = new IntentsTestRule<>(
      SelectRecipeActivity.class);

  @Before
  public void registerIdlingResource() {
    mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
    // To prove that the test fails, omit this call:
    IdlingRegistry.getInstance().register(mIdlingResource);
  }

  @Test
  public void recipeItemClick_SendsRecipeIntentExtra() {
    onView(withId(R.id.recyclerview_recipes))
        .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES_POSITION, click()));

    intended(allOf(
        hasExtraWithKey(RecipeStepsActivity.EXTRAS_RECIPE_ITEM),
        IntentMatchers.hasComponent(RecipeStepsActivity.class.getName())));

  }

  // Remember to unregister resources when not needed to avoid malfunction.
  @After
  public void unregisterIdlingResource() {
    if (mIdlingResource != null) {
      IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
  }
}
