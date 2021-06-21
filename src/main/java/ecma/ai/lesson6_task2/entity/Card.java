package ecma.ai.lesson6_task2.entity;

import ecma.ai.lesson6_task2.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Size(min = 16, max = 16)
    private String number;

    @Column
    @Size(min= 4,max=4)
    private String pinCode;

    @Column
    @Size(min = 3, max = 3)
    private String cvv;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private User user;

    private String fullName;

    private Date expireDate;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean active = false; // xodim kimgadr cardni bermagancha active bo'lmaydi
    private boolean blocked = false; //blok
    private double balance;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(user.getRole());
    }

    @Override
    public String getPassword() {
        return this.pinCode;
    }

    @Override
    public String getUsername() {
        return this.number;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }


    public Card(Integer id, String number, String cvv, Bank bank, String fullName, String pinCode, Date expireDate, CardType cardType, boolean active, boolean blocked, double balance) {
        this.id = id;
        this.number = number;
        this.cvv = cvv;
        this.bank = bank;
        this.fullName = fullName;
        this.pinCode = pinCode;
        this.expireDate = expireDate;
        this.cardType = cardType;
        this.active = active;
        this.blocked = blocked;
        this.balance = balance;
    }
}