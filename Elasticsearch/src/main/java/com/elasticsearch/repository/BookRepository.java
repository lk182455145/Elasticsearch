package com.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.elasticsearch.entity.Book;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

}
