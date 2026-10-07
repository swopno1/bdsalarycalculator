# BD Salary Calculator (বিডি বেতন ক্যালকুলেটর)

A simple, fast, lightweight, and offline-first Android application designed specifically for Bangladesh employees, job seekers, HR/payroll professionals, and office workers to instantly calculate salary breakdowns.

## Features

- **Instant Salary Calculations:**
  - **Basic Salary** (মূল বেতন)
  - **Allowances** (ভাতা সমূহ): Housing (বাড়ি ভাড়া), Medical (চিকিৎসা), Transport (যাতায়াত), Other (অন্যান্য), plus custom additions
  - **Gross Salary** (মোট বেতন)
  - **Deductions** (কর্তন সমূহ): Provident Fund (প্রভিডেন্ট ফান্ড), Income Tax (আয়কর কর্তন), Other deductions, plus custom additions
  - **Take-Home / Net Salary** (হাতে পাওয়া বেতন)
  - **Annual Gross & Annual Take-Home** (বার্ষিক বেতন)
- **Bangladesh & South Asian Number Formatting:**
  - Proper grouping (e.g., `1,25,000` and `10,00,000`)
  - Full Bengali numerals support (`৳১,২৫,০০০`, `৳৫০,০০০`)
- **Bilingual Localization:**
  - Seamless toggle between **English** and **বাংলা** right from the top bar.
- **Offline & Private:**
  - 100% offline — zero server calls, no account or login required, zero analytics tracking of financial figures.
  - Optional on-device persistence remembers last calculation.
- **Robust Input Validation:**
  - Prevents negative values, handles decimal numbers, warns if deductions exceed gross salary.
- **Material 3 Design:**
  - Light & Dark mode support.
  - Adaptive layout for mobile, foldables, and tablets.
  - One-tap share and clipboard copy for salary breakdown.

## Calculation Formula

$$\text{Total Allowances} = \text{Housing} + \text{Medical} + \text{Transport} + \text{Other} + \sum \text{Custom Allowances}$$

$$\text{Gross Salary} = \text{Basic Salary} + \text{Total Allowances}$$

$$\text{Total Deductions} = \text{Provident Fund} + \text{Income Tax} + \text{Other} + \sum \text{Custom Deductions}$$

$$\text{Take-Home Salary} = \max(0, \text{Gross Salary} - \text{Total Deductions})$$

$$\text{Annual Gross} = \text{Gross Salary} \times 12$$

$$\text{Annual Take-Home} = \text{Take-Home Salary} \times 12$$

## Google Play Store Listing Information

- **App Name:** BD Salary Calculator
- **Short Description (EN):** Calculate Bangladesh salary, gross pay, deductions and take-home salary quickly.
- **Short Description (BN):** বাংলাদেশে বেতন, ভাতা, কর্তন ও হাতে পাওয়া বেতন সহজে হিসাব করুন।
- **Keywords:** salary calculator Bangladesh, BD salary calculator, বেতন হিসাব, বেতন ক্যালকুলেটর, gross salary calculator, take home salary, monthly salary calculation

## Build & Test Instructions

```bash
# Run unit tests
gradle :app:testDebugUnitTest

# Build debug APK
gradle :app:assembleDebug
```
