CREATE OR REPLACE FUNCTION public.new_point()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
        _last_point_id integer;
        _last_point_coords geography;
        _last_point_timestamp timestamp;
        _time_diff numeric(8,2);
        _length numeric(8,2);
BEGIN
    SELECT id, coordinates, timestamp
	INTO _last_point_id, _last_point_coords, _last_point_timestamp
	FROM ride2work.point
	WHERE timestamp = (SELECT MAX(timestamp) FROM ride2work.point WHERE id_route= NEW.id_route) AND id_route = NEW.id_route;
	IF NEW.length IS NULL AND _last_point_coords IS NOT NULL THEN
		_length = ST_DISTANCE(_last_point_coords, NEW.coordinates);
		_time_diff = EXTRACT(EPOCH FROM (NEW.timestamp - _last_point_timestamp));
		NEW.length = _length;
		NEW.time  = _time_diff;
		UPDATE ride2work.route
		SET
                  distance = _length + distance,
		  		  time_in_seconds = _time_diff + time_in_seconds,
                  average_speed = (_length + distance)/(_time_diff + time_in_seconds),
                  max_speed = GREATEST((_length/_time_diff), max_speed)
		WHERE id = NEW.id_route;
	ELSIF NEW.length IS NULL AND _last_point_coords IS NULL
		UPDATE ride2work.route
		SET
				  timestamp = NEW.timestamp
		WHERE id = NEW.id_route;
    ELSIF _last_point_coords IS NULL THEN
        NEW.time = 0.0;
        NEW.length = 0.0;
	RETURN NEW;
END;
$function$


--

CREATE OR REPLACE FUNCTION public.new_route()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
    _team_id integer;
BEGIN
    SELECT id_team
		INTO _team_id
		FROM ride2work.membership m
		WHERE id_user = NEW.id_user AND m.isPresent = true;

		IF _team_id IS NOT NULL THEN
		  NEW.id_team = _team_id;
    END IF;
    RETURN NEW;
END;
$function$
