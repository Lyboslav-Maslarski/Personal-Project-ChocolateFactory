import { Product } from './Product';
import { User } from './User';

export interface Order {
  id: number;
  products: Product[];
  buyer: User;
  expected: Date;
  total: number;
}
