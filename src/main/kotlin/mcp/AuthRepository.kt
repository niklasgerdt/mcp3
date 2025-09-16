package mcp

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import java.util.List


internal class AuthorRepository {

    @Tool(description = "Get Baeldung author details using an article title")
    fun getAuthorByArticleTitle(articleTitle: String?): Author {
        return Author("John Doe", "john.doe@baeldung.com")
    }

    @Tool(description = "Get highest rated Baeldung authors")
    fun getTopAuthors(): MutableList<Author?> {
        val l = List.of<Author?>(
            Author("John Doe", "john.doe@baeldung.com"),
            Author("Jane Doe", "jane.doe@baeldung.com")
        )
        return l;
    }

    data class Author(val name: String?, val email: String?)
}

@Bean
fun authorTools(): ToolCallbackProvider {
    return MethodToolCallbackProvider
        .builder()
        .toolObjects(AuthorRepository())
        .build()
}