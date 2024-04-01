import React from 'react';

export default function FeedListMagazine() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  return (
    <>
      <div className='grid grid-cols-3 flex-wrap justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <img
            className='p-1'
            src={`https://fit-me.site/images/feed/magazine/magazine${num}.png`}
            alt=''
          />
        ))}
      </div>
    </>
  );
}
