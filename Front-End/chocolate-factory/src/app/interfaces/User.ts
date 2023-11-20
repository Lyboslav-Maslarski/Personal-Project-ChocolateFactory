import { Order } from './Order';

export interface User {
  id: number;
  token: string;
  email: string;
  fullName: string;
  city: string;
  address: string;
  phone: string;
  roles: [];
}

export interface UserDetails {
  id?: number;
  email?: string;
  fullName?: string;
  city?: string;
  address?: string;
  phone?: string;
  orders?: Order[];
}

export interface UserShort {
  id: number;
  email: string;
  moderator: boolean;
}

export interface UserRegister {
  email?: string;
  password?: string;
  fullName?: string;
  city?: string;
  address?: string;
  phone?: string;
}
