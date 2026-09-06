package com.commercial.restController;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.parent_article_mapping;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.parent_article_mappingRepository;

@RestController
@SessionAttributes("user")
public class ParentArticleMappingRestController {

	@Autowired
	private parent_article_mappingRepository mappingRepo;

	@Autowired
	private articleRepository artRepo;

	public ParentArticleMappingRestController() {
	}

	/**
	 * Get parent mapping for a specific article (child)
	 */
	@GetMapping(value = "/getParentMapping")
	public Map<String, Object> getParentMapping(@RequestParam("articleId") long articleId) {
		Map<String, Object> result = new HashMap<>();
		article art = artRepo.getOne(articleId);
		parent_article_mapping mapping = mappingRepo.findByChildArticle(art);
		if (mapping != null) {
			result.put("parentArticleId", mapping.getParent_article().getId());
		} else {
			result.put("parentArticleId", 0);
		}
		return result;
	}

	/**
	 * Save or update parent mapping for an article
	 */
	@PostMapping(value = "/saveParentMapping")
	@Transactional
	public Map<String, Object> saveParentMapping(
			@RequestParam("childArticleId") long childArticleId,
			@RequestParam(value = "parentArticleId", required = false, defaultValue = "0") long parentArticleId) {

		Map<String, Object> result = new HashMap<>();

		article childArt = artRepo.getOne(childArticleId);

		// Remove existing mapping for this child
		parent_article_mapping existing = mappingRepo.findByChildArticle(childArt);
		if (existing != null) {
			mappingRepo.delete(existing);
			mappingRepo.flush();
		}

		// If parentArticleId > 0 and not self-referencing, create new mapping
		if (parentArticleId > 0 && parentArticleId != childArticleId) {
			article parentArt = artRepo.getOne(parentArticleId);
			parent_article_mapping mapping = new parent_article_mapping(parentArt, childArt);
			mappingRepo.save(mapping);
			mappingRepo.flush();
			result.put("status", "saved");
		} else {
			result.put("status", "cleared");
		}

		return result;
	}

}
