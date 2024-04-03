import { CustomFlowbiteTheme } from 'flowbite-react';

const customDropdownTheme: CustomFlowbiteTheme['dropdown'] = {
  arrowIcon: 'ml-2 h-4 w-4',
  content: 'py-1 focus:outline-none',
  floating: {
    animation: 'transition-opacity',
    arrow: {
      base: 'absolute z-10 h-2 w-2 rotate-45',
      style: {
        light: 'bg-white',
        auto: 'bg-white',
      },
      placement: '-4px',
    },
    base: 'z-10 w-fit divide-y divide-gray-100 rounded shadow focus:outline-none',
    content: 'py-1 text-sm text-gray-700',
    divider: 'my-1 h-px bg-gray-100',
    header: 'block px-4 py-2 text-sm text-gray-700',
    hidden: 'invisible opacity-0',
    item: {
      container: '',
      base: 'flex w-full cursor-pointer items-center justify-start px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 focus:bg-gray-100 focus:outline-none',
      icon: 'mr-2 h-4 w-4',
    },
    style: {
      light: 'border border-gray-200 bg-white text-gray-900',
      auto: 'border border-gray-200 bg-white text-gray-900',
    },
    target: 'w-fit',
  },
  inlineWrapper: 'flex items-center',
};

export default customDropdownTheme;
