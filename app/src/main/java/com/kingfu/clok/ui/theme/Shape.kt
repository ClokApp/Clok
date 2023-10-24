package com.kingfu.clok.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shape = Shapes(
    extraSmall = RoundedCornerShape(size = 2.dp),
    small = RoundedCornerShape(size = 4.dp),
    medium = RoundedCornerShape(size = 8.dp),
    large = RoundedCornerShape(size = 16.dp),
    extraLarge = RoundedCornerShape(size = 32.dp)
)

val RoundedCornerHeader = RoundedCornerShape(
    topStart = Shape.large.topStart,
    topEnd = Shape.large.topEnd,
    bottomStart = CornerSize(size = 0.dp),
    bottomEnd = CornerSize(size = 0.dp)
)

val RoundedCornerFooter = RoundedCornerShape(
    topStart = CornerSize(size = 0.dp),
    topEnd = CornerSize(size = 0.dp),
    bottomStart = Shape.large.bottomStart,
    bottomEnd = Shape.large.bottomEnd
)

val RoundedCornerHeaderAndFooter = RoundedCornerShape(
    topStart = Shape.large.topStart,
    topEnd = Shape.large.topEnd,
    bottomStart = Shape.large.bottomStart,
    bottomEnd = Shape.large.bottomEnd
)

val RoundedCornerBody = RoundedCornerShape(
    topStart = CornerSize(size = 0.dp),
    topEnd = CornerSize(size = 0.dp),
    bottomStart = CornerSize(size = 0.dp),
    bottomEnd = CornerSize(size = 0.dp)
)



