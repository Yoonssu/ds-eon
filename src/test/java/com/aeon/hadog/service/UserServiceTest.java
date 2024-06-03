package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.user.JoinRequestDTO;
import com.aeon.hadog.base.dto.user.LoginRequestDTO;
import com.aeon.hadog.base.exception.*;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("회원가입 테스트")
    class signupTest{
        @Test
        @DisplayName("회원가입 성공")
        void signup() {
            // given
            JoinRequestDTO dto = JoinRequestDTO.builder()
                    .name("홍길동")
                    .id("user01")
                    .password("user012@")
                    .nickname("길동이01")
                    .email("user01@gmail.com")
                    .build();

            // when
            Long userId = userService.signup(dto);

            // then
            assertNotNull(userId);
            User findUser= userRepository.findByUserId(userId).orElseThrow();
            assertEquals(findUser.getUserId(), userId);
        }

        @Test
        @DisplayName("회원가입 실패 - 아이디 중복")
        void signup_existId() {
            // given
            JoinRequestDTO dto = JoinRequestDTO.builder()
                    .name("홍길동")
                    .id("user3")
                    .password("user012@")
                    .nickname("길동이01")
                    .email("user01@gmail.com")
                    .build();

            // when
            Exception exception = assertThrows(DuplicateIdException.class, () -> {
                userService.signup(dto);
            });

            // then
            assertEquals(ErrorCode.DUPLICATE_ID_REQUEST, ((DuplicateIdException) exception).getErrorCode());

        }

        @Test
        @DisplayName("회원가입 실패 - 닉네임 중복")
        void signup_existNickname() {
            // given
            JoinRequestDTO dto = JoinRequestDTO.builder()
                    .name("홍길동")
                    .id("user01")
                    .password("user012@")
                    .nickname("hello4")
                    .email("user01@gmail.com")
                    .build();

            // when
            Exception exception = assertThrows(DuplicateNicknameException.class, () -> {
                userService.signup(dto);
            });

            // then
            assertEquals(ErrorCode.DUPLICATE_NICKNAME_REQUEST, ((DuplicateNicknameException) exception).getErrorCode());

        }

        @Test
        @DisplayName("회원가입 실패 - 이메일 중복")
        void signup_existEmail() {
            // given
            JoinRequestDTO dto = JoinRequestDTO.builder()
                    .name("홍길동")
                    .id("user01")
                    .password("user012@")
                    .nickname("길동이01")
                    .email("user3@gmail.com")
                    .build();

            // when
            Exception exception = assertThrows(DuplicateEmailException.class, () -> {
                userService.signup(dto);
            });

            // then
            assertEquals(ErrorCode.DUPLICATE_EMAIL_REQUEST, ((DuplicateEmailException) exception).getErrorCode());

        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class signinTest{
        @Test
        @DisplayName("로그인 성공")
        void signin() {
            // given
            String id = "user3";
            String password = "user333!";
            LoginRequestDTO dto = LoginRequestDTO.builder()
                    .id(id)
                    .password(password)
                    .build();

            // when
            String token = userService.signin(dto);

            // then
            assertNotNull(token);
        }
        @Test
        @DisplayName("로그인 실패")
        void signin_passwordError() {
            // given
            String id = "user3";
            String password = "user333@";
            LoginRequestDTO dto = LoginRequestDTO.builder()
                    .id(id)
                    .password(password)
                    .build();

            // when
            Exception exception = assertThrows(InvalidLoginException.class, () -> {
                userService.signin(dto);
            });

            // then
            assertEquals(ErrorCode.INVALID_LOGIN, ((InvalidLoginException) exception).getErrorCode());

        }
    }

    @Nested
    @DisplayName("아이디 중복 테스트")
    class checkIdTest{
        @Test
        @DisplayName("아이디 중복 테스트 - 참")
        void checkId_true() {
            // given
            String existingId = "user3";

            // when
            boolean result = userService.checkId(existingId);

            // then
            assertTrue(result);
        }
        @Test
        @DisplayName("아이디 중복 테스트 - 거짓")
        void checkId_false() {
            // given
            String existingId = "user9";

            // when
            boolean result = userService.checkId(existingId);

            // then
            assertFalse(result);
        }
    }
    @Nested
    @DisplayName("닉네임 중복 테스트")
    class checkNicknameTest{
        @Test
        @DisplayName("닉네임 중복 테스트 - 참")
        void checkNickName_true() {
            // given
            String existingNickname = "hello4";

            // when
            boolean result = userService.checkNickName(existingNickname);

            // then
            assertTrue(result);
        }
        @Test
        @DisplayName("닉네임 중복 테스트 - 거짓")
        void checkNickName_false() {
            // given
            String existingNickname = "hello456";

            // when
            boolean result = userService.checkNickName(existingNickname);

            // then
            assertFalse(result);
        }

    }
    @Nested
    @DisplayName("이메일 중복 테스트")
    class checkEmailTest{
        @Test
        @DisplayName("이메일 중복 테스트 - 참")
        void checkEmail_true() {
            // given
            String existingEmail = "user3@gmail.com";

            // when
            boolean result = userService.checkEmail(existingEmail);

            // then
            assertTrue(result);
        }    @Test
        @DisplayName("이메일 중복 테스트 - 거짓")
        void checkEmail_false() {
            // given
            String existingEmail = "user9@gmail.com";

            // when
            boolean result = userService.checkEmail(existingEmail);

            // then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 테스트")
    class modifyPasswordTest{
        @Test
        @DisplayName("비밀번호 변경 성공")
        void modifyPassword() {
            // given
            String id = "user3";
            String prevPassword = "user333!";
            String newPassword = "user333@";

            // when
            boolean result = userService.modifyPassword(id, prevPassword, newPassword);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("비밀번호 변경 실패 - 이전 비밀 번호 맞지 않음")
        void modifyPassword_passwordError() {
            // given
            String id = "user3";
            String prevPassword = "user000";
            String newPassword = "user333@";

            // when
            Exception exception = assertThrows(PasswordMismatchException.class, () -> {
                userService.modifyPassword(id, prevPassword, newPassword);
            });

            // then
            assertEquals(ErrorCode.PASSWORD_MISMATCH, ((PasswordMismatchException) exception).getErrorCode());

        }

        @Test
        @DisplayName("비밀번호 변경 실패 - 새 비밀번호 규격 에러")
        void modifyPassword_passwordFormat() {
            // given
            String id = "user3";
            String prevPassword = "user333!";
            String newPassword = "us";

            // when
            Exception exception = assertThrows(PasswordFormatInvalidException.class, () -> {
                userService.modifyPassword(id, prevPassword, newPassword);
            });

            // then
            assertEquals(ErrorCode.PASSWORD_FORMAT_INVALID, ((PasswordFormatInvalidException) exception).getErrorCode());

        }

        @Test
        @DisplayName("비밀번호 변경 실패 - 이전 비밀 번호와 새 비밀번호가 동일")
        void modifyPassword_same() {
            // given
            String id = "user3";
            String prevPassword = "user333!";
            String newPassword = "user333!";

            // when
            Exception exception = assertThrows(NewPasswordSameAsOldException.class, () -> {
                userService.modifyPassword(id, prevPassword, newPassword);
            });

            // then
            assertEquals(ErrorCode.NEW_PASSWORD_SAME_AS_OLD, ((NewPasswordSameAsOldException) exception).getErrorCode());

        }
    }

    @Nested
    @DisplayName("회원 탈퇴 테스트")
    class deleteUserTest{
        @Test
        @DisplayName("회원 탈퇴 성공")
        void deleteUser() {
            // given
            String id = "user3";
            String password = "user333!";

            // when
            boolean result = userService.deleteUser(id, password);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("회원 탈퇴 실패 - 비밀번호 틀림")
        void deleteUser_password() {
            // given
            String id = "user3";
            String password = "user333@";

            // when
            Exception exception = assertThrows(PasswordMismatchException.class, () -> {
                userService.deleteUser(id, password);
            });

            // then
            assertEquals(ErrorCode.PASSWORD_MISMATCH, ((PasswordMismatchException) exception).getErrorCode());
        }
    }

}