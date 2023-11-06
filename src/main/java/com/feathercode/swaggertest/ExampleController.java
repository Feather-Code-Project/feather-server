package com.feathercode.swaggertest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
public class ExampleController {

    @Operation(summary = "GET 테스트 메소드")
    @Parameter(name = "str", description = "출력할 문자열")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "클라이언트 에러1"),
            @ApiResponse(responseCode = "401", description = "클라이어트 에러2"),
            @ApiResponse(responseCode = "500", description = "서버 에러")})
    @GetMapping("/swagger-test")
    public String test(@RequestParam(name = "str") String str) {
        return str;
    }
}
