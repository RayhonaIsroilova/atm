package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.Data;

import java.util.Map;

@Data
public class PulKiritDto {
    private Card card;
    private ATM atm;

    private Map<UZSBankNoteType, Integer> banknoteCountUZS;

    private Map<USDBankNoteType, Integer> banknoteCountUSD;
    private CardType cardType;
}

