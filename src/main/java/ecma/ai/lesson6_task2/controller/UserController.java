package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.Service.ATMservice;
import ecma.ai.lesson6_task2.Service.UserService;
import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.AtmAddDTO;
import ecma.ai.lesson6_task2.payload.UserDto;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<User> list = userService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id ){
        ApiResponse id1 = userService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?202:409).body(id1);
    }

    @PostMapping
    public HttpEntity<?> cardAddToClient(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.editUser(id,userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = userService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?202:409).body(delete);
    }

}
