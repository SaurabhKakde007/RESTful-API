package com.myblog.Service;

import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto CreateComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId (long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long postId,long commentId);
}
