import React from 'react';

export default function FeedListMagazine() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  return (
    <>
      <div className='grid grid-cols-3 flex-wrap justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <div className='m-1 h-64 w-42 flex justify-center items-center'>
            <img
              className='max-w-full max-h-full object-contain'
              src={`https://fit-me.site/images/feed/magazine/magazine${num}.png`}
              alt=''
            />
          </div>
        ))}
      </div>
    </>
  );
}
