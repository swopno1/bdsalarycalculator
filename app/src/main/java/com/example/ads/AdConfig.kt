package com.example.ads

/**
 * Centralized AdMob Configuration for BD Salary Calculator.
 *
 * Current configuration uses official Google Mobile Ads test IDs.
 * To transition to production:
 * 1. Replace [BANNER_AD_UNIT_ID] with your verified AdMob Banner Ad Unit ID.
 * 2. In `app/src/main/AndroidManifest.xml`, replace the meta-data value
 *    `com.google.android.gms.ads.APPLICATION_ID` with your production AdMob App ID.
 * 3. Never click or encourage users to click live advertisements.
 */
object AdConfig {

    /**
     * Set to true to show advertisements, or false to disable all ads.
     */
    const val ADS_ENABLED = true

    /**
     * Official Google Sample AdMob App ID.
     * Manifest: ca-app-pub-3940256099942544~3347511713
     */
    const val TEST_ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713"

    /**
     * Official Google Sample Banner Ad Unit ID.
     */
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
}
