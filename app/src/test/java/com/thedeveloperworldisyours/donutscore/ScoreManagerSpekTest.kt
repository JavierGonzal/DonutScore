package com.thedeveloperworldisyours.donutscore

import com.nhaarman.mockito_kotlin.whenever
import com.thedeveloperworldisyours.donutscore.data.CreditReportInfo
import com.thedeveloperworldisyours.donutscore.data.Example
import com.thedeveloperworldisyours.donutscore.data.ExampleAPI
import com.thedeveloperworldisyours.donutscore.score.ScoreManager
import com.thedeveloperworldisyours.donutscore.util.MockedCall
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.Spek
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Created by javiergonzalezcabezas on 8/1/18.
 */
class ScoreManagerSpekTest : Spek({
    given("a ScoreManager") {
        var example: Example? = null
        var apiMock = mock<ExampleAPI>()

        beforeEach {
            example = null
            apiMock = mock()
        }

        on("service returns something") {
                val response = Example(null, CreditReportInfo(543, null, null, null, null,null, null, null, null), null, null, null, null)
            beforeEach {
                // given
                val callMock = MockedCall(response)
                whenever(apiMock.getExample()).thenReturn(callMock)

                // when
                val scoreManager = ScoreManager(apiMock)
                runBlocking {
                    example = scoreManager.getExample()
                }
            }

            it("should receive something and no errors") {
                assertNotNull(example)
                assert(example!!.creditReportInfo?.score == response!!.creditReportInfo?.score)
                assert(example!!.creditReportInfo?.maxScoreValue == response!!.creditReportInfo?.maxScoreValue)
            }

        }
    }
})