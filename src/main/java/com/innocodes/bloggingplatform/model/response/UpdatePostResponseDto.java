package com.innocodes.bloggingplatform.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UpdatePostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime updatedAt;
    private String location;
    private Double temperature;
    private List<String> weatherConditions;
}
