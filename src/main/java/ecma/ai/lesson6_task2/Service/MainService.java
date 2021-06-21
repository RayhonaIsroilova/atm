package ecma.ai.lesson6_task2.Service;

import ecma.ai.lesson6_task2.component.MailSender;
import ecma.ai.lesson6_task2.entity.ATM;
import ecma.ai.lesson6_task2.entity.ATMHistory;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.Role;
import ecma.ai.lesson6_task2.entity.enums.ATMOperationType;
import ecma.ai.lesson6_task2.entity.enums.RoleName;
import ecma.ai.lesson6_task2.entity.enums.USDBankNoteType;
import ecma.ai.lesson6_task2.entity.enums.UZSBankNoteType;
import ecma.ai.lesson6_task2.payload.ATMWithdrawalDTO;
import ecma.ai.lesson6_task2.payload.PulKiritDto;
import ecma.ai.lesson6_task2.payload.types.ApiResponse;
import ecma.ai.lesson6_task2.repository.ATMHistoryRepository;
import ecma.ai.lesson6_task2.repository.ATMRepository;
import ecma.ai.lesson6_task2.repository.CardRepository;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ATMRepository atmRepository;
    @Autowired
    ATMHistoryRepository atmHistoryRepository;
    @Autowired
    MailSender mailSender;
    @Autowired
    UserRepository userRepository;


    // very very LOGICAL!!!!
    // tushungan uchun...
    public ApiResponse withdrawal(@Valid ATMWithdrawalDTO drawal) throws MessagingException {

        ATM atm = drawal.getAtm();
        Card card = drawal.getCard();
        ATMHistory atmHistory = new ATMHistory();
        if (card.isActive() && card.isBlocked()) {
            return new ApiResponse("hisobingiz muzlatilgan", false);
        }
        if (!atm.getCardTypes().equals(card.getCardType())) {
            return new ApiResponse("failed. card types not equals", true);
        }

        if (atm.getMaxWithdrawal() < drawal.getAmountMoneyExit()) {
            return new ApiResponse("ko'p pul yechvording", false);
        } else if (card.getBalance() < drawal.getAmountMoneyExit()) {
            return new ApiResponse("hisobizda money netu", false);
        } else if (atm.getBalance() < drawal.getAmountMoneyExit()) {
            return new ApiResponse("hisobmizda money netu, bowqa ATM ga boring", false);
        }
        atmHistory.setCard(card);
        atmHistory.setAtm(atm);
        atmHistory.setDate(new Date(System.currentTimeMillis()));
        atmHistory.setOperationType(ATMOperationType.WITHDRAWAL);
        double amountMoneyExit = drawal.getAmountMoneyExit();
        switch (card.getCardType()) {
            case HUMO:
            case UZCARD: {
                Map<UZSBankNoteType, Integer> newMap = new HashMap<>();
                for (Map.Entry<UZSBankNoteType, Integer> uzsBankNote : atm.getBanknoteCountUZS().entrySet()) {
                    int kupyura = 0;
                    if (amountMoneyExit >= uzsBankNote.getKey().getValue() && uzsBankNote.getValue() > 0) {
                        kupyura += (int) amountMoneyExit / uzsBankNote.getKey().getValue();

                        if (kupyura >= uzsBankNote.getValue()) {
                            kupyura = uzsBankNote.getValue();
                        }
                    }
                    if (kupyura != 0) {
                        newMap.put(uzsBankNote.getKey(), kupyura);
                        amountMoneyExit -= kupyura * uzsBankNote.getValue();
                        uzsBankNote.setValue(uzsBankNote.getValue() - kupyura);
                    }

                }
                if (amountMoneyExit != 0.0) {
                    return new ApiResponse("bizda bunday kupyura yo'q!", false);
                }
                atmHistory.setBanknoteCountUZS(newMap);
                break;
            }
            case VISA: {
                Map<USDBankNoteType, Integer> newMapUSD = new HashMap<>();
                for (Map.Entry<USDBankNoteType, Integer> uzsBankNote : atm.getBanknoteCountUSD().entrySet()) {
                    int kupyura = 0;
                    if (amountMoneyExit >= uzsBankNote.getKey().getValue() && uzsBankNote.getValue() > 0) {
                        kupyura += (int) amountMoneyExit / uzsBankNote.getKey().getValue();

                        if (kupyura >= uzsBankNote.getValue()) {
                            kupyura = uzsBankNote.getValue();
                        }
                    }
                    if (kupyura != 0) {
                        newMapUSD.put(uzsBankNote.getKey(), kupyura);
                        amountMoneyExit -= kupyura * uzsBankNote.getValue();
                        uzsBankNote.setValue(uzsBankNote.getValue() - kupyura);
                    }
                }
                if (amountMoneyExit != 0.0) {
                    return new ApiResponse("bizda bunday kupyura yo'q!", false);
                }
                atmHistory.setBanknoteCountUSD(newMapUSD);
                break;
            }
        }
        if (card.getBank().equals(atm.getBank())) {
            card.setBalance(card.getBalance() - drawal.getAmountMoneyExit());
            atmHistory.setOperationAmount(card.getBalance() - drawal.getAmountMoneyExit());
        } else {
            card.setBalance(card.getBalance() - drawal.getAmountMoneyExit() * (1 + atm.getComission() / 100));
            atmHistory.setOperationAmount(card.getBalance() - drawal.getAmountMoneyExit() * (1 + atm.getComission() / 100));
        }

        atm.setBalance(atm.getBalance() - drawal.getAmountMoneyExit());

        atmRepository.save(atm);
        cardRepository.save(card);
        atmHistoryRepository.save(atmHistory);

        if (atm.getBalance() <= atm.getAlertAmount()) {
            mailSender.mailTextAlertAmount("qwerty77@gmail.com",atm.getAddress(),atm.getId());
        }
        return new ApiResponse("success", true);
    }

    //card ni naqt pul bn to'ldirish. atm ga naqt pul kupyuralar solinadi !!!
    public ApiResponse cardniToldirish(PulKiritDto dto) {

        ATM atm = dto.getAtm();
        Card card = dto.getCard();
        ATMHistory atmHistory = new ATMHistory();
        if (card.isActive() && card.isBlocked()) {
            return new ApiResponse("hisobingiz muzlatilgan", false);
        }
        if (!atm.getCardTypes().equals(card.getCardType()) && !atm.getCardTypes().equals(dto.getCardType())) {
            return new ApiResponse("failed types not equals", true);
        }

        atmHistory.setCard(card);
        atmHistory.setAtm(atm);
        atmHistory.setDate(new Date(System.currentTimeMillis()));
        atmHistory.setOperationType(ATMOperationType.PULKIRITAWAL);
        double balance = 0;
        switch (atm.getCardTypes()) {
            case HUMO:
            case UZCARD:
                Map<UZSBankNoteType, Integer> banknoteCountUZS = dto.getBanknoteCountUZS();
                for (Map.Entry<UZSBankNoteType, Integer> uzsB : banknoteCountUZS.entrySet()) {
                    balance += uzsB.getValue() * uzsB.getKey().getValue();
                    for (Map.Entry<UZSBankNoteType, Integer> uzsBankNoteTypeIntegerEntry : atm.getBanknoteCountUZS().entrySet()) {
                        if (uzsBankNoteTypeIntegerEntry.getKey().equals(uzsB.getKey())) {
                            uzsBankNoteTypeIntegerEntry.setValue(uzsBankNoteTypeIntegerEntry.getValue() + uzsB.getValue());
                            break;
                        }
                    }
                    boolean bool = true;
                    for (Map.Entry<UZSBankNoteType, Integer> uzsBankNoteATM : atm.getBanknoteCountUZS().entrySet()) {
                        if (uzsB.getKey().equals(uzsBankNoteATM.getKey())) {
                            bool = false;
                            break;
                        }
                    }
                    if (bool) {
                        return new ApiResponse("bizda bunday kupyura un quticha yoq!", false);
                    }
                }
                atmHistory.setBanknoteCountUZS(banknoteCountUZS);
                break;
            case VISA:
                Map<USDBankNoteType, Integer> banknoteCountUSD = dto.getBanknoteCountUSD();
                for (Map.Entry<USDBankNoteType, Integer> uzsB : banknoteCountUSD.entrySet()) {
                    balance += uzsB.getValue() * uzsB.getKey().getValue();
                    for (Map.Entry<USDBankNoteType, Integer> usdBankNoteTypeIntegerEntry : atm.getBanknoteCountUSD().entrySet()) {
                        if (usdBankNoteTypeIntegerEntry.getKey().equals(uzsB.getKey())) {
                            usdBankNoteTypeIntegerEntry.setValue(usdBankNoteTypeIntegerEntry.getValue() + uzsB.getValue());
                            break;
                        }
                    }

                    boolean bool = true;
                    for (Map.Entry<USDBankNoteType, Integer> uzsBankNoteATM : atm.getBanknoteCountUSD().entrySet()) {
                        if (uzsB.getKey().equals(uzsBankNoteATM.getKey())) {
                            bool = false;
                            break;
                        }
                    }
                    if (bool) {
                        return new ApiResponse("bizda bunday kupyura un quticha yoq!", false);
                    }
                }
                atmHistory.setBanknoteCountUSD(banknoteCountUSD);
                break;

            default:
                return new ApiResponse("EURO bn iwlamaymiz", false);
        }

        atm.setBalance(atm.getBalance() + balance);
        if (card.getBank().equals(atm.getBank())) {
            atmHistory.setOperationAmount(balance);
            card.setBalance(card.getBalance() + balance);
        } else {
            atmHistory.setOperationAmount(balance * (1 + atm.getComission() / 100));
            card.setBalance(card.getBalance() + balance * (1 - atm.getComission() / 100));
        }

        atmRepository.save(atm);
        cardRepository.save(card);
        atmHistoryRepository.save(atmHistory);
        return new ApiResponse("success", true);
    }


}






