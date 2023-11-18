package com.myblog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min=3,message="name shoulb be at least 3 character")

    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min=10,message = "body should be at least 10 character")
    private String body;

}
