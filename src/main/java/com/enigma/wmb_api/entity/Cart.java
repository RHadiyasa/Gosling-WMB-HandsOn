package com.enigma.wmb_api.entity;

import com.enigma.wmb_api.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.UUID;

@SQLDelete(sql = ("UPDATE " + Constant.CART_TABLE + " SET is_active = false WHERE id=?"))
@Where(clause = "is_active=true")
@Entity
@Table(name = Constant.CART_TABLE)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;
}
