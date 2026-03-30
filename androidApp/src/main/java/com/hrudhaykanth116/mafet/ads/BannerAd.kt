package com.hrudhaykanth116.mafet.ads

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAd(
    adUnitId: String,
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER,
    onLoaded: (() -> Unit)? = null,
    onFailed: ((LoadAdError) -> Unit)? = null,
) {

    val extras = Bundle()
    extras.putString("collapsible", "bottom")

    val collapsibleAdRequest =
        AdRequest.Builder()
            .addNetworkExtrasBundle(
                AdMobAdapter::class.java,
                extras
            ).build()

    val simpleAdRequest = AdRequest.Builder().build()

    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(adSize)
                this.adUnitId = adUnitId
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        onLoaded?.invoke()
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        onFailed?.invoke(error)
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                    }
                }


                loadAd(collapsibleAdRequest)
            }
        }
    )


}