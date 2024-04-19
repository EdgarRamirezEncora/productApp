CREATE OR REPLACE FUNCTION GetProductFn(productId BIGINT)
RETURNS TABLE (id BIGINT, name VARCHAR, description VARCHAR, price NUMERIC)
LANGUAGE SQL
AS $$
SELECT id, name, description, price FROM products WHERE id=productId;
$$;