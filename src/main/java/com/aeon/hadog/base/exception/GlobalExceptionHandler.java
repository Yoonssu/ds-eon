package com.aeon.hadog.base.exception;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateIdException.class)
    protected ResponseEntity<ErrorResponseDTO> RegisterIdException(final DuplicateIdException e) {
        log.error("DuplicateIdExceptioin : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DUPLICATE_ID_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_ID_REQUEST));
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    protected ResponseEntity<ErrorResponseDTO> RegisterNicknameException(final DuplicateNicknameException e) {
        log.error("DuplicateNicknameExceptioin : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DUPLICATE_NICKNAME_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_NICKNAME_REQUEST));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<ErrorResponseDTO> RegisterEmailException(final DuplicateEmailException e) {
        log.error("DuplicateEmailExceptioin : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DUPLICATE_EMAIL_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_EMAIL_REQUEST));
    }

    @ExceptionHandler(InvalidLoginException.class)
    protected ResponseEntity<ErrorResponseDTO> AuthenticationFailedException(final InvalidLoginException e) {
        log.error("InvalidLoginException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INVALID_LOGIN.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.INVALID_LOGIN));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    protected ResponseEntity<ErrorResponseDTO> PasswordIncorrectException(final PasswordMismatchException e) {
        log.error("PasswordMismatchException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.PASSWORD_MISMATCH.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.PASSWORD_MISMATCH));
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> UserNotFoundException(final UserNotFoundException e) {
        log.error("UserNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USER_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USER_NOT_FOUND));
    }
}
