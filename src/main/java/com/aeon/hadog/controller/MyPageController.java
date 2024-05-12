import com.aeon.hadog.base.dto.MyPageUserDTO;
import com.aeon.hadog.base.dto.PetDTO;
import com.aeon.hadog.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    // 내 정보 조회
    @GetMapping("/myInfo")
    public ResponseEntity<MyPageUserDTO> getMyInfo(Authentication authentication) {
        try {
            String userId = (String) authentication.getPrincipal();
            MyPageUserDTO userDTO = myPageService.getMyInfo(userId);
            return ResponseEntity.ok().body(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 반려견 정보 조회
    @GetMapping("/petInfo")
    public ResponseEntity<PetDTO> getPetInfo(Authentication authentication) {
        try {
            String userId = (String) authentication.getPrincipal();
            PetDTO petDTO = myPageService.getPetInfo(userId);
            return ResponseEntity.ok().body(petDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    public ResponseEntity<String> updateNickname(@RequestParam String newNickname, Authentication authentication) {
        try {
            String userId = (String) authentication.getPrincipal();
            myPageService.updateNickname(userId, newNickname);
            return ResponseEntity.ok().body("Nickname updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
