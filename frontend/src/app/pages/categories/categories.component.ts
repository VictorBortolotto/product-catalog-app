import { Component, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { Category } from '../../model/Category.model';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../service/category/category.service';
import { ToastService } from '../../service/toast/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent implements OnInit {
  @ViewChild('toastContainer', { read: ViewContainerRef }) toastContainer!: ViewContainerRef;
  form!: FormGroup;
  categories: Category[] = [];

  constructor(private http: CategoryService, private fb: FormBuilder, private toastService: ToastService, private router: Router) {}

  ngOnInit(): void {
    this.http.getAll().subscribe({
      next: (response) => {
        this.categories = response
      },
      error: (error) => {
        if (error.status === 401) this.router.navigate(['/login']);
      }
      
    });
  }
  
  ngAfterViewInit(): void {
    if (this.toastContainer) this.toastService.setViewContainerRef(this.toastContainer);
  }

  onFabClick() {
    this.router.navigate(['/category'])
  }

  onClickDelete(category: Category) {
    if (category.id) {
      this.http.delete(category.id).subscribe({
        next: () => {
       
          this.categories = this.categories.filter(c => c.id !== category.id);
          this.toastService.showToast("Category deleted successfully!", "green")
        },
        error: () => {
          this.toastService.showToast("Failed to delete the category. Please try again.", "red")
        }
      });
    }
  }

  onClickEdit(category: Category) {
    if (category.id) {
      this.router.navigate(['/category', category.id], { state: { data: category } })
    }
  }
}
