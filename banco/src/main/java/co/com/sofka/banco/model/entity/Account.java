package co.com.sofka.banco.model.entity;


import co.com.sofka.banco.model.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private String pin;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
