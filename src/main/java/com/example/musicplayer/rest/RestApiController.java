package com.example.musicplayer.rest;

import com.example.musicplayer.rest.response.RestApiResponseBodyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.TimeZone;

public class RestApiController {
    @Autowired
    protected RequestContextResolver contextResolver;

    @Autowired
    protected RestApiResponseBodyBuilder responseBodyBuilder;

    public <T> ResponseEntity<RestApiResponseBody<Resource<T>>> success(T data, Link... links) {
        return ResponseEntity.ok().body(this.responseBodyBuilder.successBody(data, links));
    }

    public <T> ResponseEntity<RestApiResponseBody<Resources<T>>> successCollection(Collection<T> data, Link... links) {
        return ResponseEntity.ok().body(this.responseBodyBuilder.successCollectionBody(data, links));
    }

    public ResponseEntity<RestApiResponseBody<?>> success() {
        return ResponseEntity.ok().body(this.responseBodyBuilder.successBody());
    }

    protected HttpServletRequest request() {
        return this.contextResolver.request();
    }

    protected HttpServletResponse response() {
        return this.contextResolver.response();
    }

    protected TimeZone timeZone() {
        return this.contextResolver.timeZone();
    }
}
