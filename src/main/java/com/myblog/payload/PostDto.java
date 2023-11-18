package com.myblog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=2,message="post title should be at least 2 character")
    private String title;

    @NotEmpty
    @Size(min=10, message="description should be at least 10 character")
    private String description;

    @NotEmpty
    @Size(min=10,message = "content should have at least 10 content")
    private String content;
}
