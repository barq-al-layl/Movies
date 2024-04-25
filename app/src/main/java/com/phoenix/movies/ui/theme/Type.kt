package com.phoenix.movies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.phoenix.movies.R

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val lobsterTow = GoogleFont("Lobster Two")
private val roboto = GoogleFont("Robot")

val LobsterTwo = FontFamily(
    Font(lobsterTow, fontProvider),
)

val Roboto = FontFamily(
    Font(roboto, fontProvider),
)

val Typography = Typography()