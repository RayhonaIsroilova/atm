package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private CardType cardTypes;

    @ManyToOne
    private Bank bank;

    @ElementCollection
    private Map<UZSBankNoteType, Integer> banknoteCountUZS; //null

    @ElementCollection
    private Map<USDBankNoteType, Integer> banknoteCountUSD;

    private double balance;
    private double maxWithdrawal;
    private double alertAmount;
    private String address;
    private int comission;  //(0-100)/100   foizda bo'ladii
}


