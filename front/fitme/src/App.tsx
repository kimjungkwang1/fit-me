import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Button } from 'flowbite-react';
import Cart from './pages/cart';
import Dressroom from './pages/dressroom';

function App() {
  return (
    <div className='App max-w-[400px] mx-auto'>
      <Dressroom></Dressroom>
    </div>
  );
}

export default App;
