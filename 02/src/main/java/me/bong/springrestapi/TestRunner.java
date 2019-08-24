package me.bong.springrestapi;

import lombok.RequiredArgsConstructor;
import me.bong.springrestapi.events.Event;
import me.bong.springrestapi.events.EventRepository;
import me.bong.springrestapi.events.EventStatus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class TestRunner implements ApplicationRunner {

    private final EventRepository eventRepository;

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
    }
}
