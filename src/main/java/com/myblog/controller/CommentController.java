package com.myblog.controller;

import com.myblog.Service.CommentService;
import com.myblog.payload.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/kkc/posts/1/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> CreateComment(@Valid @PathVariable("postId") long postId , @RequestBody CommentDto commentDto,
                                                    BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CommentDto dto = commentService.CreateComment(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    //http://localhost:8080/api/posts/1/comments
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId){
        List<CommentDto> commentByPostId = commentService.getCommentByPostId(postId);
        return commentByPostId;


    }
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){

        CommentDto dto = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(dto,HttpStatus.OK);


    }

    //http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{commentId}")
        public ResponseEntity<CommentDto> updateComment(@PathVariable("postId")long postId,
                      @PathVariable("commentId")long commentId,
                      @RequestBody CommentDto commentDto){

        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/2/comments/1
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId")long postId,
                                                @PathVariable("commentId") long commentId){


        commentService.deleteComment(postId,commentId);

        return new ResponseEntity<>("Comment is delete",HttpStatus.OK);




    }
}
