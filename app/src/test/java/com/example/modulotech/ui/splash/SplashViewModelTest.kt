package com.example.modulotech.ui.splash

import androidx.lifecycle.asFlow
import com.example.modulotech.data.DataRepository
import com.example.modulotech.util.CoroutinesTestExtension
import com.example.modulotech.util.InstantExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

internal class SplashViewModelTest {

    @MockK
    private lateinit var mRepository: DataRepository

    private lateinit var mViewModel: SplashViewModel

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mViewModel = SplashViewModel(mRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class LoadResources {
        @Test
        @ExtendWith(InstantExecutorExtension::class)
        fun `returns the same Flow as DataRepository#refresh`() {
            val flow: Flow<Result<Unit>> = flow { emit(runCatching { }) }
            every {
                mRepository.refresh(any())
            } returns flow

            runBlockingTest {
                assertEquals(
                    flow.first(),
                    mViewModel.loadResources().asFlow().first()
                )
            }
        }
    }
}
