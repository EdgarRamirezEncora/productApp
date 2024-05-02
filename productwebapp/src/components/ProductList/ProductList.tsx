import {useEffect, useState} from "react";
import {Product} from "../../types/product.ts";
import ProductController from "../../controllers/ProductController.ts";

import ProductTable from "./ProductTable.tsx";
import {Button, Container, Grid} from "@mui/material";
import {useSnackbar} from "notistack";
import {Link} from "react-router-dom";
import {Add} from "@mui/icons-material";


const ProductList = () => {
    const [productList, setProductList] = useState<Product[]>([])
    const { enqueueSnackbar } = useSnackbar()

    useEffect(() => {
        const getProducts = async () => {
           return await ProductController.getAllProducts()
        }

        getProducts().then(response => {
            setProductList(response)
        }).catch(error => {
            enqueueSnackbar(error.message, {variant: "error"})
        })
    });


    return (
        <>
        <Container>
            <h1 className="text-center">Products</h1>
            <Grid container justifyContent="flex-end" mb={2}>
                <Link className="btn btn-success" to="/add-product">
                    <Button variant="contained" color="success">
                        <Add/>
                        Add Product
                    </Button>
                </Link>
            </Grid>
            <ProductTable
                productList={productList}
            />
        </Container>
        </>
    )
}

export default ProductList

