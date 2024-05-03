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

    @ExceptionHandler(PasswordFormatInvalidException.class)
    protected ResponseEntity<ErrorResponseDTO> PasswordFormatInvalidException(final PasswordFormatInvalidException e) {
        log.error("PasswordFormatInvalidException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.PASSWORD_FORMAT_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.PASSWORD_FORMAT_INVALID));
    }

    @ExceptionHandler(NewPasswordSameAsOldException.class)
    protected ResponseEntity<ErrorResponseDTO> NewPasswordSameAsOldException(final NewPasswordSameAsOldException e) {
        log.error("NewPasswordSameAsOldException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.NEW_PASSWORD_SAME_AS_OLD.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.NEW_PASSWORD_SAME_AS_OLD));
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> UserNotFoundException(final UserNotFoundException e) {
        log.error("UserNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USER_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USER_NOT_FOUND));
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> DiaryNotFoundException(final DiaryNotFoundException e) {
        log.error("DiaryNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DIARY_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DIARY_NOT_FOUND));
    }
}
