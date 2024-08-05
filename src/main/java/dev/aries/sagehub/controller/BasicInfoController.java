package dev.aries.sagehub.controller;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.service.basicinfoservice.BasicInfoInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User")
public class BasicInfoController {
	private final BasicInfoInterface basicInfoService;

	@GetMapping
	@Operation(summary = "Get basic info",
			description = "Get basic info of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Basic info retrieved successfully",
			content = {@Content(schema = @Schema(implementation = BasicInfoResponse.class))})
	public ResponseEntity<BasicInfoResponse> getBasicInfo(@PathVariable("user-id") Long id) {
		return ResponseEntity.ok(basicInfoService.getBasicInfo(id));
	}

	@PutMapping
	@Operation(summary = "Update basic info",
			description = "Update basic info of a user",
			security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponse(responseCode = "200", description = "Basic info updated successfully",
			content = {@Content(schema = @Schema(implementation = BasicInfoResponse.class))})
	public ResponseEntity<BasicInfoResponse> updateBasicInfo(
			@PathVariable("user-id") Long id, @RequestBody @Valid BasicInfoRequest request) {
		return ResponseEntity.ok(basicInfoService.updateBasicInfo(id, request));
	}
}
