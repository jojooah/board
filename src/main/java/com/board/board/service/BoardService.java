package com.board.board.service;

import com.board.board.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BoardRepository boardRepository;
}
