package com.mk.minishop.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.client.common.ObjectMapperFactory;
import com.mk.minishop.client.error.RestRequestError;
import com.mk.minishop.api.errors.RestErrorDto;
import io.vavr.Function0;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public abstract class AbstractClient {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractClient.class);

    private final HttpClient client = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = ObjectMapperFactory.build();

    private final String host;
    private final int port;

    public AbstractClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    protected <R> Either<RestRequestError, R> getAuthorized(String path, String token, Class<R> clazz) {
        LOG.info("GET, path: {}, token: {}", path, token);

        return processRequest(httpRequestBuilder(path)
                .header("Authorization", token)
                .GET()
                .build(), clazz);
    }

    protected <R> Either<RestRequestError, R> getUnauthorized(String path, Class<R> clazz) {
        LOG.info("GET, path: {}", path);

        return processRequest(httpRequestBuilder(path)
                .GET()
                .build(), clazz);
    }

    protected <B, R> Either<RestRequestError, R> postAuthorized(String path, String token, B body, Class<R> clazz) {
        LOG.info("POST, path: {}, body {}, token: {}", path, body, token);

        return executePost(
                () -> Try.of(
                        () -> httpRequestBuilder(path)
                                .header("Authorization", token)
                                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                                .build()
                ),
                clazz
        );
    }

    protected <B, R> Either<RestRequestError, R> postUnauthorized(String path, B body, Class<R> clazz) {
        LOG.info("POST, path: {}. body {}", path, body);

        return executePost(
                () -> Try.of(
                        () -> httpRequestBuilder(path)
                                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                                .build()
                ),
                clazz
        );
    }

    private <R> Either<RestRequestError, R> executePost(Function0<Try<HttpRequest>> httpRequestTry, Class<R> clazz) {
        return httpRequestTry.apply()
                .onFailure(e -> LOG.error("Cannot write body", e))
                .toEither(RestRequestError.CANNOT_WRITE_BODY)
                .flatMap(request -> processRequest(request, clazz));
    }

    private HttpRequest.Builder httpRequestBuilder(String path) {
        return HttpRequest.newBuilder(URI.create(buildUri(path)))
                .header("content-type", "application/json");
    }

    private <R> Either<RestRequestError, R> processRequest(HttpRequest request, Class<R> clazz) {
        return Try.of(() -> client.send(request, HttpResponse.BodyHandlers.ofString()))
                .onFailure(e -> LOG.error("Unknown error", e))
                .toEither(RestRequestError.UNKNOWN_ERROR)
                .flatMap(response -> tryParseResponse(response, clazz));
    }

    private <R> Either<RestRequestError, R> tryParseResponse(HttpResponse<String> response, Class<R> clazz) {
        final int statusCode = response.statusCode();
        if (statusCode / 100 == 2) {
            return Try.of(() -> objectMapper.readValue(response.body(), clazz))
                    .onFailure(e -> LOG.error("Cannot parse success response", e))
                    .toEither(RestRequestError.CANNOT_PARSE_SUCCESS_RESPONSE);
        }

        return Try.of(() -> objectMapper.readValue(response.body(), RestErrorDto.class))
                .onFailure(e -> LOG.error("Cannot parse error response", e))
                .fold(
                        error -> RestRequestError.CANNOT_PARSE_ERROR_RESPONSE.toEither(),
                        it -> new RestRequestError(it.getCode(), it.getMessage(), statusCode).toEither()
                );
    }

    private String buildUri(String path) {
        return String.format("http://%s:%d%s", host, port, path);
    }
}
