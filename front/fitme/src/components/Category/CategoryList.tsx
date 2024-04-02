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
  const imagePath = require(`../../assets/images/category/category${category.id}.png`);
  return (
    <>
      <button
        className='flex flex-col gap-4 items-center justify-center my-4'
        onClick={() => onClick(category)}
      >
        <div className='bg-gray-300 rounded-xl bolder-solid border-2 border-black w-20 h-20'>
          <img src={imagePath} alt={category.id.toString()} className='rounded-xl' />
        </div>
        <div className='flex justify-center'>{category.name}</div>
      </button>
    </>
  );
};

export default CategoryList;
