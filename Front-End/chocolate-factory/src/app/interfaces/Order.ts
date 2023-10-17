import { Product } from './Product';

export interface Order {
  id: number;
  orderNumber: string;
  total: number;
  status: string;
}

export interface OrderDetails {
  id: number;
  orderNumber: string;
  total: number;
  status: string;
  products: Product[];
}
