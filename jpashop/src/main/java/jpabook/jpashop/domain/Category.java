package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    //실무에서는 다대다 관계는 하면 이후에 변경이 불가능하기 때문에 실무에서는 하지 않는다.
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "categoty_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    // Self MappedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // Self MappedBy
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
