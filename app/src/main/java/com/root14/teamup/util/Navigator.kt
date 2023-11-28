import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * A navigator class to help with navigation between activities.
 *
 * This class provides a simple and convenient way to navigate to other activities,
 * with or without passing extras.
 *
 * To use this class, simply create an instance of it with the context from which you want to launch the
 * activities. Then, call the `navigateTo()` method with the class of the activity you want to navigate to.
 *
 * If you need to pass extras to the activity, you can call the `navigateTo()` method with the class of
 * the activity and a bundle of extras.
 *
 * Example usage:
 *
 * ```
 * val navigator = Navigator<MainActivity>(this)
 *
 * // Navigate to the MainActivity without passing extras.
 * navigator.navigateTo(MainActivity::class.java)
 *
 * // Navigate to the MainActivity with extras.
 * val extras = Bundle()
 * extras.putString("name", "John Doe")
 * navigator.navigateTo(MainActivity::class.java, extras)
 * ```
 */
class Navigator<T>(private val context: Context) where T : Activity {
    /**
     * Navigates to the given activity class.
     *
     * @param activityClass The class of the activity to navigate to.
     */
    fun navigateTo(activityClass: Class<T>) {
        val intent = Intent(context, activityClass)
        context.startActivity(intent)
    }

    /**
     * Navigates to the given activity class with the given extras.
     *
     * @param activityClass The class of the activity to navigate to.
     * @param extras The bundle of extras to pass to the activity.
     */
    fun navigateTo(activityClass: Class<T>, extras: Bundle) {
        val intent = Intent(context, activityClass)
        intent.putExtras(extras)
        context.startActivity(intent)
    }
}
