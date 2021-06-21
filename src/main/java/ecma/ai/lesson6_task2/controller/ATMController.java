package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.Service.ATMservice;
import ecma.ai.lesson6_task2.Service.BankService;
import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.payload.AtmAddDTO;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class ATMController {

    @Autowired
    ATMservice atMservice;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<ATM> list = atMservice.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id ){
        ApiResponse id1 = atMservice.getBanknotes(id);
        return ResponseEntity.status(id1.isSuccess()?202:409).body(id1);
    }

    @PostMapping
    public HttpEntity<?> cardAddToClient(@RequestBody AtmAddDTO atmAddDTO) {
        ApiResponse apiResponse = atMservice.addAtm(atmAddDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody AtmAddDTO atmAddDTO){
        ApiResponse apiResponse = atMservice.editAtm(atmAddDTO,id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = atMservice.delete(id);
        return ResponseEntity.status(delete.isSuccess()?202:409).body(delete);
    }

}
