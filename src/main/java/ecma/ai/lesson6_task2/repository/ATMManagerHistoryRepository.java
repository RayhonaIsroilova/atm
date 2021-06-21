package ecma.ai.lesson6_task2.repository;

import ecma.ai.lesson6_task2.entity.ATMManagerOperationsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ATMManagerHistoryRepository extends JpaRepository<ATMManagerOperationsHistory,Integer>{
        List<ATMManagerHistoryRepository> findAllByAtmId(Integer atm_id);
}
