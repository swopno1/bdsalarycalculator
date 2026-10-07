# Changelog

All notable changes to the **BD Salary Calculator** application will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-10-07

### Added
- **Core Calculation Engine:** Instant real-time calculation of Basic Salary, Gross Salary, Total Allowances, Total Deductions, Take-Home Salary, and 12-month Annual projections.
- **South Asian Number Formatting:** Accurate comma grouping for Lakhs and Crores (e.g., `1,25,000` and `10,00,000`).
- **Bengali Numeral Support:** Native Bengali digit rendering (`৳১,২৫,০০০`, `৳৫০,০০০`) and bidirectional digit input parsing.
- **Bilingual Localization:** Instant language toggle between English and বাংলা.
- **Dynamic Allowances & Deductions:** Default fields (Housing, Medical, Transport, PF, Tax) plus customizable dynamic items.
- **Input Validation:** Non-negative enforcement, decimal handling, and warning alerts when deductions exceed gross earnings.
- **AdMob Integration:** Google Mobile Ads banner integration isolated via centralized `AdConfig` with official test IDs.
- **About & Legal Information:** Dedicated in-app About dialog featuring ViveScript Solutions LLC copyright and licensing notices.
- **Privacy by Design:** 100% on-device calculations without user tracking or cloud storage requirements.
- **Theme Support:** Material Design 3 Emerald color palette with dynamic light and dark theme compatibility.
