import React from 'react';

export default function FeedListUser() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  const columns: number[][] = [[], [], []];
  imageNumbers.forEach((num, i) => {
    columns[i % 3].push(num);
  });

  return (
    <>
      <div className='grid grid-cols-3 justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <div
            className='m-1 aspect-[7/10] flex justify-center items-center rounded-md border-solid border-2 border-beige hover:scale-105 duration-300'
            key={num}
          >
            <img
              className='w-full h-full object-cover rounded-sm'
              src={`https://fit-me.site/images/feed/ootd/ootd${num}.png`}
              alt=''
            />
          </div>
        ))}
      </div>
    </>
  );
}
