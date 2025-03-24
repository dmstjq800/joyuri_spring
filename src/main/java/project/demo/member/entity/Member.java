package project.demo.member.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.processing.Generated;

import java.util.Collection;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 여기서는 간단하게 null 처리. 필요에 따라 권한 설정
    }

    @Column(columnDefinition = "TEXT")
    private String RefreshToken;
}
