import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

interface CartItem {
  id: number;
  product: String;
  productOption: String;
  productTotalPrice: number;
}

interface CartState {
  items: CartItem[];
  totalProductCount: number;
  totalCartPrice: number;
}

const initialState: CartState = {
  items: [],
  totalProductCount: 0,
  totalCartPrice: 0,
};

export const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    removeItem: (state, action: PayloadAction<CartItem>) => {
      state.items.push(action.payload);
    },
  },
});
