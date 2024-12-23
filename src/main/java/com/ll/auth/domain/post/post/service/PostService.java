package com.ll.auth.domain.post.post.service;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.repository.PostRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long getCount() {
        return postRepository.count();
    }

    public Post write(Member author, String title, String content) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();

        return postRepository.save(post);
    }

    public List<Post> findAllByOrderByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }

    public Post getPost(long id) {
        Optional<Post> op = postRepository.findById(id);
        return op.orElseThrow(() -> new NoSuchElementException("Post not found for id: " + id));
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void modify(Post post, String title, String content) {
        post.updateTitle(title);
        post.updateContent(content);
        postRepository.save(post);
    }
}