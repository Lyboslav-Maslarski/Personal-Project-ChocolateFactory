import { Type } from './Type';
export interface AddProduct {
  name: string;
  description: string;
  imageUrl: string;
  quantity: number;
  price: number;
  type: Type;
}
