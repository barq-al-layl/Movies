package com.phoenix.movies

import com.phoenix.movies.di.ApplicationModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ApiUnitTest {

    @Test
    fun apiSearchResponse() = runBlocking {
        val service = ApplicationModule.providesMoviesApiService()
        val response = service.searchTitle(
            title = "Spider man",
            limit = 10,
        )
        assertEquals(10, response.results.size)
        assertEquals(1, response.paginationKey)
        assertEquals(
            true,
            response.results.first().title.contains("Spider man", ignoreCase = true)
        )
    }

    @Test
    fun apiOverviewDetailsResponse() = runBlocking {
        val service = ApplicationModule.providesMoviesApiService()
        val response = service.getOverviewDetails("tt2250912")
        assertEquals("Spider-Man: Homecoming", response.title.title)
        assertEquals(2017, response.title.year)
        assertEquals(133, response.title.runningTimeInMinutes)
    }
}
