package ru.gurkin.spring.journal.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.service.PostService;

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
	public Flux<Post> getAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Post> getById(String id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id);
	}

	@Override
	public Mono<Post> create(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		return dao.save(post);
	}

	@Override
	public Mono<Post> update(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		return dao.save(post);
	}

	@Override
	public void delete(String id) {
		checkNotNull(id, ID_ERROR);
		dao.deleteById(id);
	}

	@Override
	public Flux<Post> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.findByTitleLikeIgnoreCase(titleFilter);
	}
}