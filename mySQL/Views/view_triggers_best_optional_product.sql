DROP TABLE IF EXISTS `best_optional_product`;
USE telcoservice_db;

-- The best_optional_product contains the id of the optional product that has generated
-- the highest value of sales and the relative value of sales.
-- The table should be populated with one tuple only and be updated only by a trigger.
CREATE TABLE `best_optional_product` (
    `ID` INT,
    `VALUE_OF_SALES` DECIMAL(6, 2)
);

-- The optional_product_volumes_of_sales tables contains every optional product with its
-- related volume of sales. This table is updated only using a trigger.
CREATE TABLE `optional_product_volume_of_sales` (
    `ID` INT,
    `VALUE_OF_SALES` DECIMAL(6, 2)
);

delimiter //

-- when a new optional_product is created we create a new tuple in the optional_product_volume_of_sales
-- table
CREATE TRIGGER update_volume_of_sales
    AFTER INSERT ON telcoservice_db.optional_product
    FOR EACH ROW
BEGIN
    INSERT INTO telcoservice_db.optional_product_volume_of_sales
    VALUES (new.ID, 0.0);
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
    DECLARE duration INT;

    IF ( old.is_valid <> 'ACCEPTED' and new.is_valid = 'ACCEPTED' ) THEN

        SET duration = (
            SELECT period
            FROM validity
            WHERE PACKAGE_ID = new.package_Id AND id = new.validity_Id
        );

        -- update the product in the table
        UPDATE telcoservice_db.optional_product_volume_of_sales VOS
        SET VALUE_OF_SALES = VALUE_OF_SALES + (
            SELECT OP.monthly_fee * duration
            FROM telcoservice_db.Order_Optional_Composition OOC JOIN telcoservice_db.optional_product as OP
                                                                     ON OOC.optional_Product_Id = OP.id
            WHERE OOC.order_Id = new.id and VOS.ID = OP.ID
        )
        WHERE VOS.id IN (
            SELECT OOC.OPTIONAL_PRODUCT_ID
            FROM telcoservice_db.Order_Optional_Composition OOC
            WHERE OOC.ORDER_ID = new.ID
        );

    END IF;
END; //
delimiter ;
delimiter //

CREATE TRIGGER update_best_optional_product
    AFTER UPDATE ON telcoservice_db.optional_product_volume_of_sales
    FOR EACH ROW
    BEGIN
        DELETE FROM best_optional_product;
        INSERT INTO best_optional_product (
            SELECT id, VALUE_OF_SALES
            FROM telcoservice_db.optional_product_volume_of_sales
            WHERE VALUE_OF_SALES >= ALL (
                SELECT VALUE_OF_SALES
                FROM telcoservice_db.optional_product_volume_of_sales
            )
        );
END; //

delimiter ;

