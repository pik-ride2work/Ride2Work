package com.pik.backend.custom_daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.backend.services.RoutePoint;
import com.pik.ride2work.tables.daos.RouteDao;
import com.pik.ride2work.tables.pojos.Route;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.pik.ride2work.Tables.POINT;

public class CustomRouteDao {

    private final Configuration cfg;
    private static final String COORDINATES = "coordinates";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public CustomRouteDao(Configuration cfg) {
        this.cfg = cfg;
    }

    public List<RoutePoint> getPointsByRouteId(Integer userId) throws IOException {
        Result<Record3<Integer, Timestamp, String>> result = DSL.using(cfg)
                .select(POINT.ID_ROUTE, POINT.TIMESTAMP, pointName(COORDINATES))
                .from(POINT)
                .where(POINT.ID_ROUTE.eq(userId))
                .fetch();
        List<RoutePoint> points = new ArrayList<>();
        for (Record3<Integer, Timestamp, String> record : result) {
            Coordinates coordinates = objectMapper.readValue(record.get(2, String.class), Coordinates.class);
            points.add(new RoutePoint(record.get(0, Integer.class), record.get(1, Timestamp.class), coordinates));
        }
        return points;
    }

    public List<Route> getRoutesByUserId(Integer userId) throws IOException {
        RouteDao routeDao = new RouteDao(cfg);
        return routeDao.fetchByIdUser(userId);
    }


    private static Field<String> pointName(String name) {
        return DSL.field(String.format("ST_AsGeoJSON(%s)", name), String.class);
    }

}
