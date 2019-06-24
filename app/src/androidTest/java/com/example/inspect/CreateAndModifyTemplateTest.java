package com.example.inspect;


import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateAndModifyTemplateTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void grantPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CAMERA");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.WRITE_EXTERNAL_STORAGE");
        }
    }

    /**
     * create, save, load, then modify a templates formatting.
     */
    @Test
    public void createAndModifyTemplateTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnFileManager), withText("File Manager"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.templateLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnNewTemplate), withText("Create New Template"),
                        childAtPosition(
                                allOf(withId(R.id.ParentConstraintLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.accept_filename),
                        childAtPosition(
                                allOf(withId(R.id.filename_layout),
                                        childAtPosition(
                                                withId(R.id.ParentConstraintLayout),
                                                6)),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.add_heading_button), withText("Add Heading"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutPdf),
                                        2),
                                1)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.add_para_button), withText("Add Long Q"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutPdf),
                                        2),
                                3)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.save), withText("Save"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayoutPdf),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                4)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.label), withText("HEADING"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayoutBody),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("HEADING")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.label), withText("Question?"),
                        childAtPosition(
                                allOf(withId(R.id.paragraph_field),
                                        childAtPosition(
                                                withId(R.id.linearLayoutBody),
                                                1)),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("Question?")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.editText2), withText("Answer"),
                        childAtPosition(
                                allOf(withId(R.id.paragraph_field),
                                        childAtPosition(
                                                withId(R.id.linearLayoutBody),
                                                1)),
                                1),
                        isDisplayed()));
        editText3.check(matches(withText("Answer")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
