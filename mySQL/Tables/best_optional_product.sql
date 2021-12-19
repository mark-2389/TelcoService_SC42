DROP TABLE IF EXISTS `best_optional_product`;

-- The best_optional_product contains the id of the optional product that has generated
-- the highest value of sales and the relative value of sales.
-- The table should be populated with one tuple only and be updated only by a trigger.
CREATE TABLE `best_optional_product` (
    `id` INT,
    `value_of_sales` DECIMAL(6, 2)
);

-- The optional_product_volumes_of_sales tables contains every optional product with its
-- related volume of sales. This table is updated only using a trigger.
CREATE TABLE `optional_product_volume_of_sales` (
    `id` INT,
    `value_of_sales` DECIMAL(6, 2)
);