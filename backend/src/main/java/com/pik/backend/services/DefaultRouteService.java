package com.pik.backend.services;

import com.pik.backend.custom_daos.CustomRouteDao;
import com.pik.backend.services.jooq_fields.PointField;
import com.pik.backend.util.DSLWrapper;
import com.pik.ride2work.tables.daos.RouteDao;
import com.pik.ride2work.tables.pojos.Route;
import com.pik.ride2work.tables.records.PointRecord;
import com.pik.ride2work.tables.records.RouteRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.pik.ride2work.Tables.POINT;
import static com.pik.ride2work.Tables.ROUTE;

@Repository
public class DefaultRouteService implements RouteService {

    private final DSLContext dsl;

    public DefaultRouteService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Future<List<Route>> getRoutesByUserId(Integer userId) {
        CompletableFuture<List<Route>> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            CustomRouteDao routeDao = new CustomRouteDao(cfg);
            List<Route> routes = routeDao.getRoutesByUserId(userId);
            future.complete(routes);
        });
        return future;
    }

    @Override
    public Future<List<RoutePoint>> getPointsByRouteId(Integer routeId) {
        CompletableFuture<List<RoutePoint>> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                CustomRouteDao routeDao = new CustomRouteDao(cfg);
                List<RoutePoint> points = routeDao.getPointsByRouteId(routeId);
                future.complete(points);
            } catch (DataAccessException e) {
                future.completeExceptionally(new NotFoundException("Route doesn't exist."));
            }
        });
        return future;
    }

    @Override
    public Future<Integer> startRoute(Integer userId) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            try {
                Route newRoute = startEmptyRoute(cfg, userId);
                future.complete(newRoute.getId());
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
    public Future<Void> writeUploadedRoute(UploadRoute uploadRoute) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            RouteRecord route = DSL.using(cfg)
                    .insertInto(ROUTE)
                    .set(ROUTE.ID_USER, uploadRoute.getUserId())
                    .set(ROUTE.IS_FINISHED, true)
                    .set(ROUTE.IS_VALID, true)
                    .set(ROUTE.TIME_IN_SECONDS, BigDecimal.valueOf(uploadRoute.getTime()))
                    .set(ROUTE.DISTANCE, BigDecimal.valueOf(uploadRoute.getLength()))
                    .set(ROUTE.TOP_RIGHT_BORDER, new PointField(uploadRoute.getTopRightBorder()))
                    .set(ROUTE.BOTTOM_LEFT_BORDER, new PointField(uploadRoute.getBottomLeftBorder()))
                    .returning(ROUTE.fields())
                    .fetchOne();
            InsertSetStep<PointRecord> setStep = DSL.using(cfg)
                    .insertInto(POINT);
            List<RoutePoint> points = uploadRoute.getPoints();
            for (int i = 0; i < points.size(); i++) {
                RoutePoint point = points.get(i);
                InsertSetMoreStep<PointRecord> moreStep = setStep
                        .set(POINT.TIMESTAMP, point.getTimestamp())
                        .set(POINT.ID_ROUTE, route.getValue(ROUTE.ID))
                        .set(POINT.LENGTH, BigDecimal.valueOf(point.getLength()))
                        .set(POINT.TIME, BigDecimal.valueOf(point.getTravelTimeSeconds()))
                        .set(POINT.COORDINATES, new PointField(point.getCoordinates()));
                if (i == points.size() - 1) {
                    moreStep.execute();
                } else {
                    moreStep.newRecord();
                }
            }
            future.complete(null);
        });
        return future;
    }


    @Override
    public Future<Void> writeSinglePoint(RoutePoint point) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DSLWrapper.transaction(dsl, future, cfg -> {
            DSL.using(cfg).
                    insertInto(POINT)
                    .set(POINT.COORDINATES, new PointField(point.getCoordinates()))
                    .set(POINT.ID_ROUTE, point.getRouteId())
                    .set(POINT.TIMESTAMP, point.getTimestamp())
                    .execute();
            future.complete(null);
        });
        return future;
    }

    private Route startEmptyRoute(Configuration cfg, Integer userId) {
        RouteRecord fetch = DSL.using(cfg)
                .insertInto(ROUTE, ROUTE.ID_USER, ROUTE.IS_FINISHED, ROUTE.IS_VALID)
                .values(userId, false, false)
                .returning(ROUTE.ID)
                .fetchOne();
        return fetch.into(Route.class);
    }
}
