package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.CleanResult;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
@Profile(value = "test")
public class MigrationTestController {
    private final Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:shopTest", "sa", "sa").cleanDisabled(false).load();

    @GetMapping("/populate")
    @ResponseStatus(HttpStatus.OK)
    public MigrateResult populate() {
        return flyway.migrate();
    }

    @GetMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public CleanResult clear() {
        return flyway.clean();
    }
}
