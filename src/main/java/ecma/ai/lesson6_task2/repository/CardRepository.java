package ecma.ai.lesson6_task2.repository;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findAllByActiveIsFalse();

    boolean findByBankAndUserAndCardType(Bank bank, User user, CardType cardType);

    Optional<Card> findByNumber(@Size(min = 16, max = 16) String number);
}
