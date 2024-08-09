package mk.com.possystem.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private int totalPrice;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_username", referencedColumnName = "username")
    Employee employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<ItemInOrder> itemInOrders;

    private boolean isFinished;

    @JsonCreator
    public Order(@JsonProperty("employee") Employee employee){
        this.employee = employee;
        this.totalPrice = 0;
        this.orderDate = LocalDateTime.now();
        this.customer = null;
        this.itemInOrders = new ArrayList<>();
        this.isFinished = false;
    }

}
