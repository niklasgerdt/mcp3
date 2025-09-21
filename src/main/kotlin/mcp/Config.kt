package mcp

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
open class Config {

    @Bean
    open fun requestLoggingFilter(): CommonsRequestLoggingFilter {
        val loggingFilter = CommonsRequestLoggingFilter()
        loggingFilter.setIncludeClientInfo(true)
        loggingFilter.setIncludeQueryString(true)
        loggingFilter.setIncludePayload(true)
        loggingFilter.setIncludeHeaders(true)
        loggingFilter.setMaxPayloadLength(64000)
        return loggingFilter
    }

    @Bean
    open fun authorTools(authorRepository: AuthorRepository): ToolCallbackProvider {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(authorRepository)
            .build()
    }
}

