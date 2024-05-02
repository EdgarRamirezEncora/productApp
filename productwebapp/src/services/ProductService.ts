import {Product} from "../types/product.ts";
import axios, { AxiosResponse} from "axios";
import PRODUCTS_URL from "../config/apiConfig.ts";

class ProductService{
    static async getProductList(): Promise<AxiosResponse<Product[]>> {
            try {
                return await axios.get(PRODUCTS_URL)
            } catch (e) {
                throw new Error("Something went wrong. Try it later.")
            }
    }
}

export default ProductService
