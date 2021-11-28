package com.board.board.service;

import com.board.board.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentRepository commentRepository;



}
