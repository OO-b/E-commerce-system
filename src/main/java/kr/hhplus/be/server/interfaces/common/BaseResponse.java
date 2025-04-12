package kr.hhplus.be.server.interfaces.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "기본 응답 DTO", requiredProperties = {"code", "message", "dateTime"})
public class BaseResponse<T> {

    /**
     * 응답 코드 (0:성공, -1:실패)
     */
    @Schema(description = "응답 코드(0:성공, 그 외: 실패)")
    public String code = "0";

    /**
     * 응답 메시지
     */
    @Schema(description = "응답 메시지")
    public String message;

    /**
     * 응답일시
     */
    @Schema(description = "응답일시", example = "9999-12-31 23:59:59")
    public LocalDateTime dateTime = LocalDateTime.now();

    /**
     * 응답데이터
     */
    @Schema(description = "응답데이터")
    public T data;

    /**
     * 응답데이터 없을 경우 기본응답 정보 객체 생성
     *
     * @param code 응답코드
     * @param message 응답메시지
     * @return {@link BaseResponse<T>}
     */
    public static <T> BaseResponse<T> of(String code, String message) {
        BaseResponse<T> dto = new BaseResponse<>();
        dto.code = code;
        dto.message = message;
        return dto;
    }

    /**
     * 응답데이터 있을 경우 기본응답 정보 객체 생성
     *
     * @param code 응답코드
     * @param message 응답메시지
     * @param data 응답데이터
     * @return {@link BaseResponse<T>}
     */
    public static <T> BaseResponse<T> of(String code, String message, T data) {
        BaseResponse<T> dto = new BaseResponse<>();
        dto.code = code;
        dto.message = message;
        dto.data = data;
        return dto;
    }
}
