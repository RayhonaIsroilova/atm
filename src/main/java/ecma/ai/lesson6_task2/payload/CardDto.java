package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import lombok.Data;

import java.sql.Date;

@Data
public class CardDto {
    private String number;
    private String pinCode;
    private String cvv;
    private Bank bank;
    private User user;
    private String fullName;
    private Date expireDate;
    private CardType cardType;
    private boolean active = false; // xodim kimgadr cardni bermagancha active bo'lmaydi
    private boolean blocked = false;
}
