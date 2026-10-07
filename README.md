# BD Salary Calculator (বিডি বেতন ক্যালকুলেটর)

**Developed & Published by:** ViveScript Solutions LLC  
**Website:** [https://www.vivescriptsolutions.com/](https://www.vivescriptsolutions.com/)  
**Application ID:** `com.vivescriptsolutions.bdsalarycalculator`  
**Target Market:** Global, with primary focus on Bangladesh, India, and Pakistan

---

## 1. Overview

**BD Salary Calculator** is a simple, lightweight, reliable, and privacy-conscious Android application created to give employees, job seekers, and office workers in Bangladesh an instant breakdown of their monthly salary.

### Target Users
* **Employees & Professionals:** Verify monthly pay slips, check allowance splits, and calculate actual take-home income.
* **Job Seekers:** Compare job offers and evaluate true in-hand salary before signing contracts.
* **HR, Payroll & Accounts Teams:** Conduct quick, on-the-spot salary estimations during negotiations and payroll reviews.

---

## 2. Key Features

* **⚡ One-Screen Architecture:** Open the app, enter your basic salary, and view your complete breakdown immediately without complex navigation.
* **🇧🇩 South Asian Number Formatting:** Correct grouping for Lakhs and Crores (e.g., `1,25,000` and `10,00,000`).
* **🌐 Bilingual (English & বাংলা):** Seamless one-tap toggle between English and authentic Bengali.
* **🔢 Bengali Digit Support:** Automatic formatting into Bengali numerals (`৳৫০,০০০`) with bidirectional ASCII/Bengali keyboard parsing.
* **💼 Comprehensive Components:**
  * **Basic Salary:** Main required entry point.
  * **Allowances:** Housing, Medical, Transport, Other, plus custom rows.
  * **Deductions:** Provident Fund (PF), Income Tax, Other, plus custom rows.
  * **Take-Home Salary:** Prominently featured net earnings with percentage-of-gross indicator.
  * **Annual Projections:** Automatic 12-month gross and take-home estimations.
* **🔒 100% Offline & Private:** No accounts, no logins, no cloud database. All calculations execute locally on-device.
* **📋 One-Tap Share:** Easily copy and share a formatted salary breakdown via WhatsApp, SMS, or email.
* **🎨 Material Design 3:** Polished Emerald theme with native light and dark mode support.

---

## 3. Technology Stack

* **Language:** Kotlin (100%)
* **UI Toolkit:** Jetpack Compose with Material 3 (M3)
* **Architecture:** Model-View-ViewModel (MVVM) with reactive `StateFlow`
* **Build System:** Gradle Kotlin DSL (`build.gradle.kts`) with Version Catalog (`gradle/libs.versions.toml`)
* **Monetization SDK:** Google Mobile Ads (AdMob) v23.6.0 (configured with official Google test IDs)
* **Testing:** JUnit 4, Robolectric, AndroidX Test

---

## 4. Requirements

* **Minimum SDK:** Android 7.0 (API Level 24)
* **Target SDK:** Android 16 (API Level 36)
* **Build Tooling:** Java 11 / Java 17 compatible JVM, Gradle 8.13+

---

## 5. Project Structure

```text
app/src/main/
├── java/com/example/
│   ├── MainActivity.kt               # Entry-point ComponentActivity
│   ├── ads/
│   │   ├── AdConfig.kt               # Centralized AdMob test/production configuration
│   │   └── BannerAdView.kt           # Non-intrusive bottom banner ad composable
│   ├── calculator/
│   │   └── SalaryCalculatorEngine.kt # Pure, deterministic salary calculation logic
│   ├── model/
│   │   └── SalaryModels.kt           # Immutable data classes for inputs and results
│   ├── ui/
│   │   ├── SalaryCalculatorScreen.kt # One-screen Compose UI and dialogs
│   │   ├── SalaryViewModel.kt        # StateFlow UI state management & persistence
│   │   ├── components/               # Input fields, hero result card, breakdown table
│   │   └── theme/                    # Color palette, M3 Theme, Typography
│   └── utils/
│       ├── AppLanguage.kt            # English & Bengali localization dictionary
│       └── CurrencyFormatter.kt      # South Asian grouping & Bengali numeral engine
└── res/
    ├── drawable/                     # Adaptive launcher vector & layer assets
    ├── mipmap-*/                     # Density raster icons (mdpi to xxxhdpi)
    └── values/ & values-bn/          # Localized strings
```

---

## 6. AdMob Configuration & Production Transition

The project is currently configured with **Google's official test AdMob IDs** to allow safe development and verification without policy violations:

* **Central Config:** `app/src/main/java/com/example/ads/AdConfig.kt`
* **Test App ID:** `ca-app-pub-3940256099942544~3347511713`
* **Test Banner Ad Unit ID:** `ca-app-pub-3940256099942544/6300978111`

### How to Switch to Production AdMob IDs:
1. In `app/src/main/AndroidManifest.xml`, update the meta-data value `com.google.android.gms.ads.APPLICATION_ID` with your verified production AdMob App ID.
2. In `app/src/main/java/com/example/ads/AdConfig.kt`, update `BANNER_AD_UNIT_ID` with your production Banner Ad Unit ID.
3. If you wish to disable ads entirely, set `AdConfig.ADS_ENABLED = false`.

---

## 7. Build & Testing

### Running Tests
Execute unit tests for calculation accuracy, boundary limits, and formatting:
```bash
gradle :app:testDebugUnitTest
```

### Compiling Debug APK
```bash
gradle :app:assembleDebug
```

### Compiling Release App Bundle (.aab)
```bash
gradle :app:bundleRelease
```

---

## 8. Repository Documentation & Legal

* [`LICENSE`](LICENSE): Full MIT License text for original source code and proprietary branding restriction notice.
* [`PRIVACY_POLICY.md`](PRIVACY_POLICY.md): Production Privacy Policy explaining on-device calculation privacy and AdMob data usage.
* [`TERMS_OF_SERVICE.md`](TERMS_OF_SERVICE.md): Terms of service including payroll estimation disclaimers.
* [`PLAY_STORE_METADATA.md`](PLAY_STORE_METADATA.md): Complete Google Play Store titles, short/full descriptions, and ASO strategy.
* [`PLAY_STORE_DATA_SAFETY.md`](PLAY_STORE_DATA_SAFETY.md): Form responses for Google Play Console Data Safety questionnaire.
* [`DESIGN_ASSETS.md`](DESIGN_ASSETS.md): Asset dimensions and visual specifications for Icon, Feature Graphic, and Screenshots.
* [`CHANGELOG.md`](CHANGELOG.md): Version history.

---

## 9. License & Copyright

**© 2026 ViveScript Solutions LLC.** All rights reserved.

The original source code of this application is released under the **MIT License**.  
The app name, logos, icons, screenshots, trademarks, and associated branding are the proprietary property of **ViveScript Solutions LLC** and are NOT licensed under the MIT License.
