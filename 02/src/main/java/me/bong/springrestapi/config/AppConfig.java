package me.bong.springrestapi.config;

import me.bong.springrestapi.account.Account;
import me.bong.springrestapi.account.AccountRole;
import me.bong.springrestapi.account.AccountService;
import me.bong.springrestapi.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            private AccountService accountService;

            @Autowired
            private AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Set<AccountRole> adminRoleSet = new HashSet<>();
                adminRoleSet.add(AccountRole.ADMIN);
                Set<AccountRole> userRoleSet = new HashSet<>();
                userRoleSet.add(AccountRole.USER);

                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(adminRoleSet)
                        .build();

                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .roles(userRoleSet)
                        .build();

                accountService.saveAccount(admin);
                accountService.saveAccount(user);
            }
        };
    }


}
