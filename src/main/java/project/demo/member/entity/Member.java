package project.demo.member.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

    public enum role {
        ROLE_ADMIN,
        ROLE_USER,
        ROLE_ARTIST
    }

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private long id;


    @Column(unique = true)
    private String username;
    
    @Column(unique = true, nullable = false, length = 30)
    private String nickname;

    private String password;

    private boolean enabled;

    private String emailToken;
    private LocalDateTime emailTokenExpiry;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }
    @Column(columnDefinition = "TEXT")
    private String RefreshToken;
}
