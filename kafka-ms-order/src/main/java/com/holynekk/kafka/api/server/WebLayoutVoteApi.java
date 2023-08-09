package com.holynekk.kafka.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.holynekk.kafka.api.request.WebLayoutVoteRequest;
import com.holynekk.kafka.command.service.WebLayoutVoteService;

@RestController
@RequestMapping("/api/web/layout/vote")
public class WebLayoutVoteApi {

	@Autowired
	private WebLayoutVoteService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createPrimary(@RequestBody WebLayoutVoteRequest request) {
		service.createLayoutVote(request);

		return ResponseEntity.status(HttpStatus.CREATED).body("Layout vote created with layout : " + request.getLayout()
				+ ", by username : " + request.getUsername());
	}

}
