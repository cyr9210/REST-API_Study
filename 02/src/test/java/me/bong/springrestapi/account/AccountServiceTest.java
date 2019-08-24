package me.bong.springrestapi.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static me.bong.springrestapi.account.AccountRole.ADMIN;
import static me.bong.springrestapi.account.AccountRole.USER;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void loadUserByUsername() {
        // given
        Set<AccountRole> accountRoleSet = new HashSet<>();
        accountRoleSet.add(ADMIN);
        accountRoleSet.add(USER);

        String username = "bong@email.com";
        String password = "bong";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(accountRoleSet)
                .build();

        accountRepository.save(account);

        // when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }
}