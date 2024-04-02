import React from 'react';

export default function FeedListUser() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  return (
    <>
      <div className='grid grid-cols-3 justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <img
            key={num}
            className='p-1'
            src={`https://fit-me.site/images/feed/ootd/ootd${num}.png`}
            alt=''
          />
        ))}
      </div>
    </>
  );
}
