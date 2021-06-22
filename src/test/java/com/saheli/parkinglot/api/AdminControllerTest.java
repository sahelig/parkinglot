package com.saheli.parkinglot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saheli.parkinglot.request.ParkingSpotAddRequest;
import com.saheli.parkinglot.service.ParkingLevelMaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingLevelMaintenanceService parkingLevelMaintenanceService;

    private byte[] payload;

    @BeforeEach
    void setMockMvc() throws IOException {
        String requestPath = "requestToAddSpot.json";
        File json = ResourceUtils.getFile(String.format("classpath:%s", requestPath));

        payload = Files.readAllBytes(json.toPath());

        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController
                (parkingLevelMaintenanceService)).build();

    }

    @Test
    public void testRequest() throws Exception {
        ParkingSpotAddRequest request = objectMapper.readValue(payload, ParkingSpotAddRequest.class);

        URI targetUrl = UriComponentsBuilder.fromUriString("/v1/parkinglot/addspot")
                .build()
                .toUri();


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(targetUrl).accept
                (MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        verify(parkingLevelMaintenanceService).addSpot(request);
        assertEquals(HttpServletResponse.SC_NO_CONTENT, result.getResponse().getStatus());
    }

}