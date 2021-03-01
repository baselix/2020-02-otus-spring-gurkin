package ru.gurkin.spring.journal.service.impl;

import com.google.common.base.Strings;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	private final MutableAclService mutableAclService;
	private final SequenceGeneratorService sequenceGenerator;

	public PostServiceImpl(PostRepository dao, MutableAclService mutableAclService,
						   SequenceGeneratorService sequenceGenerator) {
		this.dao = dao;
		this.mutableAclService = mutableAclService;
		this.sequenceGenerator = sequenceGenerator;
	}

	@PostFilter("hasPermission(filterObject, 'READ')")
	@Override
	public List<Post> getAll() {
		return dao.findAll();
	}

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	@Override
	public Post getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id)
				.orElse(null);
	}

	@Transactional
	@Override
	public Post create(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		post.setId(sequenceGenerator.generateSequence(Post.SEQUENCE_NAME));
		Post createdPost = dao.save(post);
		final Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
		final Sid admin = new GrantedAuthoritySid("ADMIN");
		ObjectIdentity oid = new ObjectIdentityImpl( createdPost.getClass(), createdPost.getId() );
		MutableAcl acl = mutableAclService.createAcl( oid );
		acl.setOwner( owner );
		acl.insertAce( acl.getEntries().size(), BasePermission.READ, owner, true );
		acl.insertAce( acl.getEntries().size(), BasePermission.READ, admin, true );
		acl.insertAce( acl.getEntries().size(), BasePermission.WRITE, owner, true );
		mutableAclService.updateAcl( acl );
		return createdPost;
	}

	@PreAuthorize("hasPermission(#post, 'WRITE')")
	@Override
	public Post update(Post post) {
		checkNotNull(post, POST_ERROR);
		checkArgument(post.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(post.getTitle()), TITLE_ERROR);
		return dao.save(post);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		Post post = getById(id);
		if(post != null) {
			dao.delete(post);
		}
	}

	@PostFilter("hasPermission(filterObject, 'READ')")
	@Override
	public List<Post> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.findByTitleLikeIgnoreCase(titleFilter);
	}
}