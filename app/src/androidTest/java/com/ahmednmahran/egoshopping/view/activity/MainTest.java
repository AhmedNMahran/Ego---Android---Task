package com.ahmednmahran.egoshopping.view.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.ahmednmahran.egoshopping.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction supportVectorDrawablesButton = onView(
                allOf(withId(R.id.phone_button), withText("تسجيل الدخول عبر رقم الهاتف"),
                        childAtPosition(
                                allOf(withId(R.id.btn_holder),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                1)));
        supportVectorDrawablesButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.phone_number),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_layout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("1013100072"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.send_code), withText("تأكيد ملكية رقم الهاتف"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etDescription),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etDescription),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("تالباتتل "), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnUpdateAddress), withText("تحديث العنوان"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fabDate),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.homeContainer),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("موافق"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatRadioButton")), withText("م"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.RadioGroup")),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatRadioButton")), withText("م"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.RadioGroup")),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatRadioButton")), withText("م"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.RadioGroup")),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("موافق"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_checkout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.itemContainer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rvApps),
                                        0),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnCheckout), withText("تأكيد الطلب"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("موافق"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction cardNumberEditText = onView(
                allOf(withId(R.id.et_card_number),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText.perform(click());

        ViewInteraction cardNumberEditText2 = onView(
                allOf(withId(R.id.et_card_number),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText2.perform(replaceText("42242"), closeSoftKeyboard());

        ViewInteraction cardNumberEditText3 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText3.perform(replaceText("4224 22444"));

        ViewInteraction cardNumberEditText4 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 22444"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText4.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText5 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 4"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText5.perform(replaceText("4224 2244 42424"));

        ViewInteraction cardNumberEditText6 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 42424"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText6.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText7 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 4242 4"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText7.perform(replaceText("4224 2244 4242 4242"));

        ViewInteraction cardNumberEditText8 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 4242 4242"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText8.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText9 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 4242 4242"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText9.perform(longClick());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction cardNumberEditText10 = onView(
                allOf(withId(R.id.et_card_number), withText("4224 2244 4242 4242"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText10.perform(replaceText("42424"));

        ViewInteraction cardNumberEditText11 = onView(
                allOf(withId(R.id.et_card_number), withText("42424"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText11.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText12 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 4"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText12.perform(replaceText("4242 42424"));

        ViewInteraction cardNumberEditText13 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 42424"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText13.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText14 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 4242 4"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText14.perform(replaceText("4242 4242 42424"));

        ViewInteraction cardNumberEditText15 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 4242 42424"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText15.perform(closeSoftKeyboard());

        ViewInteraction cardNumberEditText16 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 4242 4242 4"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText16.perform(replaceText("4242 4242 4242 4242"));

        ViewInteraction cardNumberEditText17 = onView(
                allOf(withId(R.id.et_card_number), withText("4242 4242 4242 4242"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                2),
                        isDisplayed()));
        cardNumberEditText17.perform(closeSoftKeyboard());

        ViewInteraction expiryDateEditText = onView(
                allOf(withId(R.id.et_expiry_date),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                3),
                        isDisplayed()));
        expiryDateEditText.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction expiryDateEditText2 = onView(
                allOf(withId(R.id.et_expiry_date), withText("02/"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                3),
                        isDisplayed()));
        expiryDateEditText2.perform(replaceText("02/22"));

        ViewInteraction expiryDateEditText3 = onView(
                allOf(withId(R.id.et_expiry_date), withText("02/22"),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                3),
                        isDisplayed()));
        expiryDateEditText3.perform(closeSoftKeyboard());

        ViewInteraction stripeEditText = onView(
                allOf(withId(R.id.et_cvc_number),
                        childAtPosition(
                                allOf(withId(R.id.frame_container),
                                        childAtPosition(
                                                withId(R.id.card_input_widget),
                                                1)),
                                4),
                        isDisplayed()));
        stripeEditText.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btnSaveCard), withText("حفظ"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton7.perform(click());

        pressBack();

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_home),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_checkout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnCheckout), withText("تأكيد الطلب"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2)));
        appCompatButton8.perform(scrollTo(), click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
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
