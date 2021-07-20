package com.abilashcse.leaguestandings.viewmodels.recentwins

import com.abilashcse.leaguestandings.data.model.Season
import com.abilashcse.leaguestandings.data.model.recentwins.RecentWinsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.*

class RecentWinsViewModelTest {

    private lateinit var testRecentWinsViewModel: RecentWinsViewModel
    @Mock
    private lateinit var mockRecentWinsRepository: RecentWinsRepository
    @Before
    fun setUp() {
        mockRecentWinsRepository = Mockito.mock(RecentWinsRepository::class.java)
        testRecentWinsViewModel = RecentWinsViewModel(mockRecentWinsRepository)
    }
    @Test
    fun testDateRange_When_season_is_ongoing() {
        //When season is ongoing to date should be today and from date is 30 days before today
        val dateToday = Date()
        val calendar = Calendar.getInstance()
        calendar.time = dateToday
        calendar.add(Calendar.DAY_OF_YEAR, - 90)
        val startDate = calendar.time
        calendar.time = dateToday
        calendar.add(Calendar.DAY_OF_YEAR, +90)
        val endDate = calendar.time
        val season = Season(startDate, endDate)
        val (fromDate, toDate) = testRecentWinsViewModel.getDateRange(season)
        //ToDate should be today.
        val toDateCalendar = Calendar.getInstance()
        toDateCalendar.time = toDate
        Assert.assertTrue(toDateCalendar.isToday())

        //Add 30 days from fromDate and it should be today.
        val fromDateCalendar = Calendar.getInstance()
        fromDateCalendar.time = fromDate
        fromDateCalendar.add(Calendar.DAY_OF_YEAR,  30)
        Assert.assertTrue(fromDateCalendar.isToday())

    }


    @Test
    fun testDateRange_When_season_is_finished() {
        //When season is finished to date should be last day of the season and from date is 30 days before to date
        val dateToday = Date()
        val calendar = Calendar.getInstance()
        calendar.time = dateToday
        calendar.add(Calendar.DAY_OF_YEAR, - 90)
        val startDate = calendar.time

        calendar.time = dateToday
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val endDate = calendar.time
        val season = Season(startDate, endDate)

        val (fromDate, toDate) = testRecentWinsViewModel.getDateRange(season)
        //ToDate should be endDate.
        Assert.assertTrue(toDate == endDate)

        // From date will be 30 days before season end date
        val fromDateCalendar = Calendar.getInstance()
        fromDateCalendar.time = endDate
        fromDateCalendar.add(Calendar.DAY_OF_YEAR,  -30)

        Assert.assertTrue(fromDate == fromDateCalendar.time)

    }

    private fun Calendar.isToday() : Boolean {
        val today = Calendar.getInstance()
        return today[Calendar.YEAR] == get(Calendar.YEAR) && today[Calendar.DAY_OF_YEAR] == get(
            Calendar.DAY_OF_YEAR
        )
    }
}
