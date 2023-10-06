import {Product} from "../product/Product";

export interface Order {
  id: number
  orderNumber: string
  total: number
  approved: boolean
  dispatched: boolean
}

export interface OrderDetails {
  id: number;
  orderNumber: string;
  total: number;
  approved: boolean;
  dispatched: boolean;
  products: Product[];
}
