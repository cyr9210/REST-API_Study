package me.bong.springrestapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with SPRING")
                .build();
        assertThat(event).isNotNull();
    }

    public void javaBean() {
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    @Parameters(method = "parametersForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isEqualTo(isFree);

    }

    private Object[] parametersForTestFree() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0 , false},
                new Object[] {0, 100, false}
        };
    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffline) {
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);


    }

    private Object[] parametersForTestOffline() {
        return new Object[] {
                new Object[] {"강남", true},
                new Object[] {"   ", false},
                new Object[] {"", false},
                new Object[] {null, false}
        };
    }


}