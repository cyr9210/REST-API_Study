package me.bong.springrestapi.account;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static me.bong.springrestapi.account.AccountRole.ADMIN;
import static me.bong.springrestapi.account.AccountRole.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Account result = accountService.saveAccount(account);

        // when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword()));
    }

    @Test
    public void findByUserNameFail() {
        String username = "random@email.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        accountRepository.findByEmail(username);
    }
}