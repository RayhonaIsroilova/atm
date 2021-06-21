package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.Card;
import lombok.Data;

@Data
public class ATMWithdrawalDTO {
 private Card card;
 private ATM atm;

 private double amountMoneyExit;
}

