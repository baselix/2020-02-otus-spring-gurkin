package ru.gurkin.spring.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.repository.UserRepository;
import ru.gurkin.spring.journal.service.impl.SequenceGeneratorService;

import java.util.Set;

@SpringBootApplication
public class JournalApplication {
	public static void main(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		PostRepository postRepository = context.getBean(PostRepository.class);
		SequenceGeneratorService sequenceGenerator = context.getBean(SequenceGeneratorService.class);
		MutableAclService mutableAclService = context.getBean(MutableAclService.class);
		PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);

		JournalUser user1 = userRepository.findByLoginIgnoreCase("user1");
		if(user1 == null){
			JournalUser user = new JournalUser("user1");
			user.setId(sequenceGenerator.generateSequence(JournalUser.SEQUENCE_NAME));
			user.setPassword("user1");
			user.setRole(UserRole.USER);
			userRepository.save(user);
		}

		JournalUser user2 = userRepository.findByLoginIgnoreCase("user2");
		if(user2 == null){
			JournalUser user = new JournalUser("user2");
			user.setId(sequenceGenerator.generateSequence(JournalUser.SEQUENCE_NAME));
			user.setPassword("user2");
			user.setRole(UserRole.USER);
			userRepository.save(user);
		}

		JournalUser admin = userRepository.findByLoginIgnoreCase("admin");
		if(admin == null) {
			JournalUser user = new JournalUser("admin");
			user.setId(sequenceGenerator.generateSequence(JournalUser.SEQUENCE_NAME));
			user.setPassword("admin");
			user.setRole(UserRole.ADMIN);
			admin = userRepository.save(user);
		}


		/*
		 *  Поскольку, база данных h2 обновляется при каждом запуске, а посты в MongoDB хранятся постоянно,
		 *  то возникает неприятная ситуация, когда в h2 нет записей о посте.
		 *  Стреляет ошибка, которую я не придумал, как правильно обработать.
		 *  Поэтому завожу запись для каждого уже существующего поста.
		 *  Права на пост выдаются всем администраторам.
		 *  Плюс, раз сид администратора в базе так же отсутствует,
		 *  заодно приходится поднимать транзакцию для добавления сида.
		 */
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(admin.getRole().name());
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin.getLogin(),admin.getPassword(), Set.of(grantedAuthority)));
		for(Post post : postRepository.findAll()){
			TransactionTemplate tt = new TransactionTemplate(transactionManager);
			tt.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					ObjectIdentity oid = new ObjectIdentityImpl( post.getClass(), post.getId() );
					MutableAcl acl = mutableAclService.createAcl(oid);
					acl.insertAce( acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid("ADMIN"), true );
					mutableAclService.updateAcl(acl);
				}
			});

		}
	}
}
