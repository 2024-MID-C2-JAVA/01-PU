package co.com.sofka.banktransaction.model.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "bank_transaction")
@NoArgsConstructor
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "origin_account_id", nullable = false)
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "type_transaction_id", nullable = false)
    private TypeTransaction typeTransaction;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
