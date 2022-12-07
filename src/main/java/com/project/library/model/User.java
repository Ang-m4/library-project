package com.project.library.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "user_name")
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "user_lastname")
    private String lastname;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "user_nickname")
    private String nickname;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "user_password")
    private String password;

    @Column(name = "user_blocked")
    private boolean blocked;

    @Column(name = "user_role")
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER|ROLE_LIBRARIAN)$")
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

}
