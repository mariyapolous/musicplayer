package com.example.musicplayer.rest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

//а мне все эти запросы часового пояса и правда нужны?
@Slf4j
@Component
public class RequestContextResolver {
    public HttpServletRequest request() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (IllegalStateException e) {
            log.warn("Cannot access request object. " + e.getMessage(), e);
            return null;
        }
    }

    public HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    public TimeZone timeZone() {
        String timeZoneHeader = this.request().getHeader("Time-Zone");
        if (timeZoneHeader != null) {
            try {
                int zoneOffsetValue = Integer.parseInt(timeZoneHeader);
                ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds((int) TimeUnit.HOURS.toSeconds(zoneOffsetValue));
                return TimeZone.getTimeZone(zoneOffset);
            } catch (NumberFormatException e) {
                log.error("Cannot parse Time-Zone header");
            } catch (Exception e) {
                log.error("Cannot set request time-zone");
            }
        }

        return TimeZone.getDefault();
    }

    public LocalDateTime clientTime() {
        return LocalDateTime.ofInstant(Instant.now(), this.timeZone().toZoneId());
    }
}
