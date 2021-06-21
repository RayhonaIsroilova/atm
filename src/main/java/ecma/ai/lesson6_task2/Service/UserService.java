package ecma.ai.lesson6_task2.Service;

import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.UserDto;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getList(){
        return userRepository.findAll();
    }

    public ApiResponse getId(Integer id){
        Optional<User> byId = userRepository.findById(id);
        return byId.map(user -> new ApiResponse("mana ol userini", true, user)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addUser(UserDto userDto){
        Optional<User> byRole = userRepository.findByRole(userDto.getRole().getRoleName());
        if (byRole.isPresent())
            return new ApiResponse("bu rolni bu odamga bergansan",false);
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return new ApiResponse("Added success",true,user);
    }

    public ApiResponse editUser(Integer id,UserDto userDto) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Optional<User> byRole = userRepository.findByRole(userDto.getRole().getRoleName());
        if (byRole.isPresent())
            return new ApiResponse("bu rolni bu odamga bergansan", false);
        User user = byId.get();
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return new ApiResponse("Edited success", true, user);
    }

    public ApiResponse delete(Integer id){
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        userRepository.deleteById(id);
        return new ApiResponse("Delete success",true);
    }

}
