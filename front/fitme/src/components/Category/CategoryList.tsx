import React from 'react';

type OptionType = {
  id: number;
  name: string;
};

type CategoryProps = {
  category: OptionType;
  onClick: (category: OptionType) => void;
};

const CategoryList: React.FC<CategoryProps> = ({ category, onClick }) => {
  return (
    <>
      <button
        className='flex flex-col gap-4 items-center justify-center my-4'
        onClick={() => onClick(category)}
      >
        <div className='bg-gray-300 rounded-xl w-20 h-20'></div>
        <div className='flex justify-center'>{category.name}</div>
      </button>
    </>
  );
};

export default CategoryList;
