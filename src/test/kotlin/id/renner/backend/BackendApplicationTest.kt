package id.renner.backend

import id.renner.backend.util.decode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.net.Inet4Address

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("service-test")
class BackendApplicationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Test that you can paste something and retrieve it using the returned url`() {
        // given
        val message = "hello world!"

        // when
        val postResponse = mockMvc.perform(post("/p").content(message).characterEncoding(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString

        // then
        assertThat(postResponse)
                .isNotEmpty()

        // given
        val getPath = postResponse
                .removePrefix(Inet4Address.getLocalHost().hostName)
                .decode()
                .trim()

        // when
        val getResponse = mockMvc.perform(get(getPath))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString

        // then
        assertThat(getResponse)
                .isEqualTo(message)
    }
}
