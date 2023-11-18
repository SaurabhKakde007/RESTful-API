package com.myblog.Service.impl;

import com.myblog.Service.PostService;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Dto to entity
        Post post=mapToEntity(postDto);
        Post newPost=postRepository.save(post);

        //convert entity to dto
       PostDto newPostDto= mapToDto(newPost);

        return newPostDto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> content = posts.getContent();

        List<PostDto> dtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(dtos);

        postResponse.setPageNo(posts.getTotalPages());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);



    }

    @Override
    public void deletePostById(long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        postRepository.delete(post);


    }


    Post mapToEntity(PostDto postDto){


        Post post=modelMapper.map(postDto,Post.class);

//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToDto(Post post){

        PostDto postDto=modelMapper.map(post,PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }


}
