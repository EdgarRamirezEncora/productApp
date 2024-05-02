import ProductService from "../services/ProductService.ts";
import {Product} from "../types/product.ts";

class ProductController {
    static async getAllProducts(): Promise<Product[]> {
        const response = await ProductService.getProductList()
        return response.data
    }
}

export  default ProductController