package com.example.modulotech.util

import java.io.InputStreamReader
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(MockResponseFileReader(fileName).content)
    )
}

class MockResponseFileReader(fileName: String) {

    val content =
        InputStreamReader(
            this.javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        ).use {
            it.readText()
        }
}
