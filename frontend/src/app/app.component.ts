import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { finalize } from 'rxjs';
import { environment } from '../environments/environment';

interface Dish {
  id: number;
  name: string;
  description: string;
  price: number;
  category: string;
  imageUrl?: string | null;
  active?: boolean;
}
interface CartItem { dish: Dish; quantity: number; }
interface OrderResponse { id: number; total: number; status?: string; }
interface CreateOrderRequest {
  customerName: string;
  customerEmail: string;
  items: { productId: number; quantity: number }[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  searchTerm = '';
  activeCategory = 'Todos';
  cartOpen = false;
  loading = false;
  submittingOrder = false;
  errorMessage = '';
  successMessage = '';
  categories: string[] = ['Todos'];
  dishes: Dish[] = [];
  cart: CartItem[] = [];

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void {
    this.loadMenu();
  }

  private loadMenu(): void {
    this.loading = true;
    this.errorMessage = '';
    this.http.get<Dish[]>(`${environment.apiUrl}/products`)
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: dishes => {
          this.dishes = dishes;
          this.categories = ['Todos', ...new Set(dishes.map(dish => dish.category))];
          this.errorMessage = dishes.length ? '' : 'O cardápio está vazio no momento.';
        },
        error: (error: HttpErrorResponse) => {
          this.errorMessage = 'Não foi possível carregar o cardápio. Verifique se a API e o banco de dados estão em execução.';
          console.error('Falha ao carregar produtos:', error);
        }
      });
  }

  get filteredDishes(): Dish[] {
    const term = this.searchTerm.trim().toLocaleLowerCase('pt-BR');
    return this.dishes.filter(dish => {
      const categoryMatches = this.activeCategory === 'Todos' || dish.category === this.activeCategory;
      const searchMatches = !term ||
        dish.name.toLocaleLowerCase('pt-BR').includes(term) ||
        dish.description.toLocaleLowerCase('pt-BR').includes(term);
      return categoryMatches && searchMatches;
    });
  }

  get cartCount(): number { return this.cart.reduce((sum, item) => sum + item.quantity, 0); }
  get cartTotal(): number { return this.cart.reduce((sum, item) => sum + item.dish.price * item.quantity, 0); }

  addToCart(dish: Dish): void {
    const existing = this.cart.find(item => item.dish.id === dish.id);
    if (existing) existing.quantity += 1;
    else this.cart = [...this.cart, { dish, quantity: 1 }];
    this.successMessage = '';
  }

  changeQuantity(id: number, delta: number): void {
    this.cart = this.cart
      .map(item => item.dish.id === id ? { ...item, quantity: item.quantity + delta } : item)
      .filter(item => item.quantity > 0);
  }

  removeFromCart(id: number): void { this.cart = this.cart.filter(item => item.dish.id !== id); }

  formatPrice(value: number): string {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  checkout(): void {
    if (!this.cart.length || this.submittingOrder) return;

    const customerName = window.prompt('Informe seu nome para o pedido:')?.trim();
    if (!customerName) return;
    const customerEmail = window.prompt('Informe seu e-mail:')?.trim();
    if (!customerEmail) return;

    const request: CreateOrderRequest = {
      customerName,
      customerEmail,
      items: this.cart.map(item => ({ productId: item.dish.id, quantity: item.quantity }))
    };

    this.submittingOrder = true;
    this.errorMessage = '';
    this.http.post<OrderResponse>(`${environment.apiUrl}/orders`, request)
      .pipe(finalize(() => this.submittingOrder = false))
      .subscribe({
        next: order => {
          this.successMessage = `Pedido #${order.id} registrado com sucesso! Total: ${this.formatPrice(order.total)}.`;
          window.alert(this.successMessage);
          this.cart = [];
          this.cartOpen = false;
        },
        error: (error: HttpErrorResponse) => {
          const message = error.error?.detail || error.error?.message || 'Não foi possível registrar seu pedido.';
          this.errorMessage = typeof message === 'string' ? message : 'Confira os dados informados e tente novamente.';
          window.alert(this.errorMessage);
          console.error('Falha ao criar pedido:', error);
        }
      });
  }
}
