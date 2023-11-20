import { Comment } from './Comment';
export interface Product {
  id: number;
  name: string;
  imageUrl: string;
  price: number;
}

export interface ProductDetails {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  price: number;
  comments: Comment[];
}

export interface ProductOrder {
  id: number;
  name: string;
  price: number;
}

export interface ProductUpdate {
  id: number;
  name: string;
  description: string;
  quantity: number;
  price: number;
}
