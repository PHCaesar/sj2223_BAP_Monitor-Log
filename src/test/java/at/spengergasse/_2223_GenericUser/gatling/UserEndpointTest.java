package at.spengergasse._2223_GenericUser.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

public class UserEndpointTest extends Simulation {

    HttpProtocolBuilder httpProtocol = http // 4
            .baseUrl("http://localhost:8080") // 5
            .doNotTrackHeader("1");

    ChainBuilder createUsers = repeat(1,"n").on(exec(http("").post("/api/user")
            .body(StringBody("{\n" +
                    "  \"name\": \"PHCaesar\",\n" +
                    "  \"mutateFirstname\" : \"Philipp\",\n" +
                    "  \"mutateLastname\" : \"Cserich\",\n" +
                    "  \"mutatePassword\" : \"password\",\n" +
                    "  \"registerDate\" : null,\n" +
                    "  \"mutateBirthDate\" : null,\n" +
                    "  \"version\" : 1\n" +
                    "}"))));

    ChainBuilder getUsers =
            repeat(100, "n").on( // 1
                    exec(http("").get("/api/user")) // 2
                            .pause(1)
            );

    ScenarioBuilder scn = scenario("UserTest") // 7
            .exec(createUsers,getUsers);

    {
        setUp(
                scn.injectOpen(rampUsers(10).during(10))
        ).protocols(httpProtocol);

    }
}
