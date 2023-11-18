package com.myblog.Service.impl;

import com.myblog.Service.CommentService;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.BlogApiException;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto CreateComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId)
        );
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

       CommentDto dto1= mapToDto(newComment);

       return dto1;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post saurabh");
        }


        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Id", postId));


        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "Id", commentId));

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }

       // comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not exit");
        }
        commentRepository.delete(comment);


    }



    private Comment mapToEntity(CommentDto commentDto) {

        Comment comment=modelMapper.map(commentDto,Comment.class);

//        Comment comment = new Comment();
//
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;

    }

    private CommentDto mapToDto(Comment comment){

        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);

//        CommentDto commentDto=new CommentDto();
//
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;

    }
}


