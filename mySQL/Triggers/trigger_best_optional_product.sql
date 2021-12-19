delimiter //

-- when a new optional_product is created we create a new tuple in the optional_product_volume_of_sales
-- table
CREATE TRIGGER update_volume_of_sales
    AFTER INSERT ON telcoservice_db.optional_product
    FOR EACH ROW
BEGIN
    INSERT INTO telcoservice_db.optional_product_volume_of_sales
    VALUES (new.id, 0.0);
END; //

delimiter ;
delimiter //

-- when an order is created the optional products of the order update the table
-- optional_product_volume_of_sales.
CREATE TRIGGER update_optional_product_sales
    AFTER UPDATE ON telcoservice_db.order
    FOR EACH row
BEGIN
    -- the duration of the order in number of months
    DECLARE duration INT DEFAULT (
		SELECT period
        FROM validity
        WHERE packageId = new.packageId AND id = new.validityId
    );

	-- a table containing the id of an optional product and the relative total value
	CREATE TEMPORARY TABLE new_volume_of_sales (
			SELECT OP.id AS Id, OP.monthly_fee * duration AS total_value
			FROM telcoservice_db.OrderOptionalComposition OOC JOIN telcoservice_db.optional_product as OP
			ON OOC.optionalProductId = OP.id
			WHERE OOC.orderId = new.id -- select only the optionals of the new order
	);

	-- update the product in the table
    UPDATE telcoservice_db.optional_product_volume_of_sales VOS
    SET value_of_sales = value_of_sales + (
        SELECT total_value
        FROM new_volume_of_sales N
        WHERE VOS.id = N.id
    )
    WHERE VOS.id IN (
        SELECT id FROM new_volumes_of_sales
    );

END; //
delimiter ;
delimiter //

CREATE TRIGGER update_best_optional_product
    AFTER UPDATE ON telcoservice_db.optional_product_volume_of_sales
    FOR EACH ROW
    BEGIN
        DELETE FROM best_optional_product;
        INSERT INTO best_optional_product (
            SELECT id, value_of_sales
            FROM telcoservice_db.optional_product_volume_of_sales
            WHERE value_of_sales >= ALL (
                SELECT value_of_sales
                FROM telcoservice_db.optional_product_volume_of_sales
            )
        );

        -- DECLARE new_id INT DEFAULT (
        -- 	SELECT id, max(value_of_sales)
        --    FROM telcoservice_db.optional_product_volume_of_sales
        -- );
        --
        -- DECLARE new_id INT DEFAULT (
        -- 	SELECT id, max(value_of_sales)
        --    FROM telcoservice_db.optional_product_volume_of_sales
        -- );
        --
        -- DECLARE top_id INT DEFAULT (
        -- 	SELECT id
        --     FROM best_optional_product
        -- );
        --
        -- IF (new_id = top_id) THEN
        -- 	UPDATE best_optional_product
        --     SET value_of_sales = (
        -- 		SELECT value_of_sales
        -- 		FROM optional_product_volume_of_sales VOS
        -- 		WHERE VOS.id = new_id
        -- );
        -- ELSE IF ()
        -- UPDATE best_optional_product
        -- SET value_of_sales = (
        --     SELECT value_of_sales
        --     FROM optional_product_volume_of_sales VOS
        --     WHERE VOS.id = new_id
        -- );
        -- UPDATE best_optional_product
        -- SET value_of_sales = (
        --     SELECT value_of_sales
        --     FROM optional_product_volume_of_sales VOS
        --     WHERE VOS.id = new_id
        -- );
        --
        -- END IF;
END; //

delimiter ;

