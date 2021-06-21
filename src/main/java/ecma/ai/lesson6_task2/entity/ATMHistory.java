package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ATMHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ATM atm;

    @Column
    private Date date;

    @ManyToOne
    private Card card;

    @Column
    private double operationAmount;

    @ElementCollection
    private Map<UZSBankNoteType, Integer> banknoteCountUZS; //null

    @ElementCollection
    private Map<USDBankNoteType, Integer> banknoteCountUSD;

    @Enumerated(value = EnumType.STRING)
    private ATMOperationType operationType;

}
