package me.bong.springrestapi.config;

import me.bong.springrestapi.account.Account;
import me.bong.springrestapi.account.AccountRole;
import me.bong.springrestapi.account.AccountService;
import me.bong.springrestapi.common.BaseControllerTest;
import me.bong.springrestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.HashSet;
import java.util.Set;

import static me.bong.springrestapi.account.AccountRole.ADMIN;
import static me.bong.springrestapi.account.AccountRole.USER;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    private AccountService accountService;

    Set<AccountRole> accountRoleSet = new HashSet<>();



    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        //given
        accountRoleSet.add(ADMIN);
        accountRoleSet.add(USER);

        Account account = Account.builder()
                .email("bong@email.com")
                .password("bong")
                .roles(accountRoleSet)
                .build();
        accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "pass";

        //when && then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", "bong@email.com")
                .param("password", "bong")
                .param("grant_type", "password")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").isNotEmpty())
                ;
    }

}