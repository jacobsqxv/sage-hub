package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.service.basicinfoservice.BasicInfoInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/{user-id}/basic-info")
public class BasicInfoController {
	private final BasicInfoInterface basicInfoService;

	@GetMapping
	public ResponseEntity<BasicInfoResponse> getBasicInfo(@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(this.basicInfoService.getBasicInfo(id));
	}

	@PutMapping
	public ResponseEntity<BasicInfoResponse> updateBasicInfo(
			@PathVariable("user-id") Long id, @RequestBody @Valid BasicInfoRequest request) {
		return ResponseEntity.ok(this.basicInfoService.updateBasicInfo(id, request));
	}
}
