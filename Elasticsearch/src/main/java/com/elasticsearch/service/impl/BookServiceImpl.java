package com.elasticsearch.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elasticsearch.config.MyResultMapper;
import com.elasticsearch.entity.Book;
import com.elasticsearch.repository.BookRepository;
import com.elasticsearch.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private MyResultMapper myResultMapper;

	@Override
	public Book add(Book book) {
		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book update(String id, Book book) {
		Optional<Book> opb = bookRepository.findById(id);
		if(opb.isPresent()) {
			Book oldBook = opb.get();
			oldBook.setContent(book.getContent());
			oldBook.setTitle(book.getTitle());
			return bookRepository.save(oldBook);
		}
		return null;
	}

	@Override
	public void delete(String id) {
		bookRepository.deleteById(id);
	}

	@Override
	public Page<Book> page(Pageable pageable, String keyword) {
		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		//分页
		searchQuery.withPageable(pageable);
		
		//关键字查询
		if(StringUtils.isNotBlank(keyword)) {
			searchQuery.withQuery(QueryBuilders.queryStringQuery(keyword));
			
//			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//			boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword));
//			boolQueryBuilder.should(QueryBuilders.matchQuery("content", keyword));
//			searchQuery.withQuery(boolQueryBuilder);
		}
		
		//高亮显示
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("*");
		highlightBuilder.preTags("<span>");
		highlightBuilder.postTags("</span>");
		
		searchQuery.withHighlightBuilder(highlightBuilder);
		
		return elasticsearchTemplate.queryForPage(searchQuery.build(), Book.class, myResultMapper);
	}
}
