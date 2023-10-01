import { Type } from './Type';
export interface Product {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  quantity: number;
  price: number;
  type: Type;
  timesBought: number;
}
