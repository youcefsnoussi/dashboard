package com.commercial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.backup_edit.article_backup;
import com.commercial.entities.schema.backup_edit.repository.article_backupRepository;

@SpringBootApplication
public class CommercialStableApplication {
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	article_backupRepository art_bRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CommercialStableApplication.class, args);
	}
	/*
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("============= ENTER TEST RUN ============= ");
		
		article art = artRepo.getOne((long) 1);
		
		System.out.println(art.getCode());
		
		article_backup art_b = new article_backup(art);
		
		art_bRepo.save(art_b);art_bRepo.flush();
		
		System.out.println("=>"+art_b.getId_article()+" / id -> "+art_b.getId());
		
	}
	*/
}
