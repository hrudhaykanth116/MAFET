package com.hrudhaykanth116.training.ads
//
// import android.app.Activity
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.material3.Button
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.LaunchedEffect
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.platform.LocalContext
// import com.google.android.gms.ads.AdRequest
// import com.google.android.gms.ads.LoadAdError
// import com.google.android.gms.ads.interstitial.InterstitialAd
// import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
// import com.hrudhaykanth116.core.ui.components.CenteredColumn
//
// @Composable
// fun InterstitialAdScreen() {
//     val context = LocalContext.current
//     val activity = context as? Activity ?: return
//
//     var interstitialAd by remember { mutableStateOf<InterstitialAd?>(null) }
//
//     fun loadAd() {
//         InterstitialAd.load(
//             context,
//             MyAdUnitIds.INTERSTITIAL,
//             AdRequest.Builder().build(),
//             object : InterstitialAdLoadCallback() {
//                 override fun onAdLoaded(ad: InterstitialAd) {
//                     interstitialAd = ad
//                 }
//
//                 override fun onAdFailedToLoad(error: LoadAdError) {
//                     interstitialAd = null
//                 }
//             }
//         )
//     }
//
//
//     LaunchedEffect(Unit) {
//         loadAd()
//     }
//
//     CenteredColumn(modifier = Modifier.fillMaxSize()) {
//         Button(
//             onClick = {
//                 interstitialAd?.show(activity)
//                 interstitialAd = null
//                 loadAd()
//             }
//         ) {
//             Text("Show Interstitial")
//         }
//     }
// }