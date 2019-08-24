package me.bong.springrestapi.events;

import me.bong.springrestapi.common.BaseControllerTest;
import me.bong.springrestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {


    @Autowired
    private EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("spring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 11, 23, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 12, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2019, 07, 13, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 07, 14, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남")
                .build();
//        event.setId(10);

//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(header().stringValues(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("free").value(false))
                    .andExpect(jsonPath("offline").value(true))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.update-events").exists())
                    .andExpect(jsonPath("_links.query-events").exists())
                    .andDo(document("create-event",
                            links(
                                    linkWithRel("self").description("link to self"),
                                    linkWithRel("update-events").description("link to update event"),
                                    linkWithRel("query-events").description("link to query events"),
                                    linkWithRel("profile").description("link to profile")
                            ),
                            requestHeaders(
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header"),
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header")
                            ),
                            requestFields(
                                    fieldWithPath("name").description("이벤트의 이름"),
                                    fieldWithPath("description").description("이벤트 설명"),
                                    fieldWithPath("beginEnrollmentDateTime").description("이벤트 신청 시작 시간"),
                                    fieldWithPath("closeEnrollmentDateTime").description("이벤트 신청 마감 시간"),
                                    fieldWithPath("beginEventDateTime").description("이벤트 시작 시간"),
                                    fieldWithPath("endEventDateTime").description("이벤트 종료 시간"),
                                    fieldWithPath("basePrice").description("기본 요금"),
                                    fieldWithPath("maxPrice").description("최대 요금"),
                                    fieldWithPath("limitOfEnrollment").description("최대 인원"),
                                    fieldWithPath("location").description("이벤트 개최 장소")
                            ),
                            responseHeaders(
                                    headerWithName(HttpHeaders.LOCATION).description("location header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("이벤트의 id"),
                                    fieldWithPath("name").description("이벤트의 이름"),
                                    fieldWithPath("description").description("이벤트 설명"),
                                    fieldWithPath("beginEnrollmentDateTime").description("이벤트 신청 시작 시간"),
                                    fieldWithPath("closeEnrollmentDateTime").description("이벤트 신청 마감 시간"),
                                    fieldWithPath("beginEventDateTime").description("이벤트 시작 시간"),
                                    fieldWithPath("endEventDateTime").description("이벤트 종료 시간"),
                                    fieldWithPath("basePrice").description("기본 요금"),
                                    fieldWithPath("maxPrice").description("최대 요금"),
                                    fieldWithPath("limitOfEnrollment").description("최대 인원"),
                                    fieldWithPath("location").description("이벤트 개최 장소"),
                                    fieldWithPath("free").description("무료 여부"),
                                    fieldWithPath("offline").description("오프라인 여부"),
                                    fieldWithPath("eventStatus").description("이벤트 상태"),
                                    fieldWithPath("_links.self.href").description("link to self"),
                                    fieldWithPath("_links.update-events.href").description("link to update-events"),
                                    fieldWithPath("_links.query-events.href").description("link to query-events"),
                                    fieldWithPath("_links.profile.href").description("link to profile")
                            )
                            ))
         ;
    }

    @Test
    public void createEvent_BadRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 11, 23, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 12, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2019, 07, 13, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 07, 14, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남")
                .free(true)
                .offline(false)
                .build();
//        event.setId(10);

//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void createEvent_BadRequest_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력받을수 없는 입력값을 사용한 경우에 에러를 발생시킨다.")
    public void createEvent_BadRequest_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("spring")
                .description("rest api")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 12, 23, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 11, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2019, 07, 14, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 07, 13, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("content[0].objectName").exists())
//                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("content[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())
//                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;

    }

    @Test
    @TestDescription("30개 이벤트를 10개씩 두번째 페이지 조회하기")
    public void queryEvents() throws Exception {
        //given
        IntStream.range(0, 30).forEach(this::generateEvent); //mehtod reference로 간결에가 사용이 가능

        //when
        ResultActions perform = this.mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

    @Test
    @TestDescription("기존의 이벤트 하나 조회하기")
    public void getEvent() throws Exception {
        //given
        Event event = this.generateEvent(100);

        //when
        ResultActions perform = this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists());

        //then
        perform.andDo(document("get-an-events"));


    }

    @Test
    @TestDescription("없는 이벤트를 조회하면 404 응답받기")
    public void getEvent404() throws Exception {
        this.mockMvc.perform(get("/api/events/1142323"))
                .andExpect(status().isNotFound());
    }

    @Test
    @TestDescription("이벤트를 정상적으로 수정하기")
    public void updateEvent() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setName("수정한 이벤트");


        // when
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventDto.getName()))
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("update-event"));

        Event updateEvent = eventRepository.findById(event.getId()).get();
        assertThat(updateEvent.getName()).isEqualTo(eventDto.getName());
    }

    @Test
    @TestDescription("이벤트 입력값이 잘못된 경우 BadRequest 응답 받기")
    public void updateEvent400_Empty() throws Exception {
        // given
        Event event = this.generateEvent(200);

        // when
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("이벤트 입력값이 잘못된 경우 BadRequest 응답 받기")
    public void updateEvent400_Wrong() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        // when
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("존재하지 않는 이벤트 수정 404 응답")
    public void updateEvent404_NotFound() throws Exception {
        // given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);

        // when
        this.mockMvc.perform(put("/api/events/123331")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Event generateEvent(int i) {
        Event event = Event.builder()
                .name("event" + i)
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

        return this.eventRepository.save(event);
    }


}
