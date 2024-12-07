package com.example.cholestifyapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    // Test Navigasi ke Login
//    @Test
//    fun testNavigateToLogin() {
//        ActivityScenario.launch(MainActivity::class.java)
//        onView(withId(R.id.nav_host_fragment_activity_main)).check(matches(isDisplayed()))
//        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
//    }

    // Test Register dan Navigasi ke Login setelah berhasil Register
    @Test
    fun testRegisterAndNavigateToLogin() {
        ActivityScenario.launch(MainActivity::class.java)

        // Navigasi ke halaman registrasi
        onView(withId(R.id.registerFragment)).perform(click()) // Misalnya, ada tombol ke registrasi
        onView(withId(R.id.buttonregist)).perform(click())

        // Isi data registrasi
        onView(withId(R.id.editTextTextRegist)).perform(typeText("newuser"), closeSoftKeyboard())
        onView(withId(R.id.EmailAddressRegist)).perform(typeText("newuser@example.com"), closeSoftKeyboard())
        onView(withId(R.id.TextPasswordRegist)).perform(typeText("newpassword123"), closeSoftKeyboard())
//        onView(withId(R.id.confirmPasswordRegister)).perform(typeText("newpassword123"), closeSoftKeyboard())
        onView(withId(R.id.sign_up_btn)).perform(click())

        // Verifikasi navigasi ke halaman login setelah registrasi
        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }

    // Test Login Berhasil setelah Registrasi
    @Test
    fun testSuccessfulLogin() {
        ActivityScenario.launch(MainActivity::class.java)

        // Navigasi ke halaman login
        onView(withId(R.id.loginFragment)).perform(click())

        // Isi form login
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("newuser@example.com"), closeSoftKeyboard())
        onView(withId(R.id.editTextTextPassword)).perform(typeText("newpassword123"), closeSoftKeyboard())

        // Klik tombol login
        onView(withId(R.id.buttonregist)).perform(click())

        // Verifikasi navigasi ke Home setelah login berhasil
        onView(withId(R.id.navigation_home)).check(matches(isDisplayed()))
    }

    // Test Navigasi ke Profil
    @Test
    fun testNavigateToProfile() {
        ActivityScenario.launch(MainActivity::class.java)

        // Navigasi ke Profil
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.navigation_profile)).perform(click())

        // Verifikasi Profil ditampilkan
//        onView(withId(R.id.text_profile)).check(matches(isDisplayed()))
    }

    // Test Navigasi ke Update Daily Food
    @Test
    fun testNavigateToUpdateDailyFood() {
        ActivityScenario.launch(MainActivity::class.java)

        // Navigasi ke Update Daily Food
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.update_daily_food)).perform(click())

        // Verifikasi Update Daily Food ditampilkan
        onView(withId(R.id.update_daily_food)).check(matches(isDisplayed()))
    }
}