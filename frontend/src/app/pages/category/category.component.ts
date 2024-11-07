import { Component, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { CategoryService } from '../../service/category/category.service';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 
import { ToastService } from '../../service/toast/toast.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { ModalComponent } from '../../components/modal/modal.component';
import { Category } from '../../model/Category.model';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ModalComponent
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {
  @ViewChild('toastContainer', { read: ViewContainerRef }) toastContainer!: ViewContainerRef;
  @ViewChild(ModalComponent) modal!: ModalComponent;
  form!: FormGroup;
  category: Category = {name: '', description: ''};

  constructor(private http: CategoryService, private fb: FormBuilder, private toastService: ToastService, private router: Router, private location: Location) {}

  ngOnInit(): void {
    const state = this.location.getState() as { data: Category };
    if (state?.data) this.category = state.data;
    this.form = this.fb.group({
      name: [this.category.name ? this.category.name : '', Validators.required],
      description: [this.category.description ? this.category.description : '', [Validators.required]]
    });
  }

  ngAfterViewInit(): void {
    if (this.toastContainer) this.toastService.setViewContainerRef(this.toastContainer);
  }

  onSubmit() {
    if (this.form.valid) {
      this.category.id ? this.upateCategory() : this.saveCategory()
    } else {
      this.toastService.showToast("Please, fill all the field's before continue!", "red")
    }
  }

  saveCategory() {
    this.category.name = this.form.get('name')?.value;
    this.category.description = this.form.get('description')?.value;
    
    this.http.save(this.category).subscribe({
      next: () => {
        this.router.navigate(['/categories'])
      },
      error: () => {
        this.toastService.showToast("Failed to save the category. Please try again.", "red")
      }
    });

  }

  upateCategory() {
    this.category.name = this.form.get('name')?.value;
    this.category.description = this.form.get('description')?.value;
    
    this.http.update(this.category).subscribe({
      next: () => {
        this.router.navigate(['/categories'])
      },
      error: () => {
        this.toastService.showToast("Failed to save the category. Please try again.", "red")
      }
    });
  }

  openModal() {
    this.modal.open(this.category); 
  }
}
