package me.bong.springrestapi;

import lombok.RequiredArgsConstructor;
import me.bong.springrestapi.account.Account;
import me.bong.springrestapi.account.AccountRole;
import me.bong.springrestapi.account.AccountService;
import me.bong.springrestapi.events.Event;
import me.bong.springrestapi.events.EventRepository;
import me.bong.springrestapi.events.EventStatus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TestRunner implements ApplicationRunner {

    private final EventRepository eventRepository;
    private final AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Event event = Event.builder()
                .name("event" + 100)
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 11, 23, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 12, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2019, 07, 13, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 07, 14, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();

        eventRepository.save(event);

        Set<AccountRole> accountRoleSet = new HashSet<>();
        accountRoleSet.add(AccountRole.ADMIN);
        accountRoleSet.add(AccountRole.USER);

        Account account = Account.builder()
                .email("bong@email.com")
                .password("bong")
                .roles(accountRoleSet)
                .build();

        accountService.saveAccount(account);


    }
}
