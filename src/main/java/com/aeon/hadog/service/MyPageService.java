import com.aeon.hadog.base.dto.MyPageUserDTO;
import com.aeon.hadog.base.dto.PetDTO;
import com.aeon.hadog.domain.User;
import com.aeon.hadog.domain.Pet;
import com.aeon.hadog.repository.UserRepository;
import com.aeon.hadog.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    // 내 정보 조회
    public MyPageUserDTO getMyInfo(Long userId) {
        // userId를 이용해서 사용자 정보를 가져옵니다.
        User user = userRepository.findById(userId);
        if (user != null) {
            return MyPageUserDTO.builder()
                    .name(user.getName())
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .build();
        } else {
            // 사용자가 없을 경우 처리
            return null;
        }
    }

    // 반려견 정보 조회
    public PetDTO getPetInfo(Long petId) {
        // petId를 이용해서 반려견 정보를 가져옵니다.
        Pet pet = petRepository.findById(petId);
        if (pet != null) {
            return PetDTO.builder()
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .sex(pet.getSex())
                    .neuter(pet.getNeuter())
                    .image(pet.getImage())
                    .age(pet.getAge())
                    .build();
        } else {
            // 반려견 정보가 없을 경우 처리
            return null;
        }
    }

    // 닉네임 변경
    public void updateNickname(Long userId, String newNickname) {
        // userId를 이용해서 사용자를 찾고, 닉네임을 변경합니다.
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setNickname(newNickname);
            userRepository.save(user);
        } else {
            // 사용자가 없을 경우 처리
            // 예외를 던지거나 적절한 처리를 수행해야 합니다.
        }
    }
}
