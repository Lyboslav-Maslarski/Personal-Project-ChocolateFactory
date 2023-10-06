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
  orders?: [];
}

export interface UserShort {
  id: number;
  email: string;
  isModerator: boolean;
}

