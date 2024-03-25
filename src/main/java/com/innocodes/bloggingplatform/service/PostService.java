package com.innocodes.bloggingplatform.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocodes.bloggingplatform.exceptions.BadRequestException;
import com.innocodes.bloggingplatform.model.entity.Author;
import com.innocodes.bloggingplatform.model.entity.Post;
import com.innocodes.bloggingplatform.model.request.ApiResponse;
import com.innocodes.bloggingplatform.model.dto.PostDto;
import com.innocodes.bloggingplatform.model.dto.UpdatePostDto;
import com.innocodes.bloggingplatform.model.request.WeatherMain;
import com.innocodes.bloggingplatform.model.response.PostResponseDto;
import com.innocodes.bloggingplatform.model.response.WeatherDescription;
import com.innocodes.bloggingplatform.model.response.WeatherResponse;
import com.innocodes.bloggingplatform.repository.AuthorRepository;
import com.innocodes.bloggingplatform.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    public ApiResponse<Object> createPost(PostDto postDto) {
        if (postDto.getTitle() == null || postDto.getContent() == null || postDto.getAuthor() == null) {
            return ApiResponse.builder()
                    .message("Title, content, and author are required")
                    .status("error")
                    .build();
        }

        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setContent(postDto.getContent());
        newPost.setLocation(postDto.getLocation());

        Author author = new Author();
        author.setName(postDto.getAuthor());
        authorRepository.save(author);

        newPost.setAuthor(author);

        String location = postDto.getLocation();
        WeatherResponse weatherResponse = weatherService.getWeatherData(location);

        if (weatherResponse != null) {
            WeatherMain weatherMain = weatherResponse.getMain();
            if (weatherMain != null) {
                double temperature = weatherMain.getTemperature();
                newPost.setTemperature(temperature);

                double humidity = weatherMain.getHumidity();
                newPost.setHumidity(humidity);

                double pressure = weatherMain.getPressure();
                newPost.setPressure(pressure);
            } else {
                newPost.setTemperature(0.0);
                newPost.setHumidity(0.0);
                newPost.setPressure(0.0);
            }

            List<WeatherDescription> weatherDescriptions = weatherResponse.getWeather();
            List<String> weatherConditions = new ArrayList<>();

            for (WeatherDescription description : weatherDescriptions) {
                weatherConditions.add(description.getDescription());
            }

            newPost.setWeatherConditions(weatherConditions);
        } else {
            newPost.setTemperature(0.0);
            newPost.setHumidity(0.0);
            newPost.setPressure(0.0);
        }

        postRepository.save(newPost);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(newPost.getId());
        postResponseDto.setTitle(newPost.getTitle());
        postResponseDto.setContent(newPost.getContent());
        postResponseDto.setAuthorName(newPost.getAuthor().getName());
        postResponseDto.setCreatedAt(newPost.getCreatedAt());
        postResponseDto.setLocation(newPost.getLocation());
        postResponseDto.setTemperature(newPost.getTemperature());
        postResponseDto.setHumidity(newPost.getHumidity());
        postResponseDto.setPressure(newPost.getPressure());
        postResponseDto.setWeatherConditions(newPost.getWeatherConditions());

        return ApiResponse.builder()
                .message("Post created successfully")
                .status("success")
                .data(postResponseDto)
                .build();
    }

    public ApiResponse<Object> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto postResponseDto = new PostResponseDto();
            postResponseDto.setId(post.getId());
            postResponseDto.setTitle(post.getTitle());
            postResponseDto.setContent(post.getContent());

            Author author = post.getAuthor();
            if (author != null) {
                postResponseDto.setAuthorName(author.getName());
            }

            postResponseDto.setCreatedAt(post.getCreatedAt());
            postResponseDto.setLocation(post.getLocation());
            postResponseDto.setUpdatedAt(post.getUpdatedAt());
            postResponseDto.setTemperature(post.getTemperature());
            postResponseDto.setPressure(post.getPressure());
            postResponseDto.setHumidity(post.getHumidity());
            postResponseDto.setWeatherConditions(post.getWeatherConditions());

            postResponseDtos.add(postResponseDto);
        }

        return ApiResponse.builder()
                .message("Posts retrieved successfully")
                .status("success")
                .data(postResponseDtos)
                .build();
    }

    public ApiResponse<Object> getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ApiResponse.builder()
                    .message("Post not found")
                    .status("error")
                    .build();
        }

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setTemperature(post.getTemperature());
        postResponseDto.setHumidity(post.getHumidity());
        postResponseDto.setPressure(post.getPressure());
        postResponseDto.setAuthorName(post.getAuthor().getName());
        postResponseDto.setLocation(post.getLocation());
        postResponseDto.setUpdatedAt(post.getUpdatedAt());
        postResponseDto.setWeatherConditions(post.getWeatherConditions());

        return ApiResponse.builder()
                .message("Post retrieved successfully")
                .status("success")
                .data(postResponseDto)
                .build();
    }

    public ApiResponse<Object> deletePostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new BadRequestException("Post not found");
        }
        postRepository.delete(post);
        return ApiResponse.builder()
                .message("Post deleted successfully")
                .status("success")
                .build();
    }

    public ApiResponse<Object> updatePost(Long id, UpdatePostDto updatePostDto) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new BadRequestException("Post not found");
        }
        // Configure ModelMapper to ignore null values
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);


        modelMapper.map(updatePostDto, post);

        if (updatePostDto.getAuthor() != null) {
            Author author = post.getAuthor();
            if (author == null) {
                author = new Author();
                post.setAuthor(author);
            }
            author.setName(updatePostDto.getAuthor());
            authorRepository.save(author);
        }

        postRepository.save(post);
        return ApiResponse.builder()
                .message("Post updated successfully")
                .status("success")
                .build();
    }
}
