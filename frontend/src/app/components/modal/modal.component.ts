import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Product } from '../../model/product.model';
import { CategoryService } from '../../service/category/category.service';
import { ProductService } from '../../service/product/product.service';
import { Category } from '../../model/Category.model';
import { ToastService } from '../../service/toast/toast.service';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.css'
})
export class ModalComponent implements OnInit {
  products: Product[] = [];
  category: Category = {id: 0, name: '', description: ''};
  isVisible: boolean = false;
  isProductsView: boolean = true;

  constructor(private httpCategory: CategoryService, private httpProduct: ProductService, private toastService: ToastService) {}

  ngOnInit(): void {}

  open(category: Category) {
    this.isVisible = true;
    this.category = category;
    this.loadProducts();
  }

  close() {
    this.isVisible = false;
  }

  loadProducts() {
    this.isProductsView = true;
    this.httpProduct.getAll().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        this.toastService.showToast("Failed to retrieve the products. Please try again.", "red")
      }
    });
  }  

  addProductToCategory(product: Product) {
    if (this.category.id) {
      this.httpCategory.addProductToCategory(this.category, product).subscribe({
        error: (err) => {
          this.toastService.showToast("Failed to add the product in category. Please try again.", "red")
        }
      });
    }
  }

  loadProductsInCategory() {
    if (this.category.id) {
      this.isProductsView = false;
      this.httpCategory.getProductsByCategoryId(this.category.id).subscribe({
        next: (data) => {
          this.products = data;
        },
        error: (err) => {
          this.toastService.showToast("Failed to retrieve the products. Please try again.", "red")
        }
      });
    }
  }

  removeProductFromCategory(product: Product) {
    if (this.category.id && product.id) {
      this.httpCategory.deleteByCategoryIdAndProductId(this.category.id, product.id).subscribe({
        next: (data) => {
          this.products = this.products.filter(c => c.id !== product.id);
          this.products = data;
        },
        error: (err) => {
          this.toastService.showToast("Failed to retrieve the products. Please try again.", "red")
        }
      });
    }
  }
}
