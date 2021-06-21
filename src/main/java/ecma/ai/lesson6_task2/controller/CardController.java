package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.Service.CardService;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.CardDto;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import ecma.ai.lesson6_task2.payload.ClientDto;
import ecma.ai.lesson6_task2.repository.CardRepository;
import ecma.ai.lesson6_task2.repository.RoleRepository;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<Card> list = cardService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id ){
        ApiResponse id1 = cardService.getId(id);
        return ResponseEntity.status(id1.isSuccess()?202:409).body(id1);
    }

    @PostMapping
    public HttpEntity<?> cardAddToClient(@RequestBody CardDto cardDto) {
//
////        List<Card> all = cardRepository.findAll();
//        List<Card> allByActiveIsTrue = cardRepository.findAllByActiveIsFalse();
//
//        Card card = allByActiveIsTrue.get(0);
//
//        User user = new User();
//        user.setFullName(clientDto.getFullName());
//        user.setRole(roleRepository.getOne(3));
//
//        userRepository.save(user);
//        card.setUser(user);
//        card.setActive(true);
//        card.setFullName(clientDto.getFullName());
//        cardRepository.save(card);
        ApiResponse apiResponse = cardService.addCard(cardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody CardDto cardDto){
        ApiResponse apiResponse = cardService.editCard(id, cardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = cardService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?202:409).body(delete);
    }

}
