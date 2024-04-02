import React from 'react';

export default function FeedListMagazine() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  return (
    <>
      <div className='grid grid-cols-3 flex-wrap justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <div className='m-1 aspect-[11/14] flex justify-center items-center  hover:scale-105 duration-300'>
            <img
              className='w-full h-full object-cover'
              src={`https://fit-me.site/images/feed/magazine/magazine${num}.png`}
              alt=''
            />
          </div>
        ))}
      </div>
    </>
  );
}
