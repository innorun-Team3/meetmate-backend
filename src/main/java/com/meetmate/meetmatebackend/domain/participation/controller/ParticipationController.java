package com.meetmate.meetmatebackend.domain.participation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ParticipationController {

    public String test() {
        log.info("test");
        return  "ok";
    }
}
