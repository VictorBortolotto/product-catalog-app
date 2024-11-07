import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category.model';
import { Product } from '../../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url = 'http://localhost:8080'

  constructor(private http: HttpClient) { }

  save(category: Category): Observable<Category> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<Category>(`${this.url}/category`, category, { headers });
  }

  update(category: Category): Observable<Category> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<Category>(`${this.url}/category`, category, { headers });
  }

  get(id: number): Observable<Category> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.get<Category>(`${this.url}/category/` + id, {headers});
  }

  getAll(): Observable<Array<Category>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.get<Array<Category>>(`${this.url}/category`, {headers});
  }

  getProductsByCategoryId(id: number): Observable<Array<Product>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.get<Array<Product>>(`${this.url}/category/` + id + `/products`, {headers});
  }

  addProductToCategory(category: Category, product: Product) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(`${this.url}/category/product`, { category, product }, {headers});
  }

  delete(id: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.delete(`${this.url}/category/` + id , {headers});
  }

  deleteByCategoryIdAndProductId(categoryId: number, productId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.delete(`${this.url}/category/` + categoryId + `/product/` + productId, {headers});
  }
}
