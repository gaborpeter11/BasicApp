package com.basesportperformance.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.basesportperformance.ui.addrecord.model.SportType
import com.basesportperformance.ui.theme.SportCycling
import com.basesportperformance.ui.theme.SportGym
import com.basesportperformance.ui.theme.SportHiking
import com.basesportperformance.ui.theme.SportOther
import com.basesportperformance.ui.theme.SportRunning
import com.basesportperformance.ui.theme.SportSwimming

fun SportType.color(): Color = when (this) {
    SportType.RUNNING -> SportRunning
    SportType.CYCLING -> SportCycling
    SportType.SWIMMING -> SportSwimming
    SportType.HIKING -> SportHiking
    SportType.GYM -> SportGym
    SportType.OTHER -> SportOther
}

fun SportType.icon(): ImageVector = when (this) {
    SportType.RUNNING -> Icons.AutoMirrored.Filled.DirectionsRun
    SportType.CYCLING -> Icons.AutoMirrored.Filled.DirectionsBike
    SportType.SWIMMING -> Icons.Filled.Pool
    SportType.HIKING -> Icons.Filled.Terrain
    SportType.GYM -> Icons.Filled.FitnessCenter
    SportType.OTHER -> Icons.Filled.SportsScore
}

private fun matchingSportType(displayName: String): SportType? =
    SportType.entries.firstOrNull { it.displayName == displayName }

fun String.sportColor(): Color = matchingSportType(this)?.color() ?: SportOther

fun String.sportIcon(): ImageVector = matchingSportType(this)?.icon() ?: Icons.Filled.SportsScore

@Composable
fun SportIconBadge(
    sport: String,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp
) {
    val color = sport.sportColor()

    Box(
        modifier = modifier
            .size(size)
            .background(color.copy(alpha = 0.16f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = sport.sportIcon(),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(size * 0.55f)
        )
    }
}