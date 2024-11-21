package com.enigma.wmb_api.entity;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.constant.MenuCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@SQLDelete(sql = "UPDATE m_menu SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
@Builder
@Entity
@Table(name = Constant.MENU_TABLE)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "bigint check (price > 0)")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private MenuCategory category;
}
