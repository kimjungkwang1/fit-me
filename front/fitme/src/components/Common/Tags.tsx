import React from 'react';

type TagProps = {
  tag: string;
};

export default function Tags({ tag }: TagProps) {
  return (
    <>
      <div className='mr-[3px] inline-block px-2 text-sm text-center border border-solid border-gray-500 rounded-lg'>
        {tag}
      </div>
    </>
  );
}
