import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../model/product.model';
import { ProductService } from '../../service/product/product.service';
import { ToastService } from '../../service/toast/toast.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {
  @ViewChild('toastContainer', { read: ViewContainerRef }) toastContainer!: ViewContainerRef;
  form!: FormGroup;
  products: Product[] = [];

  constructor(private http: ProductService, private fb: FormBuilder, private toastService: ToastService, private router: Router) {}

  ngOnInit(): void {
    this.http.getAll().subscribe({
      next: (response) => {
        this.products = response
      },
      error: () => {
        this.toastService.showToast("Failed to retrieve the products. Please try again.", "red")
      }
    });
  }
  
  ngAfterViewInit(): void {
    if (this.toastContainer) this.toastService.setViewContainerRef(this.toastContainer);
  }

  onFabClick() {
    this.router.navigate(['/product'])
  }

  onClickDelete(product: Product) {
    if (product.id) {
      this.http.delete(product.id).subscribe({
        next: () => {
          this.products = this.products.filter(c => c.id !== product.id);
          this.toastService.showToast("Product deleted successfully!", "green")
        },
        error: () => {
          this.toastService.showToast("Failed to delete the category. Please try again.", "red")
        }
      });
    }
  }

  onClickEdit(product: Product) {
    if (product.id) {
      this.router.navigate(['/product', product.id], { state: { data: product } })
    }
  }
}
