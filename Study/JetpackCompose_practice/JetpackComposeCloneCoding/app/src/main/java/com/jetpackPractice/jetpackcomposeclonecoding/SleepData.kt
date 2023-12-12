package com.jetpackPractice.jetpackcomposeclonecoding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.Yellow_Awake
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.Yellow_Deep
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.Yellow_Light
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.Yellow_Rem
import java.time.Duration
import java.time.LocalDateTime


// enum class로 각 상태에 따라 색상 값을 정해줌
enum class SleepType(val title : Int, val color : Color){
    Awake(R.string.sleep_type_awake, Yellow_Awake),
    REM(R.string.sleep_type_rem, Yellow_Rem),
    Light(R.string.sleep_type_light, Yellow_Light),
    Deep(R.string.sleep_type_deep, Yellow_Deep)
}

// Duration.between은 안드로이드 API 26인 오레오 이상에서만 수행됩니다.
// 만약 안드로이드가 최소 sdk가 26미만이라면 SDK를 수동으로 직접 판단하거나, @RequireApi로 해당 버전 이상에서만 수행되게 만들거나
// 혹은 build.gradle에서 defaultConfig 내의 최소 안드로이드 버전(minsdk)을 26으로 만들어주시면 됩니다.
data class SleepPeriod(
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val type: SleepType
){
    val duration : Duration by lazy{
        Duration.between(startTime, endTime)
    }
}

data class SleepDayData(
    val startDate : LocalDateTime,
    val sleepPeriods: List<SleepPeriod>,
    val sleepScore : Int,
) {
    val firstSleepStart : LocalDateTime by lazy{
        // sleepPeriods 리스트를 정렬해준다 기준은 SleepPeriod의 startTime을 참조한 값으로, 그후 first()는 가장 첫번째 요소를 달라
        sleepPeriods.sortedBy(SleepPeriod::startTime).first().startTime
    }
    val lastSleepEnd : LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::endTime).last().endTime
    }
    val totalTimeInBed: Duration by lazy{
        Duration.between(firstSleepStart, lastSleepEnd)
    }

    val sleepScoreEmoji : String by lazy{
        when(sleepScore){
            in 0..40 -> "😖"
            in 41..60 -> "😏"
            in 60..70 -> "😴"
            in 71..100 -> "😃"
            else -> "🤷‍"
        }
    }

    fun fractionOfTotalTime(sleepPeriod: SleepPeriod) : Float{
        return sleepPeriod.duration.toMinutes() / totalTimeInBed.toMinutes().toFloat()
    }

    fun minutesAfterSleepStart(sleepPeriod: SleepPeriod) : Long{
        return Duration.between(
            firstSleepStart,
            sleepPeriod.startTime
        ).toMinutes()
    }

}

data class SleepGraphData(
    val sleepDayData: List<SleepDayData>,
) {
    val earliestStartHour: Int by lazy {
        sleepDayData.minOf { it.firstSleepStart.hour }
    }
    val latestEndHour: Int by lazy {
        sleepDayData.maxOf { it.lastSleepEnd.hour }
    }
}