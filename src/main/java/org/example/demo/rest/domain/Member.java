package org.example.demo.rest.domain;

import lombok.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
    private Long memberId;
    @NonNull private String userId;
    @NonNull private String name;
    @NonNull private String password;

}
