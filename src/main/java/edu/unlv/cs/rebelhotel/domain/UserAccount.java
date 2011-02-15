package edu.unlv.cs.rebelhotel.domain;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.enums.UserGroup;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Transient;

@Configurable("userAccount")
@RooJavaBean
@RooToString
@RooEntity(finders = { "findUserAccountsByNSHE" })
public class UserAccount {

    private transient MessageDigestPasswordEncoder passwordEncoder;

    @NotNull
    @DecimalMin("1000000000")
    @Digits(integer = 10, fraction = 0)
    @Column(unique = true)
    private Long NSHE;

    @NotNull
    private String password;

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
}
