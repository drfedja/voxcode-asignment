package com.voxcode.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.toJavaInstant

@RequiresApi(Build.VERSION_CODES.O)
internal fun formatArticleDate(
    instant: kotlin.time.Instant
): String =
    DateTimeFormatter.ofPattern("MMM d, yyyy")
        .withZone(ZoneId.systemDefault())
        .format(instant.toJavaInstant())