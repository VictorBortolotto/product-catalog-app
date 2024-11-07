import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { CategoryComponent } from './pages/category/category.component';
import { ProductComponent } from './pages/product/product.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { ProductsComponent } from './pages/products/products.component';


export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'product', component: ProductComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'category/:id', component: CategoryComponent },
  { path: 'product/:id', component: ProductComponent }
];
