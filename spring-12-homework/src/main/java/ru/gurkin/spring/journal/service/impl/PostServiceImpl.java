package ru.gurkin.spring.journal.service.impl;

import com.google.common.base.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.service.PostService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.journal.model.ErrorConstants.*;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository dao;

	public PostServiceImpl(PostRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Post> getAll() {
		return dao.findByUser(getUser().getUsername());
	}

	@Override
	public Post getById(String id) {
		checkNotNull(id, ID_ERROR);
		Post post = dao.findById(id)
				.orElse(null);
		if(post != null && !post.getUser().equals(getUser().getUsername())){
			return null;
		}
		return post;
	}

	@Override
	public Post create(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		post.setUser(getUser().getUsername());
		return dao.save(post);
	}

	@Override
	public Post update(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		post.setUser(getUser().getUsername());
		return dao.save(post);
	}

	@Override
	public void delete(String id) {
		checkNotNull(id, ID_ERROR);
		Post post = getById(id);
		if(post != null) {
			dao.delete(post);
		}
	}

	@Override
	public List<Post> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.findByUserAndTitleLikeIgnoreCase(getUser().getUsername(), titleFilter);
	}

	private User getUser(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		return (User) authentication.getPrincipal();
	}
}