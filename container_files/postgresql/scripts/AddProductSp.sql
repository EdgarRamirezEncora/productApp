CREATE OR REPLACE PROCEDURE AddProductSp(
       product_name VARCHAR,
       product_description VARCHAR,
       product_price NUMERIC
)
LANGUAGE SQL
AS $$
    INSERT INTO products(name, description, price)
    VALUES(product_name, product_description, product_price);
$$;