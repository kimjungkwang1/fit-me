/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts,tsx}', 'node_modules/flowbite-react/lib/esm/**/*.js'],
  theme: {
    extend: {
      aspectRatio: {
        iphone: '18 / 32',
      },
      colors: {
        darkgray: '#252323',
        bluegray: '#70798C',
        lightbrown: '#DAD2BC',
        beige: '#F5F1ED',
        brown: '#A99985',
      },
    },
  },
  plugins: [],
};
