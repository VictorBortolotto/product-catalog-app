import { Component, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastService } from '../../service/toast/toast.service';
import { AuthService } from '../../service/auth/auth.service';
import { CommonModule, Location } from '@angular/common';
import { User } from '../../model/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit {
  @ViewChild('toastContainer', { read: ViewContainerRef }) toastContainer!: ViewContainerRef;
  form!: FormGroup;
  private user: User = {id: 0, email: '', password: ''}

  constructor(private http: AuthService, private fb: FormBuilder, private toastService: ToastService, private router: Router, private location: Location) {}
  
  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', Validators.required],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]]
    });
  }

  signup() {
    this.user.email = this.form.get('email')?.value;
    this.user.password = this.form.get('password')?.value;
    this.http.signup(this.user).subscribe({
      next: (response) => {
        localStorage.setItem("token", response.token);
        localStorage.setItem("userID", response.id);
        this.router.navigate(['/categories'])
      },
      error: () => {
        this.toastService.showToast("Failed to create the user. Please try again.", "red")
      }
    })
  }
}
