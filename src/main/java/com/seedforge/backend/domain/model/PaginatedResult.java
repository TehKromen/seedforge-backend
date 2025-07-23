package com.seedforge.backend.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PaginatedResult<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<T> content;


    public <R> PaginatedResult<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mappedContent = content .stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new PaginatedResult<>(page, size, totalElements, totalPages, mappedContent);
    }

}
