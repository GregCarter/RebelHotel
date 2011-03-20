package edu.unlv.cs.rebelhotel.domain;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.enums.UserGroup;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Configurable("userAccount")
@RooJavaBean
@RooToString
@RooEntity(finders = { "findUserAccountsByUserId" })
public class UserAccount {

    @NotNull
    @Column(unique = true)
    private String userId;

    private transient MessageDigestPasswordEncoder passwordEncoder;

    @NotNull
    private String password;

    @NotNull
    @Column(unique=true)
    private String email = "default@email.com";
    
    @Enumerated(EnumType.STRING)
    private UserGroup userGroup;

    private Boolean enabled = Boolean.TRUE;

    public void setPassword(String password) {
        String encoded = passwordEncoder.encodePassword(password, null);
        this.password = encoded;
    }

    public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserId: ").append(getUserId()).append(", ");
        sb.append("UserGroup: ").append(getUserGroup());
        return sb.toString();
    }
}
