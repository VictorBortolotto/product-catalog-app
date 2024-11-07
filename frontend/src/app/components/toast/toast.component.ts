import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.css'
})
export class ToastComponent {
  show = false;
  message = '';
  backgroundColor: string = '';

  constructor() { }

  ngOnInit(): void { }

  showToast(message: string, backgroundColor: string): void {
    this.message = message;
    this.show = true;
    this.backgroundColor = backgroundColor
    setTimeout(() => {
      this.show = false;
    }, 3000);
  }
}
