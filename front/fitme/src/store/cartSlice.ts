import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import { api } from '../services/api';
import { RootState } from './store';
import { useNavigate } from 'react-router-dom';
//장바구니 아이템
interface CartItem {
  id: number;
  productId: number;
  name: string;
  price: number;
  color: string;
  productOptionId: number;
  size: string;
  productSizeId: number;
  url: string;
  quantity: number;
  stockQuantity: number;
  isChecked: boolean;
  category: number;
}
//장바구니 상태
interface CartState {
  items: CartItem[];
  totalProductCount: number;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  address: address;
  totalPrice: number;
}
interface address {
  name: String;
  phoneNumber: String;
  address: string;
}
interface option {
  productOptionId: number;
  productSizeId: number;
  quantity: number;
}
//초기상태
const initialState: CartState = {
  items: [],
  totalProductCount: 0,
  status: 'idle',
  totalPrice: 0,
  address: {
    name: 'String',
    phoneNumber: 'String',
    address: 'string',
  },
};
// 카트 정보 가져오기
export const getCart = createAsyncThunk('cart/getCart', async () => {
  const response = await api.get<any>('/api/cart/products');
  return response.data.carts.map((item: any) => ({
    id: item.id,
    productId: item.product.id,
    name: item.product.name,
    price: item.productTotalPrice,
    color: item.productOption.color,
    size: item.productOption.size,
    url: item.product.mainImages[0].url,
    quantity: item.productOption.quantity,
    stockQuantity: item.productOption.stockQuantity,
    isChecked: true,
    category: item.product.categoryId,
    productOptionId: item.productOption.id,
    productSizeId: item.productOption.productSizeId,
  }));
});
// 카트에 아이템 추가
export const addCartItem = createAsyncThunk(
  'cart/addCartItem',
  async ({ id, options }: { id: number; options: option[] }) => {
    const response = await api.post<any>('/api/cart/products/' + id, { options: options });
  }
);
// 카트에서 아이템 삭제
export const deleteCartItem = createAsyncThunk('cart/deleteCartItem', async (ids: number[]) => {
  const response = await api.delete<any>('/api/cart/products', { data: { cartIds: ids } });
});
// 카트에서 수량 수정
export const updateQuantity = createAsyncThunk(
  'cart/updateQuantity',
  async ({ id, quantity }: { id: number; quantity: number }, { rejectWithValue }) => {
    try {
      const response = await api.put<any>('/api/cart/products/quantity', {
        cartId: id,
        quantity: quantity,
      });
    } catch (error: any) {
      console.log(error);
      return rejectWithValue(error.response.data);
    }
    return { id, quantity };
  }
);
// 카트에서 주문
export const order = createAsyncThunk('cart/order', async (_, { rejectWithValue, getState }) => {
  const state = getState() as RootState;
  const checkedItems = state.cart.items.filter((item) => item.isChecked);
  const orderItems = checkedItems.map((item) => ({
    productId: item.productId,
    productOptionId: item.productOptionId,
    productSizeId: item.productSizeId,
    quantity: item.quantity,
  }));
  try {
    const response = await api.post<any>('/api/orders', {
      orderRequests: orderItems,
    });
    return response.data;
  } catch (error: any) {
    console.log(error);
    return rejectWithValue(error.response.data);
  }
});

//주소 가져오기
export const getAddress = createAsyncThunk('cart/getAddress', async () => {
  const response = await api.get<any>('/api/members');
  return response.data;
});

export const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    // 체크 변경
    toggleItemChecked: (state, action: PayloadAction<number>) => {
      const index = state.items.findIndex((item) => item.id === action.payload);
      if (index !== -1) {
        state.items[index].isChecked = !state.items[index].isChecked;
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getCart.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getCart.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload;
        state.totalProductCount = action.payload.length;
        state.totalPrice = action.payload.reduce(
          (total: number, item: CartItem) => total + item.price,
          0
        );
      })
      .addCase(getCart.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(addCartItem.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(addCartItem.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(deleteCartItem.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(deleteCartItem.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(updateQuantity.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(updateQuantity.fulfilled, (state, action) => {
        state.status = 'succeeded';
        const { id, quantity } = action.payload;
        const itemIndex = state.items.findIndex((item) => item.id === id);
        if (itemIndex !== -1) {
          let price = state.items[itemIndex].price / state.items[itemIndex].quantity;
          state.items[itemIndex].quantity = quantity;
          state.items[itemIndex].price = quantity * price;
        }
      })
      .addCase(updateQuantity.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(getAddress.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(getAddress.fulfilled, (state, action) => {
        state.status = 'succeeded';
        const { nickname, phoneNumber, address } = action.payload;
        state.address.name = nickname;
        state.address.phoneNumber = phoneNumber;
        state.address.address = address;
      })
      .addCase(getAddress.rejected, (state) => {
        state.status = 'failed';
      })
      .addCase(order.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(order.fulfilled, (state, action) => {
        state.status = 'succeeded';
      })
      .addCase(order.rejected, (state) => {
        state.status = 'failed';
      });
  },
});
export default cartSlice.reducer;
export const { toggleItemChecked } = cartSlice.actions;
