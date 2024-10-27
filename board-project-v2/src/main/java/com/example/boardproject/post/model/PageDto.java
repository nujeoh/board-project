package com.example.boardproject.post.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto{

    private List<PostDto> content;

    private Integer page;

    private Integer size;

    private Integer currentElements;

    private Integer totalPages;

    private Long totalElements;
}
