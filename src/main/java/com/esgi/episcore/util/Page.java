package com.esgi.episcore.util;

import java.util.List;

/**
 * Résultat paginé générique.
 *
 * Utilisation dans un DAO :
 * <pre>
 *   String sql = "SELECT * FROM players LIMIT ? OFFSET ?";
 *   int offset = page * size;
 *   // ... remplir la liste ...
 *   long total = count(); // SELECT COUNT(*)
 *   return new Page<>(list, page, size, total);
 * </pre>
 *
 * @param <T> type des éléments (généralement un DTO)
 */
public final class Page<T> {

    private final List<T> content;
    private final int     page;
    private final int     size;
    private final long    totalElements;

    public Page(List<T> content, int page, int size, long totalElements) {
        if (page < 0)  throw new IllegalArgumentException("page doit être >= 0, reçu : " + page);
        if (size <= 0) throw new IllegalArgumentException("size doit être > 0, reçu : " + size);
        if (totalElements < 0) throw new IllegalArgumentException("totalElements doit être >= 0");

        this.content       = List.copyOf(content);
        this.page          = page;
        this.size          = size;
        this.totalElements = totalElements;
    }

    // ── getters ──────────────────────────────────────────────
    public List<T> getContent()       { return content; }
    public int     getPage()          { return page; }
    public int     getSize()          { return size; }
    public long    getTotalElements() { return totalElements; }

    /** Nombre total de pages (arrondi supérieur). */
    public int getTotalPages() {
        return size == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
    }

    /** Vrai si cette page est la dernière. */
    public boolean isLast() {
        return page >= getTotalPages() - 1;
    }

    @Override
    public String toString() {
        return String.format("Page{page=%d/%d, size=%d, totalElements=%d, items=%d}",
                page + 1, getTotalPages(), size, totalElements, content.size());
    }
}
