package banquemisr.challenge05.utils

import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import banquemisr.challenge05.R

object  AppUtils
{




    fun navigateToDestination(context: FragmentActivity, action: Int)
    {
        try
        {
            // Find the NavHostFragment
            val navHostFragment = context.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            // Get the NavController associated with the NavHostFragment
            val navController = navHostFragment.navController
            // Navigate to the desired destination fragment
            navController.navigate(action)
        }
        catch (e: Exception)
        {
            Log.e("TAG", "Error navigating to destination: ${e.message}")
        }
    }

}