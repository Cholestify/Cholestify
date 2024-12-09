package com.example.cholestifyapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test

class ExampleInstrumentedTest {

    // Test Register dan Navigasi ke Login setelah berhasil Register
    @Test
    fun testRegisterAndNavigateToLogin() {
        // Launch MainActivity with ActivityScenario
        ActivityScenario.launch(MainActivity::class.java)

        // Navigasi ke halaman registrasi
        onView(withId(R.id.registerFragment)).perform(click()) // Misalnya, ada tombol ke registrasi
        onView(withId(R.id.buttonregist)).perform(click())

        // Isi data registrasi
        onView(withId(R.id.editTextTextRegist)).perform(typeText("newuser"), closeSoftKeyboard())
        onView(withId(R.id.EmailAddressRegist)).perform(typeText("newuser@example.com"), closeSoftKeyboard())
        onView(withId(R.id.TextPasswordRegist)).perform(typeText("newpassword123"), closeSoftKeyboard())

        onView(withId(R.id.sign_up_btn)).perform(click())

        // Verifikasi navigasi ke halaman login setelah registrasi
        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }
}