export interface User {
  id: number;
  username: string;
  email: string;
  roles: any[];
  accessToken: string;
  token_type: string;
}
