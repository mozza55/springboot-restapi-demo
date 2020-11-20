package org.example.demo.rest.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Post {
    private Long postId;
    private Long memberId;
    @NonNull private String content;
    private LocalDateTime createdAt;

    public Post(@NonNull String content) {
        this.content = content;
    }

    public Post(Long memberId, @NonNull String content) {
        this.memberId = memberId;
        this.content = content;
    }

    public Post(Long memberId, @NonNull String content, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
