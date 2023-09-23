package com.gumu.tracker_data.repository

import com.google.common.truth.Truth.assertThat
import com.gumu.tracker_data.remote.OpenFoodApi
import com.gumu.tracker_data.remote.malformedFoodResponse
import com.gumu.tracker_data.remote.validFoodResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.internal.http.HTTP_FORBIDDEN
import okhttp3.internal.http.HTTP_OK
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TrackerRepositoryImplTest {
    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodApi::class.java)
        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenSearchFoodRequest_whenGettingValidResponse_thenReturnResults() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HTTP_OK)
                .setBody(validFoodResponse)
        )
        val result = repository.searchFood("apple", 1, 40)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun givenSearchFoodRequest_whenGettingInvalidResponse_thenReturnFailure() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HTTP_FORBIDDEN)
                .setBody(validFoodResponse)
        )
        val result = repository.searchFood("apple", 1, 40)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun givenSearchFoodRequest_whenGettingMalformedResponse_thenReturnFailure() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(malformedFoodResponse)
        )
        val result = repository.searchFood("apple", 1, 40)

        assertThat(result.isFailure).isTrue()
    }
}
