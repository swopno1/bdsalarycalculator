# Design Assets & Graphic Specifications: BD Salary Calculator

**Publisher:** ViveScript Solutions LLC  
**Application ID:** `com.vivescriptsolutions.bdsalarycalculator`  
**Brand Identity:** Emerald Financial Utility (Bangladesh Taka focus)

---

## 1. Brand Color System

| Token | Light Value | Dark Value | Purpose |
| :--- | :--- | :--- | :--- |
| **Primary (Bangladesh Emerald)** | `#065F46` | `#34D399` | Main brand color, Take-home salary badge, Key buttons |
| **Primary Container** | `#D1FAE5` | `#065F46` | Hero card surface, emphasis backgrounds |
| **Secondary (Slate Navy)** | `#1E3A8A` | `#93C5FD` | Language chip, informational accents |
| **Tertiary (Warm Amber)** | `#B45309` | `#FCD34D` | Allowances indicators, summary accent |
| **Error / Deduction Red** | `#DC2626` | `#F87171` | Deductions warning, remove item buttons |
| **Background Neutral** | `#F8FAFC` | `#090E13` | Clean scaffold background |
| **Surface Card** | `#FFFFFF` | `#111921` | Form section cards, breakdown table |

---

## 2. Generated & Ready-to-Upload Store Assets

All production store graphic assets are generated and located in `play_store_assets/`:

| File Path | Dimensions | Format | Usage |
| :--- | :--- | :--- | :--- |
| `play_store_assets/app_icon_512x512.png` | 512 x 512 px | PNG32 (32-bit) | Google Play Store High-Res App Icon |
| `play_store_assets/feature_graphic_1024x500.png` | 1024 x 500 px | PNG32 (32-bit) | Google Play Store Feature Graphic Banner |
| `play_store_assets/screenshot_phone_1080x1920.png` | 1080 x 1920 px | PNG32 (32-bit) | Phone Store Listing Screenshot |
| `play_store_assets/screenshot_tablet_2048x1536.png` | 2048 x 1536 px | PNG32 (32-bit) | Tablet & Foldable 7"/10" Store Screenshot |

---

## 3. App Launcher Icon

### Specifications
* **Format:** Android Adaptive Icon (API 26+) with non-adaptive raster fallback (PNG32).
* **Dimensions:** 512 x 512 px (Google Play Store high-res icon), density mipmaps:
  * `mdpi`: 48 x 48 px
  * `hdpi`: 72 x 72 px
  * `xhdpi`: 96 x 96 px
  * `xxhdpi`: 144 x 144 px
  * `xxxhdpi`: 192 x 192 px
* **Adaptive Layers:**
  * **Background:** Solid Bangladesh Emerald `#0F5132` (`ic_launcher_background.xml`).
  * **Foreground:** Centered 66dp safe zone vector/raster displaying the Bangladesh Taka currency symbol (৳) integrated with a modern minimalist financial payroll document (`ic_launcher_foreground.xml`).
  * **Mask:** Circle (`ic_launcher_round.png`) and squircle adaptive support.

---

## 3. Google Play Feature Graphic

### Specifications
* **Dimensions:** 1024 px width x 500 px height
* **Format:** PNG (24-bit, no alpha) or high-quality JPEG, max 15 MB
* **Color Background:** Diagonal gradient from Deep Emerald (`#064E3B`) to Forest Teal (`#022C22`).
* **Visual Composition:**
  * **Left Side (60%):**
    * Headline: **BD Salary Calculator** (Bold 48pt, `#FFFFFF`)
    * Sub-headline: **বেতন ও কর্তন হিসাব করুন সহজে** (Medium 24pt, `#A7F3D0`)
    * Feature pills: *Instant Take-Home* • *BDT (৳) Format* • *100% Offline*
  * **Right Side (40%):**
    * Tilted modern device mockup showing the green Result Card with:
      * "হাতে পাওয়া বেতন: ৳৪৬,০০০"
      * Gross: ৳৫০,০০০ | Deductions: ৳৪,০০০

---

## 4. Google Play Store Screenshots Plan

### Screen 1: The One-Screen Calculator (Hero View)
* **Title:** Instant Salary Calculation in Bangladesh
* **Subtitle:** Enter your Basic Salary and see gross, deductions & take-home pay immediately.
* **Content:** Clean preview of the top result card and basic salary card filled with `৳30,000`.

### Screen 2: Detailed Allowances & Deductions
* **Title:** Customized Allowances & Deductions
* **Subtitle:** Housing, Medical, Transport, Provident Fund, and custom rows with 1-tap addition.
* **Content:** Shows active allowance rows and deduction fields with clear ৳ prefixes.

### Screen 3: Authentic Bangla & English Localization
* **Title:** বাংলা ও ইংরেজি উভয় ভাষায় সহজ হিসাব
* **Subtitle:** Toggle languages instantly with authentic South Asian number formatting (৳৫০,০০০).
* **Content:** The interface displayed entirely in Bengali mode.

### Screen 4: Salary Summary & One-Tap Share
* **Title:** Proportional Breakdown & Easy Sharing
* **Subtitle:** Export your salary summary to WhatsApp, SMS, or clipboard in seconds.
* **Content:** Shows the visual color-coded salary proportion bar and the share action sheet.

---

## 5. Typography Standards

* **Display / Headline:** Default sans-serif with bold weight and negative letter spacing for crisp numeral rendering.
* **Bengali Glyphs:** System standard Unicode font ensuring consistent character shaping for conjuncts and Bengali numbers (`০, ১, ২, ৩, ৪, ৫, ৬, ৭, ৮, ৯`).
* **Accessibility:** All text uses scalable `sp` units respecting system font scaling up to 200%.
