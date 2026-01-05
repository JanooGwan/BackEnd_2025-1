package com.example.bcsd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ARTICLE_NOT_FOUND(404, "해당 게시글을 찾을 수 없습니다."),
    BOARD_NOT_FOUND(404, "해당 게시판을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(404, "해당 회원을 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(409, "해당 이메일은 이미 사용중 입니다."),
    INVALID_MEMBER_REFERENCE(400, "해당 회원은 존재하지 않습니다."),
    INVALID_BOARD_REFERENCE(400, "해당 게시판은 존재하지 않습니다."),
    NULL_VALUE_NOT_ALLOWED(400, "요청에 null 값이 포함되어 있습니다."),
    CANNOT_DELETE_MEMBER_WITH_ARTICLES(400, "해당 사용자가 작성한 게시글이 있어 삭제할 수 없습니다."),
    CANNOT_DELETE_BOARD_WITH_ARTICLES(400, "해당 게시판에 작성된 게시글이 있어 삭제할 수 없습니다."),
    INVALID_CREDENTIALS(401, "정보(이메일, 비밀번호)가 일치하지 않습니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다.");


    private final int status;
    private final String message;
}
