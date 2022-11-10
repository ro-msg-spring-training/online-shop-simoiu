package ro.msg.learning.shop.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.service.test.TestService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(value = "test")
public class MigrationTestController {
    private final TestService testService;

    @GetMapping("/populate")
    @ResponseStatus(HttpStatus.OK)
    public void populate() {
        testService.populateData();
    }

    @GetMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        testService.clearData();
    }
}
