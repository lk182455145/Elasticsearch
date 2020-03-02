package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.entity.Book;
import com.elasticsearch.service.BookService;

@RestController
@RequestMapping("book")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@GetMapping
	public Page<Book> page(@PageableDefault Pageable pageable, String keyword) {
		return bookService.page(pageable, keyword);
	}
	
	@PostMapping
	public Book add(@RequestBody Book book) {
		return bookService.add(book);
	}
	
	@PutMapping("{id}")
	public Book update(@PathVariable("id")String id, Book book) {
		return null;
	}
	
	@DeleteMapping("{id}")
	public void del(@PathVariable("id")String id) {
		bookService.delete(id);
	}
}
