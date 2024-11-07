import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { Product } from '../../model/product.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../service/product/product.service';
import { ToastService } from '../../service/toast/toast.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
  @ViewChild('toastContainer', { read: ViewContainerRef }) toastContainer!: ViewContainerRef;
  form!: FormGroup;
  product: Product = {name: '', description: '', price: 0.0};

  constructor(private http: ProductService, private fb: FormBuilder, private toastService: ToastService, private router: Router, private location: Location) {}

  ngOnInit(): void {
    const state = this.location.getState() as { data: Product }
    if (state?.data) this.product = state.data
    this.form = this.fb.group({
      name: [this.product.name ? this.product.name : '', Validators.required],
      description: [this.product.description ? this.product.description : '', [Validators.required]],
      price: [this.product.price ? this.product.price : '', [Validators.required]]
    });
  }

  ngAfterViewInit(): void {
    if (this.toastContainer) this.toastService.setViewContainerRef(this.toastContainer);
  }

  onSubmit() {
    if (this.form.valid) {
      this.product.id ? this.upateCProduct() : this.saveProduct();
    } else {
      this.toastService.showToast("Please, fill all the field's before continue!", "red")
    }
  }

  saveProduct() {
    this.product.name = this.form.get('name')?.value;
    this.product.description = this.form.get('description')?.value;
    this.product.price = this.form.get('price')?.value;
    
    this.http.save(this.product).subscribe({
      next: () => {
        this.router.navigate(['/products'])
      },
      error: (error) => {
        if (error.status === 401) this.router.navigate(['/login']);
        this.toastService.showToast("Failed to save the product. Please try again.", "red")
      }
    });

  }

  upateCProduct() {
    this.product.name = this.form.get('name')?.value;
    this.product.description = this.form.get('description')?.value;
    this.product.price = this.form.get('price')?.value;
    
    this.http.update(this.product).subscribe({
      next: () => {
        this.router.navigate(['/products'])
      },
      error: (error) => {
        if (error.status === 401) this.router.navigate(['/login']);
        this.toastService.showToast("Failed to update the product. Please try again.", "red")
      }
    });
  }
}
