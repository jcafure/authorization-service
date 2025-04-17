package br.com.caridade.authorization.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user_role_group")
public class UserRoleGroup extends BaseEntity{

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @EqualsAndHashCode.Include
    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @EqualsAndHashCode.Include
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
}
