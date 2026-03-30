package com.example.jetpack.domain.catalog

import com.example.jetpack.domain.enums.HomeShortcut

/**
 * Metadata for one home-grid sample: where to copy from and how to find it (search + Cursor).
 *
 * @param primarySourcePath Path under `app/src/main/java/com/example/jetpack/` (main file to copy).
 * @param extraHints Optional related files (components, ViewModels).
 * @param searchAliases Extra lowercase tokens matched by home search (synonyms, APIs, libraries).
 */
data class SampleMeta(
    val shortcut: HomeShortcut,
    val topics: Set<SampleTopic>,
    val primarySourcePath: String,
    val extraHints: List<String> = emptyList(),
    val searchAliases: Set<String> = emptySet(),
    val notes: String = "",
) {
    fun matchesQuery(normalizedQuery: String): Boolean {
        if (normalizedQuery.isBlank()) return true
        if (shortcut.name.lowercase().contains(normalizedQuery)) return true
        if (topics.any { it.name.lowercase().contains(normalizedQuery) }) return true
        if (searchAliases.any { it.contains(normalizedQuery) }) return true
        if (primarySourcePath.lowercase().contains(normalizedQuery)) return true
        if (extraHints.any { it.lowercase().contains(normalizedQuery) }) return true
        if (notes.lowercase().contains(normalizedQuery)) return true
        return false
    }
}

/**
 * Samples reachable from the app but not listed as [HomeShortcut] (tabs, one-off composables).
 */
data class StandalonePlayground(
    val title: String,
    val topics: Set<SampleTopic>,
    val primarySourcePath: String,
    val searchAliases: Set<String> = emptySet(),
    val notes: String = "",
)
