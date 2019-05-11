package com.pik.backend.services;

import com.pik.backend.util.DSLWrapper;
import com.pik.ride2work.tables.daos.RouteDao;
import com.pik.ride2work.tables.pojos.Route;
import com.pik.ride2work.tables.records.RouteRecord;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.pik.ride2work.Tables.ROUTE;

@Repository
public class DefaultRouteService implements RouteService {

    private static final String INSERT_POINT_TEMPLATE = "INSERT INTO ride2work.point (timestamp, coordinates, id_route) VALUES\n";
    private static final String POINT_RECORD_TEMPLATE = "('%s', ST_GeographyFromText('SRID=4326;POINT(%s %s)'), %s)";
    private final DSLContext dsl;

    public DefaultRouteService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Future<Integer> startRoute(Integer userId) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                RouteRecord routeRecord = DSL.using(cfg)
                        .insertInto(ROUTE, ROUTE.ID_USER, ROUTE.IS_FINISHED, ROUTE.IS_VALID)
                        .values(userId, false, false)
                        .returning(ROUTE.ID)
                        .fetchOne();
                future.complete(routeRecord.getId());
            } catch (DataAccessException e) {
                future.completeExceptionally(new IllegalStateException("Failed to create a new route for the user."));
            }
        });
        return future;
    }

    @Override
    public Future<Void> endRoute(Integer routeId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                RouteDao routeDao = new RouteDao(cfg);
                Route toEnd = routeDao.findById(routeId);
                toEnd.setIsFinished(true);
                routeDao.update(toEnd);
                future.complete(null);
            } catch (DataAccessException e) {
                future.completeExceptionally(new IllegalStateException("Failed to create a new route for the user."));
            }
        });
        return future;
    }

    @Override
    public Future<Void> writeUploadedRoute(UploadedRoute uploadedRoute) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            Integer routeId = startRoute(uploadedRoute.getUserId()).get();
            String insertQuery = INSERT_POINT_TEMPLATE + uploadedRoute.getPoints().stream()
                    .map(point -> singlePointRecord(point, routeId))
                    .collect(Collectors.joining(",")) + ";";
            DSL.using(cfg).execute(insertQuery);
        });
        return future;
    }

    private static String singlePointRecord(RoutePoint point, Integer routeId) {
        return String.format(POINT_RECORD_TEMPLATE,
                point.getTimestamp().toString(),
                point.getLatitude(),
                point.getLongitude(),
                routeId);
    }
}
