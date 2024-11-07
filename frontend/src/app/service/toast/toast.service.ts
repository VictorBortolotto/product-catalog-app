import { ToastComponent } from '../../components/toast/toast.component';
import { Injectable, ViewContainerRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  private viewContainerRef!: ViewContainerRef;

  constructor() {}

  setViewContainerRef(viewContainerRef: ViewContainerRef): void {
    this.viewContainerRef = viewContainerRef;
  }

  showToast(message: string, color: string): void {
    if (this.viewContainerRef) {
      const componentRef = this.viewContainerRef.createComponent(ToastComponent);
      componentRef.instance.showToast(message, color);
    }
  }
}
