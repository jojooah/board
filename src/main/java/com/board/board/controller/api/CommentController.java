package com.board.board.controller.api;

import com.board.board.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController extends AbstractController{
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;

}
