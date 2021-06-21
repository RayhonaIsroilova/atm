package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.Service.ATMservice;
import ecma.ai.lesson6_task2.Service.MainService;
import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.payload.ATMWithdrawalDTO;
import ecma.ai.lesson6_task2.payload.AtmAddDTO;
import ecma.ai.lesson6_task2.payload.PulKiritDto;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    MainService mainService;

   @SneakyThrows
   @PostMapping("/withdrawal")
   public HttpEntity<?> withdrawal(@RequestBody ATMWithdrawalDTO atmWithdrawalDTO){
       ApiResponse withdrawal = mainService.withdrawal(atmWithdrawalDTO);
       return ResponseEntity.status(withdrawal.isSuccess()?202:409).body(withdrawal);

   }

    @PostMapping("/cardniToldirish")
    public HttpEntity<?> cardAddToClient(@RequestBody PulKiritDto pulKiritDto) {
        ApiResponse apiResponse = mainService.cardniToldirish(pulKiritDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }


}
