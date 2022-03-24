import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../common/product';
import { map, Observable } from 'rxjs';
import { ProductCategory } from '../common/product-category';
//import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  //private baseUrl = 'http://localhost:8080/api/products';
  private baseUrl = 'https://localhost:8080/api';

  constructor(private httpClient: HttpClient) { }

   public getProductByCategory(theCategoryId: number): Observable<Product[]> {
    // need to build URL based on category id 
    const searchUrl = `${this.baseUrl}/category?id=${theCategoryId}`;
    return this.httpClient.get<Product[]>(searchUrl);
  }

  public getProductListPaginate(theCategoryId: number, thePage: number, thePageSize: number): Observable<GetResponseProducts> {
    // need to build URL based on category id, page and size 
    const paginationUrl = `${this.baseUrl}/products/${theCategoryId}/${thePage}/${thePageSize}`;
    return this.httpClient.get<GetResponseProducts>(paginationUrl);
  }

  searchProducts(theKeyword: string): Observable<Product[]> {

    // need to build URL based on the keyword 
    const searchUrl = `${this.baseUrl}/product/keyword?name=${theKeyword}`;
    return this.httpClient.get<Product[]>(searchUrl);
  }

   searchProductsPaginate(theKeyword: string, thePage: number, thePageSize: number): Observable<GetResponseProducts> {
    // need to build URL based on keyword string, page and size 
    const paginationUrl = `${this.baseUrl}/getProductsByKey/${theKeyword}/${thePage}/${thePageSize}`;
    return this.httpClient.get<GetResponseProducts>(paginationUrl);
  }

  public getProductCategories(): Observable<ProductCategory[]> { 
    const categoriesUrl = `${this.baseUrl}/categories`;
    return this.httpClient.get<ProductCategory[]>(categoriesUrl);
    
  }

  public findById(theProductId: number): Observable<Product> {
    // need to build URL based on product id 
    const productUrl = `${this.baseUrl}/product/${theProductId}`;
    return this.httpClient.get<Product>(productUrl);

  }

}

interface GetResponseProducts {
  
    products: Product[],
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  
}

// interface GetResponse {
//   _embedded: {
//     products: Product[];
//   }
// }