package ecma.ai.lesson6_task2.Service;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import ecma.ai.lesson6_task2.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    public List<Bank> getList(){
        return bankRepository.findAll();
    }

    public ApiResponse getId(Integer id){
        Optional<Bank> byId = bankRepository.findById(id);
        return byId.map(bank -> new ApiResponse("Take it", true, bank)).orElseGet(() -> new ApiResponse("This id not found", false));
    }

    public ApiResponse addBank(Bank bank){
        Bank bank1 = new Bank();
        bank1.setName(bank.getName());
        bankRepository.save(bank1);
        return new ApiResponse("Added success",true);
    }

    public ApiResponse editBank(Integer id, Bank bank){
        Optional<Bank> byId = bankRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        Bank bank1 = byId.get();
        bank1.setName(bank.getName());
        bankRepository.save(bank1);
        return new ApiResponse("Edited success",true);
    }


    public ApiResponse delete(Integer id){
        Optional<Bank> byId = bankRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found",false);
        bankRepository.deleteById(id);
        return new ApiResponse("Delete success",true);
    }
}
