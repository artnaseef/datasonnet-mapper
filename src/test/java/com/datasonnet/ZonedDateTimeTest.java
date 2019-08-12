package com.datasonnet;

import com.datasonnet.wrap.Mapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZonedDateTimeTest {

    @Test
    void testOffset() {
        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.offset(\"2019-07-22T21:00:00Z\", \"P1Y1D\")", new HashMap<>());
        String offsetDate = mapper.transform("{}").replaceAll("\"", "");
        assertTrue("2020-07-23T21:00:00Z".equals(offsetDate));
//        System.out.println("Offset date is " + offsetDate);
    }

    @Test
    void testNow() {
        Instant before = Instant.now();

        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.now()", new HashMap<>());
        // getting rid of quotes so the Instant parser works
        Instant mapped = Instant.parse(mapper.transform("{}").replaceAll("\"", ""));

        Instant after = Instant.now();

        assertTrue(before.compareTo(mapped) <= 0);
        assertTrue(after.compareTo(mapped) >= 0);
    }

    @Test
    void testFormat() {
        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.format(\"2019-07-04T21:00:00Z\", \"yyyy-MM-dd'T'HH:mm:ssVV\", \"d MMM uuuu\")", new HashMap<>());
        String formattedDate = mapper.transform("{}").replaceAll("\"", "");
        //System.out.println("Formatted date is " + formattedDate);
        assertTrue("4 Jul 2019".equals(formattedDate));
    }

    @Test
    void testCompare() {
        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.compare(\"2019-07-04T21:00:00Z\", \"yyyy-MM-dd'T'HH:mm:ssVV\", \"2019-07-04T21:00:00Z\", \"yyyy-MM-dd'T'HH:mm:ssVV\")", new HashMap<>());
        String compareResult = mapper.transform("{}").replaceAll("\"", "");
        //System.out.println("Formatted date is " + formattedDate);
        assertTrue("0".equals(compareResult));
    }

    @Test
    void testTimezone() {
        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.changeTimeZone(\"2019-07-04T21:00:00-0500\", \"yyyy-MM-dd'T'HH:mm:ssZ\", \"America/Los_Angeles\")", new HashMap<>());
        String newTimezone = mapper.transform("{}").replaceAll("\"", "");
        //System.out.println("New date is " + newTimezone);
        assertTrue("2019-07-04T19:00:00-0700".equals(newTimezone));
    }

    @Test
    void testLocalDT() {
        Mapper mapper = new Mapper("function(payload) PortX.ZonedDateTime.toLocalDate(\"2019-07-04T21:00:00-0500\", \"yyyy-MM-dd'T'HH:mm:ssZ\")", new HashMap<>());
        String newDate = mapper.transform("{}").replaceAll("\"", "");
        assertTrue("2019-07-04".equals(newDate));

        mapper = new Mapper("function(payload) PortX.ZonedDateTime.toLocalTime(\"2019-07-04T21:00:00-0500\", \"yyyy-MM-dd'T'HH:mm:ssZ\")", new HashMap<>());
        String newTime = mapper.transform("{}").replaceAll("\"", "");
        assertTrue("21:00:00".equals(newTime));

    }
}