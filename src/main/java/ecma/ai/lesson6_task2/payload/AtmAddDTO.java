package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class AtmAddDTO {
    @NotNull
    private CardType cardTypes;

    private Map<UZSBankNoteType, Integer> banknoteCountUZS; //null

    private Map<USDBankNoteType, Integer> banknoteCountUSD;

    @NotNull
    private double maxWithdrawal;

    @NotNull
    private double alertAmount;

    @NotNull
    private String address;

    @NotNull
    private Integer bankID;
    private int comission;
    private User user;
}
