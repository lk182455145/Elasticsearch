package com.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.elasticsearch.entity.Book;

public interface BookService {

	Book add(Book book);
	
	Book update(String id, Book book);
	
	void delete(String id);
	
	Page<Book> page(Pageable pageable, String keyword);
}
