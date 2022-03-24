import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  //templateUrl: './product-list.component.html',
  //templateUrl: './product-list-table.component.html',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  currentCategoryId: number = 0;
  currentCategoryName: string = "";
  searchMode: boolean = false;
  previousCategoryId: number = 1;

  // new properties for pagination
  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;

  previousKeyword: string = "";


  constructor(private productService: ProductService,
              private cartService: CartService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
    this.listProducts();
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword'); // path
    if (this.searchMode) {
      this.handleSearchProducts();
    }
    else {
      this.handleListProducts();
    }

  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

    // if we have a different category id than previous
    // then set thePageNumber to 1
    if(this.previousKeyword != theKeyword){
      this.thePageNumber = 1;
    }
    this.previousKeyword = theKeyword;
    console.log(`Keyword=${theKeyword}, thePageNumber=${this.thePageNumber}`);

    this.productService.searchProductsPaginate(theKeyword, this.thePageNumber - 1, this.thePageSize).subscribe( // method is invoked when you subscribe
      this.processResult(),
      (error) =>{
        console.log(error.error.message);
      }
    )

    // now search for the products using keyword -- old before pagination
    /*this.productService.searchProducts(theKeyword).subscribe(
      data => {
        this.products = data;
      }
    )*/
  }

  handleListProducts() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if (hasCategoryId) {
      // get the "id" param string. convert string to a number using the "+" symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;

      // get the "name" param string
      this.currentCategoryName = this.route.snapshot.paramMap.get('name')!;
    }
    else {
      // not category id available .. default 1 
      this.currentCategoryId = 1;
      this.currentCategoryName = 'Books';
    }

    /*this.productService.getProductByCategory(this.currentCategoryId).subscribe(
      (      data: Product[]) => {
        this.products = data;
      }
    ) */ 
    
    //
    // Check if we have a different category than previous
    // Note: Angular will reuse a component if it is currently being viewed
    //

    // if we have a different category id than previous
    // then set thePageNumber back to 1
    if (this.previousCategoryId != this.currentCategoryId) {
      this.thePageNumber = 1;
    }

    this.previousCategoryId = this.currentCategoryId;
    console.log(`currentCategoryId=${this.currentCategoryId}, thePageNumber=${this.thePageNumber}`);

    // now get the products for the given category id 
    this.productService.getProductListPaginate(this.currentCategoryId, this.thePageNumber - 1, this.thePageSize).subscribe( // method is invoked when you subscribe
      this.processResult(),
      (error) =>{
        console.log(error.error.message);
      }
    )
  }

  private processResult() {
    return (data: any) => {
      this.products = data['content'];
      this.thePageNumber = data.number + 1;
      this.thePageSize = data.size;
      this.theTotalElements = data.totalElements;
    };
  }

  public updatePageSize(pageSize: number) {
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.listProducts();
  }

  addToCart(theProduct: Product){
    console.log("Adding to cart: "+theProduct.name +" "+theProduct.unitPrice);

    const theCartItem = new CartItem(theProduct);

    this.cartService.addToCart(theCartItem);
  }

}