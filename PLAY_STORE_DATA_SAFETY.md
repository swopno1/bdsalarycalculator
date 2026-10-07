# Google Play Data Safety: BD Salary Calculator

**Application ID:** `com.vivescriptsolutions.bdsalarycalculator`  
**Publisher:** ViveScript Solutions LLC  
**Last Verified:** October 7, 2026

This document provides exact, verified answers for the **Data Safety form** in the Google Play Console for BD Salary Calculator.

---

## 1. Overview Summary

* **Does your app collect or share any user data?**  
  **Yes** (Solely technical and diagnostic data collected by the third-party Google Mobile Ads / AdMob SDK; the app itself does not collect or transmit user-authored financial or personal data).
* **Is all user data encrypted in transit?**  
  **Yes** (All network traffic from the Google Mobile Ads SDK uses HTTPS/TLS).
* **Do you provide a way for users to request that their data be deleted?**  
  **Yes** (Users can reset/delete their Google Advertising ID through Android OS settings, and can clear local on-device stored numbers via the in-app Reset button).

---

## 2. Data Types & Declarations

### A. Financial Info
* **Is financial info collected or shared?**  
  **NO.**  
  *Explanation:* All user-entered salary amounts, allowances, deductions, and take-home figures remain entirely on the local device. No financial figures are ever collected, logged, or shared over the network.

### B. Personal Info (Name, Email, Address, Phone, etc.)
* **Is personal info collected or shared?**  
  **NO.**

### C. Device or Other IDs (Collected by Google Mobile Ads SDK)
* **Data Type:** Device or other IDs (e.g., Android Advertising ID)
  * **Collected?** Yes (Collected automatically by Google Play Services / AdMob)
  * **Shared?** Yes (Shared with Google AdMob for ad serving)
  * **Ephemeral?** No
  * **Required or Optional?** Required for ad-supported functionality (user can opt out of personalized ads in device settings)
  * **Purpose:**
    * Advertising or marketing
    * Fraud prevention, security, and compliance
    * Analytics / Ad measurement

### D. App Info and Performance (Collected by Google Mobile Ads SDK)
* **Data Type:** Diagnostics / Crash logs / Performance diagnostics
  * **Collected?** Yes (Automatically by Google Mobile Ads SDK)
  * **Shared?** Yes (Shared with Google)
  * **Purpose:**
    * App functionality
    * Analytics / Performance diagnostics

---

## 3. Play Console Questionnaire Answers Checklist

| Question | Selection |
| :--- | :--- |
| Does your app collect or share any of the required user data types? | **Yes** |
| Is all of the user data collected by your app encrypted in transit? | **Yes** |
| Does your app provide a way for users to request that their data be deleted? | **Yes** |
| Does your app collect Location data? | **No** |
| Does your app collect Personal Info? | **No** |
| Does your app collect Financial Info? | **No** |
| Does your app collect Health or Fitness data? | **No** |
| Does your app collect Messages, Photos, Audio, or Files? | **No** |
| Does your app collect Calendar or Contacts? | **No** |
| Does your app collect Device or other IDs? | **Yes** (Advertising ID by Google Mobile Ads) |
| Device IDs purpose | **Advertising or marketing, Analytics, Fraud prevention** |

---

## 4. Notes for Reviewers

BD Salary Calculator operates as a standalone offline-first utility. The only external network requests originate from the official Google Mobile Ads SDK. All financial inputs and calculations are strictly isolated within the Android application's local process sandbox.
